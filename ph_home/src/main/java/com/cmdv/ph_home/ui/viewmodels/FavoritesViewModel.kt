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
import com.cmdv.domain.utils.ResponseWrapper
import com.cmdv.domain.utils.ResponseWrapper.Status
import com.cmdv.domain.utils.ResponseWrapper.Status.*
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase,
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase,
    private val removeAllFavoriteCharacterUseCase: RemoveAllFavoriteCharacterUseCase
) : ViewModel() {

    private val _viewModelState = MutableLiveData(LOADING)
    val viewModelState: LiveData<Status> = _viewModelState

    private val _favoriteCharacters = MutableLiveData<List<CharacterModel>>()
    val favoriteCharacters: LiveData<List<CharacterModel>> = _favoriteCharacters

    private val _removeAll = MutableLiveData<Event<Int>>()
    val removeAll: LiveData<Event<Int>> = _removeAll

    init {
        getFavoritesCharacters()
    }

    /**
     * Get all user's favorite characters stored in DB.
     */
    fun getFavoritesCharacters() {
        viewModelScope.launch {
            _viewModelState.value = LOADING
            val params = GetFavoriteCharactersUseCase.Params()
            getFavoriteCharactersUseCase(params).collect { response ->
                _viewModelState.value = response.status
                if (response.status == READY) {
                    _favoriteCharacters.value = response.data
                }
            }
        }
    }

    /**
     * Remove all favorite characters from DB.
     */
    fun removeAll() {
        viewModelScope.launch {
            _viewModelState.value = LOADING
            val params = RemoveAllFavoriteCharacterUseCase.Params()
            removeAllFavoriteCharacterUseCase(params).collect { response ->
                _viewModelState.value = response.status
                response.data?.let { event ->
                    _removeAll.value = event
                }
            }
        }
    }
}