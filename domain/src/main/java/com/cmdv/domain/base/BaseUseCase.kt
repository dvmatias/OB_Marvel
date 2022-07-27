package com.cmdv.domain.base

import android.util.Log
import com.cmdv.domain.utils.ResponseWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext

/**
 * Base UseCase class. Every use case in the app should extend (be a subclass) of this [BaseUseCase] class. Subclassed
 * use cases must implement data binding pattern.
 *
 * @param R The response class type.
 * @param P The params class type
 */
abstract class BaseUseCase<R, in P> {

    /**
     * Function to override in each use cass subclass. This function declares the params class type and the return class
     * type.
     */
    abstract suspend fun executeUseCase(params: P): R

    /**
     * Function overloading. This function allows to provide custom implementation for the invoke operator.
     *
     * Uses callback flow to run the request, wait for the response and offer it once the request completes.
     */
    operator fun invoke(params: P): Flow<R> =
        callbackFlow {
            try {
                // Offer a response in which the status is Loading and the data is null
                trySend(withContext(Dispatchers.Main.immediate) {
                    @Suppress("UNCHECKED_CAST")
                    ResponseWrapper.loading(null) as R
                })
            } catch (e: Exception) {
                Log.e(BaseUseCase::class.java.name, "Error: $e")
            }
            // Trigger the actual BE request
            trySend(withContext(Dispatchers.Default) { executeUseCase(params) })
            awaitClose { cancel() }
        }
}