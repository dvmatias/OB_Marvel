package com.cmdv.ph_home.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmdv.domain.uimodels.CharacterUiModel
import com.cmdv.domain.usecases.GetCharactersUseCase
import com.cmdv.domain.usecases.GetTotalCharactersUseCase
import com.cmdv.domain.utils.ResponseWrapper
import kotlinx.coroutines.launch

private const val LIMIT_CHARACTERS_FETCH_DEFAULT = 32
private const val OFFSET_CHARACTERS_FETCH_DEFAULT = 0

class CharactersViewModel(
    private val getTotalCharactersUseCase: GetTotalCharactersUseCase,
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {
    /**
     * Represents the state of this view model (LOADING, READY, ERROR).
     */
    private val _viewModelState = MutableLiveData(ResponseWrapper.Status.LOADING)
    val viewModelState: LiveData<ResponseWrapper.Status>
        get() = _viewModelState

    private var _characters = MutableLiveData<List<CharacterUiModel>>()
    val characters: LiveData<List<CharacterUiModel>> = _characters

    private val isAllCharactersLoaded: Boolean
        get() = _characters.value?.size.let { totalCharactersCount == it }

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

    fun getCharacters(
        fetch: Boolean,
        limit: Int = LIMIT_CHARACTERS_FETCH_DEFAULT,
        offset: Int = OFFSET_CHARACTERS_FETCH_DEFAULT
    ) {
        if (!isAllCharactersLoaded) {
            _viewModelState.value = ResponseWrapper.Status.LOADING
            val params = GetCharactersUseCase.Params(fetch, limit, offset)
            viewModelScope.launch {
                getCharactersUseCase(params).collect { response ->
                    with(response) {
                        if (status == ResponseWrapper.Status.READY) {
                            data?.let { _characters.value = it }
                        }
                        _viewModelState.value = status
                    }
                }
            }
        }
    }
}