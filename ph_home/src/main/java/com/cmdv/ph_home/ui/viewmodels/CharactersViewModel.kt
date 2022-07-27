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
    private val _viewModelState = MutableLiveData(ResponseWrapper.Status.LOADING)
    val viewModelState: LiveData<ResponseWrapper.Status>
        get() = _viewModelState

    var totalCharactersCount: Int = 0

    fun getTotalCharactersCount() {
        viewModelScope.launch {
            _viewModelState.value = ResponseWrapper.Status.LOADING
            val params = GetTotalCharactersUseCase.Params()
            getTotalCharactersUseCase(params).collect { response ->
                _viewModelState.value = response.status
                totalCharactersCount = response.data ?: 0
                // TODO
            }
        }
    }
}