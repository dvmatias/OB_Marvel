package com.cmdv.data.networking

import android.util.Log
import com.cmdv.domain.utils.FailureType
import com.cmdv.domain.utils.ResponseWrapper
import retrofit2.Call

private const val TAG = "ServiceCallHandler"

/**
 * Utility class to perform network requests
 *
 * @param networkHandler Offers information regarding network state.
 */
open class ServiceCallHandler(private val networkHandler: NetworkHandler) {
    /**
     * Function to perform and handle network requests.
     *
     * @param call Retrofit service call instance to execute.
     * @param transformResponse Function that provides a data transformation.
     * @return This function returns an implementation of [ResponseWrapper] with a given type.
     */
    open fun <E, M> doNetworkRequest(call: Call<E>, transformResponse: (E) -> M): ResponseWrapper<M> {
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
                                ResponseWrapper.error(failureType = FailureType.ResponseTransformError)
                            }
                        } else {
                            Log.d(TAG, "Network error: request did not complete successfully")
                            ResponseWrapper.error(failureType = FailureType.ServerError)
                        }
                    }
                }
                false, null -> {
                    Log.d(TAG, "Network error: Internet connection problems")
                    ResponseWrapper.error(failureType = FailureType.ConnectionError)
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "Network error: $e")
            ResponseWrapper.error(null, FailureType.ServerError)
        }
    }
}