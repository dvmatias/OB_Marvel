package com.cmdv.domain.utils

import java.io.Serializable

/**
 * Wrapper class. This class is a wrapper over any BE response.
 *
 * @param status Represents the response status.
 * @param data Represents the actual data/information required in the response.
 * @param failureType Represents the failure (if any) in a FE to BE interaction request.
 */
class ResponseWrapper<out T>(
    val status: Status,
    val data: T?,
    val failureType: FailureType?
) : Serializable {

    /**
     * Enum class to describe possible FE to BE request status.
     */
    enum class Status {
        LOADING,
        ERROR,
        READY
    }

    companion object {
        /**
         * Executed when a request ends up successfully.
         */
        fun <T> success(data: T?): ResponseWrapper<T> =
            ResponseWrapper(Status.READY, data, null)

        /**
         * Executed when a request ends up not successfully.
         */
        fun <T> error(data: T? = null, failureType: FailureType): ResponseWrapper<T> =
            ResponseWrapper(Status.ERROR, data, failureType)

        /**
         * Executed when a request has just being made and is not completed.
         */
        fun <T> loading(data: T? = null): ResponseWrapper<T> =
            ResponseWrapper(Status.LOADING, data, null)
    }
}