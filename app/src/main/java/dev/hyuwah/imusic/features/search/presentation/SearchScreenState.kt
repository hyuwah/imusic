package dev.hyuwah.imusic.features.search.presentation

import dev.hyuwah.imusic.core.common.domain.model.ErrorType
import dev.hyuwah.imusic.features.search.domain.model.SearchModel
import dev.hyuwah.imusic.features.search.domain.model.SearchResultModel

data class SearchScreenState(
    val isLoading: Boolean? = null,
    val searchResult: SearchResultModel? = null,
    val searchHistories: List<String> = listOf(),
    val selectedTrack: SearchModel? = null,
    val searchError: ErrorType? = null
)