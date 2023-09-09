package dev.hyuwah.imusic.core.common.data

import com.skydoves.sandwich.ApiResponse
import dev.hyuwah.imusic.core.common.domain.model.AsyncResult
import dev.hyuwah.imusic.core.common.domain.model.ErrorType

/**
 * Convenience method to transform  Skydoves's [Sandwich](https://github.com/skydoves/sandwich) [ApiResponse] into our [AsyncResult]
 * @param mapper function to map Response into Domain Model
 */
fun <DATA, DOMAIN> ApiResponse<DATA>.toAsyncResult(
    mapper: (DATA) -> DOMAIN
): AsyncResult<DOMAIN> {
    return when (this) {
        is ApiResponse.Failure.Error -> {
            AsyncResult.Error(
                type = ErrorType.ServerError(
                    httpCode = statusCode
                )
            )
        }
        is ApiResponse.Failure.Exception -> {
            AsyncResult.Error(
                type = ErrorType.ExceptionError(
                    throwable = exception,
                    message = message
                )
            )
        }
        is ApiResponse.Success -> {
            AsyncResult.Success(
                data = mapper(data),
                httpCode = statusCode
            )
        }
    }
}