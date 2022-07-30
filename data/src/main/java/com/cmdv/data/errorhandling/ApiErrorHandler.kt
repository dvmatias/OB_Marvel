package com.cmdv.data.errorhandling

import com.cmdv.domain.utils.FailureType
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Interface . Contract to support error handling. Each API class can have an implementation of this class in which the
 * failure type should be associated to a error message according the code or nature of the API call error.
 */
interface  ApiErrorHandler {
    fun <M> handleError(code: Int): ResponseWrapper<M>
    fun <M> handleError(code: Int, failureType: FailureType): ResponseWrapper<M>
}