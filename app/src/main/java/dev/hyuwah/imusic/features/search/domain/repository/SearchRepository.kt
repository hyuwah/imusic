package dev.hyuwah.imusic.features.search.domain.repository

import dev.hyuwah.imusic.core.common.domain.model.AsyncResult
import dev.hyuwah.imusic.features.search.domain.model.SearchMedia
import dev.hyuwah.imusic.features.search.domain.model.SearchResultModel

interface SearchRepository {
    suspend fun search(term: String, media: SearchMedia): AsyncResult<SearchResultModel>
}