package dev.hyuwah.imusic.features.search.domain.usecase

import dev.hyuwah.imusic.core.common.domain.model.AsyncResult
import dev.hyuwah.imusic.features.search.domain.model.SearchMedia
import dev.hyuwah.imusic.features.search.domain.model.SearchResultModel
import dev.hyuwah.imusic.features.search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchMusicUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
) : SearchMusicUseCase {
    override suspend fun invoke(term: String): AsyncResult<SearchResultModel> {
        return searchRepository.search(term, SearchMedia.MUSIC)
    }
}