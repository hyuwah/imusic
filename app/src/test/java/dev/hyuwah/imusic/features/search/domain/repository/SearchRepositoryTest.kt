package dev.hyuwah.imusic.features.search.domain.repository

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.StatusCode
import dev.hyuwah.imusic.core.common.domain.model.AsyncResult
import dev.hyuwah.imusic.core.common.domain.model.ErrorType
import dev.hyuwah.imusic.features.search.data.remote.ITunesSearchService
import dev.hyuwah.imusic.features.search.data.remote.response.SearchResponse
import dev.hyuwah.imusic.features.search.data.repository.SearchRepositoryImpl
import dev.hyuwah.imusic.features.search.domain.model.SearchMedia
import dev.hyuwah.imusic.features.search.domain.model.SearchResultModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class SearchRepositoryTest {

    @MockK
    private lateinit var service: ITunesSearchService

    private lateinit var repository: SearchRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = SearchRepositoryImpl(service)
    }

    @Test
    fun `search music returns success`() {
        // Arrange
        val expected = AsyncResult.Success(
            data = SearchResultModel(0, emptyList()),
            httpCode = StatusCode.OK
        )
        coEvery {
            service.search(any(), any())
        } coAnswers {
            ApiResponse.Success(Response.success(SearchResponse(0, emptyList())))
        }
        // Act
        val result = runBlocking {
            repository.search("query", SearchMedia.MUSIC)
        }
        // Assert
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `search music returns server error`() {
        // Arrange
        val expected = AsyncResult.Error(
            type = ErrorType.ServerError(
                httpCode = StatusCode.InternalServerError
            )
        )
        coEvery {
            service.search(any(), any())
        } coAnswers {
            ApiResponse.of { Response.error(500, "".toResponseBody()) }
        }
        // Act
        val result = runBlocking {
            repository.search("query", SearchMedia.MUSIC)
        }
        // Assert
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `search music returns exception error`() {
        // Arrange
        val exception = Exception("Generic Exception")
        val expected = AsyncResult.Error(
            type = ErrorType.ExceptionError(
                throwable = exception,
                message = exception.message
            )
        )
        coEvery {
            service.search(any(), any())
        } coAnswers {
            ApiResponse.error(exception)
        }
        // Act
        val result = runBlocking {
            repository.search("query", SearchMedia.MUSIC)
        }
        // Assert
        Assert.assertEquals(expected, result)
    }

}