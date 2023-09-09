package dev.hyuwah.imusic.core.common.domain.model

import com.skydoves.sandwich.StatusCode

/**
 * Wrapper model for data flow from Data layer through Presentation layer
 * `Async` naming indicates that the data is async / can have loading state
 */
sealed interface AsyncResult<out T> {
    data object Loading: AsyncResult<Nothing>
    data class Success<T>(val data: T, val httpCode: StatusCode? = null): AsyncResult<T>
    data class Error(val type: ErrorType): AsyncResult<Nothing>
}