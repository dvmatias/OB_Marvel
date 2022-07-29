package com.cmdv.domain.utils

/**
 * Sealed class that holds failure types for FE to BE interactions.
 */
sealed class FailureType {
	object None : FailureType()
	object ConnectionError : FailureType()
	object ServerError : FailureType()
	class LocalError(val message: String) : FailureType()
	object ResponseTransformError : FailureType()
}