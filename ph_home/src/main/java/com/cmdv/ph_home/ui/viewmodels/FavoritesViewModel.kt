package com.cmdv.ph_home.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.usecases.GetFavoriteCharactersUseCase
import com.cmdv.domain.usecases.RemoveAllFavoriteCharacterUseCase
import com.cmdv.domain.usecases.RemoveFavoriteCharacterUseCase
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.ResponseWrapper.Status
import com.cmdv.domain.utils.ResponseWrapper.Status.LOADING
import com.cmdv.domain.utils.ResponseWrapper.Status.READY
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase,
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase,
    private val removeAllFavoriteCharacterUseCase: RemoveAllFavoriteCharacterUseCase
) : ViewModel() {

    private val _viewModelState = MutableLiveData(LOADING)
    val viewModelState: LiveData<Status>
        get() = _viewModelState

    private val _favoriteCharacters = MutableLiveData<List<CharacterModel>>()
    val favoriteCharacters: LiveData<List<CharacterModel>>
        get() = _favoriteCharacters

    private val _removeAll = MutableLiveData<Event<Int>>()
    val removeAll: LiveData<Event<Int>>
        get() = _removeAll

    init {
        getFavorites()
    }

    /**
     * Get all user's favorite characters stored in DB.
     */
    fun getFavorites() {
        viewModelScope.launch {
            val params = GetFavoriteCharactersUseCase.Params()
            getFavoriteCharactersUseCase(params).collect { response ->
                with(response) {
                    _viewModelState.value = status
                    if (status == READY) data?.let { _favoriteCharacters.value = it }
                }
            }
        }
    }

    /**
     * Remove all favorite characters from DB.
     */
    fun removeAllFavorites() {
        viewModelScope.launch {
            val params = RemoveAllFavoriteCharacterUseCase.Params()
            removeAllFavoriteCharacterUseCase(params).collect { response ->
                with(response) {
                    _viewModelState.value = status
                    if (status == READY) data?.let { _removeAll.value = it }
                }
            }
        }
    }

    /**
     * Remove a single favorite character from DB.
     *
     * @param id Character unique identifier to be removed from favorites DB.
     * @param position Character position inside the adapter.
     */
    fun removeFavorite(id: Int, position: Int) {
        viewModelScope.launch {
            val params = RemoveFavoriteCharacterUseCase.Params(id, position)
            removeFavoriteCharacterUseCase(params).collect { response ->
                with(response) {
                    _viewModelState.value = status
                    if (status == READY) getFavorites()
                }
            }
        }
    }
}