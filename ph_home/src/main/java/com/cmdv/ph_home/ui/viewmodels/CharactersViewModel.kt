package com.cmdv.ph_home.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cmdv.domain.usecases.GetTotalCharactersUseCase
import com.cmdv.domain.utils.ResponseWrapper
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val getTotalCharactersUseCase: GetTotalCharactersUseCase
) : ViewModel() {
    /**
     * Represents the state of this view model (LOADING, READY, ERROR).
     */
    private val _viewModelState = MutableLiveData(ResponseWrapper.Status.LOADING)
    val viewModelState: LiveData<ResponseWrapper.Status>
        get() = _viewModelState

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
                    _viewModelState.value = status
                    totalCharactersCount = data ?: 0
                    // TODO
                }
            }
        }
    }
}