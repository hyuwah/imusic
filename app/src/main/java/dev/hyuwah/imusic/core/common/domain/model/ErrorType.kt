package dev.hyuwah.imusic.core.common.domain.model

import com.skydoves.sandwich.StatusCode

/**
 * Wrapper class for Error case of a particular data
 * As of now, only categorized into [ServerError] & [ExceptionError]
 * Can be extended for more specific case later
 */
sealed interface ErrorType {
    data class ServerError(
        val httpCode: StatusCode? = null
    ): ErrorType

    data class ExceptionError(
        val throwable: Throwable,
        val message: String? = null
    ): ErrorType
}