package com.cmdv.data.errorhandling

import android.content.Context
import com.cmdv.common.R
import com.cmdv.data.errorhandling.CharacterDetailsApiErrorHandler.CharacterDetailsApiErrorCode.*
import com.cmdv.data.sources.apiservices.CharacterDetailsApi
import com.cmdv.domain.utils.FailureType
import com.cmdv.domain.utils.ResponseWrapper

/**
 * Error handler for [CharacterDetailsApi].
 *
 * @author matias.delv.dom@gmail.com
 */
class CharacterDetailsApiErrorHandler(context: Context) : ApiErrorHandler {
    private val resources by lazy { context.resources }

    override fun <M> handleError(code: Int): ResponseWrapper<M> {
        return ResponseWrapper.error(
            null,
            when (code) {
                INTERNET_CONNECTION.code -> FailureType.ServerError(resources.getString(R.string.error_character_details_server))
                TRANSFORMATION_ERROR.code -> FailureType.LocalError(resources.getString(R.string.error_character_details_transformation))
                CHARACTER_NOT_FOUND.code -> FailureType.ServerError(resources.getString(R.string.error_character_details_character_not_found))
                DATA_ERROR.code -> FailureType.ServerError(resources.getString(R.string.error_character_details_data))
                else -> FailureType.None
            }
        )
    }

    override fun <M> handleError(code: Int, failureType: FailureType): ResponseWrapper<M> {
        TODO("Not yet implemented")
    }

    /**
     * [CharacterDetailsApiErrorHandler] error codes definitions.
     */
    enum class CharacterDetailsApiErrorCode(val code: Int) {
        INTERNET_CONNECTION(400),
        TRANSFORMATION_ERROR(509),
        CHARACTER_NOT_FOUND(404),
        DATA_ERROR(409)
    }
}