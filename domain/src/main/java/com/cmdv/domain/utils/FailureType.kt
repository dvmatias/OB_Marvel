package com.cmdv.domain.utils

/**
 * Sealed class that holds failure types for FE to BE interactions.
 */
sealed class FailureType {
	object ConnectionError : FailureType()
	object ServerError : FailureType()
	object LocalError : FailureType()
	object ResponseTransformError : FailureType()
}