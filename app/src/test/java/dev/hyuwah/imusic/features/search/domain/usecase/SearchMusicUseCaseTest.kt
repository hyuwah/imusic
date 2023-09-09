package dev.hyuwah.imusic.features.search.domain.usecase

import com.skydoves.sandwich.StatusCode
import dev.hyuwah.imusic.core.common.domain.model.AsyncResult
import dev.hyuwah.imusic.core.common.domain.model.ErrorType
import dev.hyuwah.imusic.features.search.domain.model.SearchResultModel
import dev.hyuwah.imusic.features.search.domain.repository.SearchRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchMusicUseCaseTest {

    @MockK
    private lateinit var repository: SearchRepository

    private lateinit var useCase: SearchMusicUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = SearchMusicUseCaseImpl(repository)
    }

    @Test
    fun `search music returns success`() {
        // Arrange
        val expected = AsyncResult.Success(
            data = SearchResultModel(0, emptyList()),
            httpCode = StatusCode.OK
        )
        coEvery { repository.search(any(), any()) } coAnswers { expected }
        // Act
        val result = runBlocking {
            useCase.invoke("query")
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
        coEvery { repository.search(any(), any()) } coAnswers { expected }
        // Act
        val result = runBlocking {
            useCase.invoke("query")
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
        coEvery { repository.search(any(), any()) } coAnswers { expected }
        // Act
        val result = runBlocking {
            useCase.invoke("query")
        }
        // Assert
        Assert.assertEquals(expected, result)
    }

}