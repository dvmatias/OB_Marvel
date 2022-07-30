package com.cmdv.domain.utils

/**
 * Sealed class that holds failure types for FE to BE interactions.
 */
sealed class FailureType(open var message: String) {
	object None : FailureType("")
	class ConnectionError(override var message: String) : FailureType(message)
	class ServerError(override var message: String) : FailureType(message)
	class LocalError(override var message: String) : FailureType(message)
	object ResponseTransformError : FailureType("Transformation error.")
}