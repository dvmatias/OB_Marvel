package com.cmdv.ph_character_details.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmdv.domain.models.CharacterModel
import com.cmdv.domain.usecases.GetCharacterByIdUserCase
import com.cmdv.domain.utils.ResponseWrapper
import kotlinx.coroutines.launch

/**
 * Android View Model.
 */
class CharacterDetailsViewModel(
    private val getCharacterByIdUserCase: GetCharacterByIdUserCase
) : ViewModel() {
    /**
     * Represents the state of this view model (LOADING, READY, ERROR).
     */
    private val _viewModelState = MutableLiveData(ResponseWrapper.Status.LOADING)
    val viewModelState: LiveData<ResponseWrapper.Status>
        get() = _viewModelState

    private val _character = MutableLiveData<ResponseWrapper<CharacterModel>>()
    val character: LiveData<ResponseWrapper<CharacterModel>>
        get() = _character

    /**
     * Get the character details.
     *
     * @param characterId The character unique identifier fer getting details.
     */
    fun getCharacterDetails(characterId: Int) {
        viewModelScope.launch {
            val params = GetCharacterByIdUserCase.Params(characterId)
            getCharacterByIdUserCase.invoke(params).collect {
                _character.value = it
            }
        }
    }
}