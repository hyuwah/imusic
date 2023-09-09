package dev.hyuwah.imusic.features.search.data.repository

import dev.hyuwah.imusic.core.common.data.toAsyncResult
import dev.hyuwah.imusic.core.common.domain.model.AsyncResult
import dev.hyuwah.imusic.features.search.data.mapper.toDomain
import dev.hyuwah.imusic.features.search.data.remote.response.ITunesSearchService
import dev.hyuwah.imusic.features.search.domain.model.SearchMedia
import dev.hyuwah.imusic.features.search.domain.model.SearchResultModel
import dev.hyuwah.imusic.features.search.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val iTunesSearchService: ITunesSearchService
): SearchRepository {
    override suspend fun search(term: String, media: SearchMedia): AsyncResult<SearchResultModel> {
        val apiResponse = iTunesSearchService.search(term, media.asQuery())
        return apiResponse.toAsyncResult { response ->
            response.toDomain()
        }
    }

}