package com.cmdv.ph_character_details.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.models.ComicModel
import com.cmdv.domain.usecases.GetCharacterByIdUserCase
import com.cmdv.domain.usecases.GetComicsByCharacterIdUserCase
import com.cmdv.domain.utils.ResponseWrapper.Status
import kotlinx.coroutines.launch

private const val OFFSET_COMICS_FETCH_DEFAULT = 0

/**
 * Android View Model.
 */
class CharacterDetailsViewModel(
    private val getCharacterByIdUserCase: GetCharacterByIdUserCase,
    private val getComicsByCharacterIdUserCase: GetComicsByCharacterIdUserCase
) : ViewModel() {
    /**
     * Represents the state of this view model (LOADING, READY, ERROR).
     */
    private val _viewModelState = MutableLiveData(Status.LOADING)
    val viewModelState: LiveData<Status>
        get() = _viewModelState

    private val _character = MutableLiveData<CharacterModel>()
    val character: LiveData<CharacterModel>
        get() = _character

    private val _comics = MutableLiveData<List<ComicModel>>()
    val comics: LiveData<List<ComicModel>>
        get() = _comics

    private var totalComicsCount: Int = 0

    private var comicsCount: Int = 0

    /**
     * Get the character details.
     *
     * @param characterId The character unique identifier fer getting details.
     */
    fun getCharacterDetails(characterId: Int?) {
        if (characterId == null) {
            _viewModelState.value = Status.ERROR
            return
        }
        viewModelScope.launch {
            val params = GetCharacterByIdUserCase.Params(characterId)
            getCharacterByIdUserCase.invoke(params).collect { response ->
                response.data?.let {
                    totalComicsCount = it.comicsCount
                    _character.value = it
                }
                _viewModelState.value = response.status
            }
        }
    }

    /**
     * Get the character details.
     *
     * @param characterId The character unique identifier fer getting details.
     */
    fun getCharacterComics(
        characterId: Int,
        offset: Int = OFFSET_COMICS_FETCH_DEFAULT
    ) {
        if (totalComicsCount == 0) {
            _comics.value = listOf()
            return
        }

        viewModelScope.launch {
            val params = GetComicsByCharacterIdUserCase.Params(characterId, offset)
            getComicsByCharacterIdUserCase.invoke(params).collect { response ->
                if (response.status == Status.READY) {
                    response.data?.let {
                        _comics.value = it
                        comicsCount += it.size
                        if (comicsCount < totalComicsCount) {
                            getCharacterComics(characterId, comicsCount)
                        }
                    }
                }
            }
        }
    }
}