package com.cmdv.data.errorhandling

import android.content.Context
import com.cmdv.common.R
import com.cmdv.data.errorhandling.CharactersApiErrorHandler.CharactersApiErrorCode.*
import com.cmdv.data.sources.apiservices.CharactersApi
import com.cmdv.domain.utils.FailureType
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Error handler for [CharactersApi].
 */
class CharactersApiErrorHandler(context: Context) : ApiErrorHandler {
    private val resources by lazy { context.resources }

    override fun <M> handleError(code: Int): ResponseWrapper<M> {
        return ResponseWrapper.error(
            null,
            when (code) {
                INTERNET_CONNECTION.code -> FailureType.ServerError(resources.getString(R.string.error_characters_connection))
                TRANSFORMATION_ERROR.code -> FailureType.LocalError(resources.getString(R.string.error_characters_transformation))
                OTHERS.code -> FailureType.LocalError(resources.getString(R.string.error_characters_others))
                else -> FailureType.None
            }
        )
    }

    override fun <M> handleError(code: Int, failureType: FailureType): ResponseWrapper<M> {
        TODO("Not yet implemented")
    }

    /**
     * [CharactersApi] error codes definitions.
     */
    enum class CharactersApiErrorCode(val code: Int) {
        INTERNET_CONNECTION(400),
        TRANSFORMATION_ERROR(509),
        OTHERS(409)
    }
}