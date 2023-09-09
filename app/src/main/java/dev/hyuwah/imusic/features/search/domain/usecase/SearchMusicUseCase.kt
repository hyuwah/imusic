package dev.hyuwah.imusic.features.search.domain.usecase

import dev.hyuwah.imusic.core.common.domain.model.AsyncResult
import dev.hyuwah.imusic.features.search.domain.model.SearchResultModel

interface SearchMusicUseCase {
    suspend fun invoke(term: String): AsyncResult<SearchResultModel>
}