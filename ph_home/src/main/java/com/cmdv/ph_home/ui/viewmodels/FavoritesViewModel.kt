package com.cmdv.ph_home.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.usecases.GetFavoriteCharactersUseCase
import com.cmdv.domain.usecases.RemoveAllFavoriteCharacterUseCase
import com.cmdv.domain.usecases.RemoveFavoriteCharacterUseCase
import com.cmdv.domain.utils.Event
import com.cmdv.domain.utils.ResponseWrapper.Status

class FavoritesViewModel(
    private val getFavoriteCharactersUseCase: GetFavoriteCharactersUseCase,
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase,
    private val removeAllFavoriteCharacterUseCase: RemoveAllFavoriteCharacterUseCase
) : ViewModel() {

    private val _viewModelState = MutableLiveData(Status.LOADING)
    val viewModelState: LiveData<Status>
        get() = _viewModelState

    private val _favoriteCharacters = MutableLiveData<List<CharacterModel>>()
    val favoriteCharacters: LiveData<List<CharacterModel>>
        get() = _favoriteCharacters

    private val _removeAll = MutableLiveData<Event<Int>>()
    val removeAll: LiveData<Event<Int>>
        get() = _removeAll

    init {
        getFavoritesCharacters()
    }

    fun getFavoritesCharacters() {
        TODO()
    }

    fun removeAll() {
        TODO()
    }

    private fun getCharacter(position: Int): CharacterModel {
        TODO()
    }

}