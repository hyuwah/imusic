package dev.hyuwah.imusic.features.search.domain.model

data class SearchResultModel(
    val counts: Int,
    val results: List<SearchModel>
)
