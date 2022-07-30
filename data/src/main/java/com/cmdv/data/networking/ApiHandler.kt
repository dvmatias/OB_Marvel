package com.cmdv.data.networking

import android.util.Log
import com.cmdv.data.errorhandling.ApiErrorHandler
import com.cmdv.domain.utils.FailureType
import com.cmdv.domain.utils.ResponseWrapper
import retrofit2.Call

private const val TAG = "ApiHandler"

/**
 * Utility class to perform network requests
 *
 * @param networkHandler Offers information regarding network state.
 */
class ApiHandler(private val networkHandler: NetworkHandler) {
    /**
     * Function to perform and handle network requests.
     *
     * @param call Retrofit service call instance to execute.
     * @param transformResponse Function that provides a data transformation.
     * @param errorHandler Helper class that declares errors for a specific network call.
     * @return This function returns an implementation of [ResponseWrapper] with a given type.
     */
    fun <E, M> doNetworkRequest(
        call: Call<E>,
        errorHandler: ApiErrorHandler? = null,
        transformResponse: (E) -> M
    ): ResponseWrapper<M> {
        return try {
            when (networkHandler.isConnected) {
                true -> {
                    // Execute the service call to get a response
                    val response = call.execute()
                    with(response) {
                        val body = response.body()
                        if (isSuccessful && body != null) {
                            try {
                                // Try to transform the response in order to return it properly formatted
                                ResponseWrapper.success(transformResponse(body))
                            } catch (e: Exception) {
                                Log.d(TAG, "Network error: response transformation did not complete successfully")
                                // Respond with error if transformation fails
                                errorHandler?.handleError(509) ?: ResponseWrapper.error(failureType = FailureType.ResponseTransformError)
                            }
                        } else {
                            Log.d(TAG, "Network error: request did not complete successfully")
                            errorHandler?.handleError(response.code()) ?: ResponseWrapper.error(failureType = FailureType.ServerError(response.message()))
                        }
                    }
                }
                false, null -> {
                    Log.d(TAG, "Network error: Internet connection problems")
                    errorHandler?.handleError(400) ?: ResponseWrapper.error(failureType = FailureType.ConnectionError("Network error: Internet connection problems"))
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "Network error: $e")
            ResponseWrapper.error(null, FailureType.ServerError(""))
        }
    }
}