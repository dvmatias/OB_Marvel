package com.cmdv.ph_home.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.usecases.*
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.ResponseWrapper
import com.cmdv.domain.utils.ResponseWrapper.Status.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val LIMIT_CHARACTERS_FETCH_DEFAULT = 32
private const val OFFSET_CHARACTERS_FETCH_DEFAULT = 0

class CharactersViewModel(
    private val getTotalCharactersUseCase: GetTotalCharactersUseCase,
    private val getCharactersUseCase: GetCharactersUseCase,
    private val removeStoredCharactersUseCase: RemoveStoredCharactersUseCase,
    private val addFavoriteCharacterUseCase: AddFavoriteCharacterUseCase,
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase
) : ViewModel() {
    /**
     * Represents the state of this view model (LOADING, READY, ERROR).
     */
    private val _viewModelState = MutableLiveData(LOADING)
    val viewModelState: LiveData<ResponseWrapper.Status>
        get() = _viewModelState

    private var _characters = MutableLiveData<List<CharacterModel>>()
    val characters: LiveData<List<CharacterModel>>
        get() = _characters

    private val isAllCharactersLoaded: Boolean
        get() = _characters.value?.size.let { totalCharactersCount == it }

    private val _addedFavoritePosition = MutableLiveData<Event<Int>>()
    val addedFavoritePosition: LiveData<Event<Int>>
        get() = _addedFavoritePosition

    private val _removedFavoritePosition = MutableLiveData<Event<Int>>()
    val removedFavoritePosition: LiveData<Event<Int>>
        get() = _removedFavoritePosition

    /**
     * Total amount of characters available in Marvel's API.
     */
    var totalCharactersCount: Int = 0

    /**
     * Get the total amount of characters available in Marvel's API. App shouldn't ask for more characters once the
     * maximum of characters has being fetched.
     */
    fun getTotalCharactersCount() {
        viewModelScope.launch {
            val params = GetTotalCharactersUseCase.Params()
            getTotalCharactersUseCase(params).collect { response ->
                with(response) {
                    totalCharactersCount = data ?: 0
                    if (totalCharactersCount != 0) {
                        getCharacters(fetch = true)
                    }
                    _viewModelState.value = status
                }
            }
        }
    }

    /**
     * Get a list of Marvel's characters.
     *
     * @param fetch If true, fetch from service call, if not, fetch from DB.
     * @param limit Limit the result set to the specified number of resources (applied only to service call).
     * @param offset Skip the specified number of resources in the result set (applied only to service call).
     */
    fun getCharacters(
        fetch: Boolean,
        limit: Int = LIMIT_CHARACTERS_FETCH_DEFAULT,
        offset: Int = OFFSET_CHARACTERS_FETCH_DEFAULT
    ) {
        if (!isAllCharactersLoaded) {
            val params = GetCharactersUseCase.Params(fetch, limit, offset)
            viewModelScope.launch {
                getCharactersUseCase(params).collect { response ->
                    with(response) {
                        if (status == READY) {
                            data?.let { _characters.value = it }
                        }
                        _viewModelState.value = status
                    }
                }
            }
        }
    }

    /**
     * Remove stored characters in DB.
     */
    fun removeStoredCharacters() {
        viewModelScope.launch {
            removeStoredCharactersUseCase(RemoveStoredCharactersUseCase.Params()).collect()
        }
    }

    /**
     * Add a character as favorite into the DB.
     *
     * @param characterId Character unique identifier to be added as favorite.
     * @param position Character's position inside the adapter.
     */
    fun addFavorite(characterId: Int, position: Int) {
        val params = AddFavoriteCharacterUseCase.Params(characterId, position)
        viewModelScope.launch {
            addFavoriteCharacterUseCase(params).collect { response ->
                response.data?.let { event ->
                    _addedFavoritePosition.value = event
                }
            }
        }
    }

    /**
     * Removed a character as favorite from the DB.
     *
     * @param characterId Character unique identifier to be added as favorite.
     * @param position Character's position inside the adapter.
     */
    fun removeFavorite(characterId: Int, position: Int) {
        val params = RemoveFavoriteCharacterUseCase.Params(characterId, position)
        viewModelScope.launch {
            removeFavoriteCharacterUseCase(params).collect { response ->
                response.data?.let { event ->
                    _removedFavoritePosition.value = event
                }
            }
        }
    }
}