package dev.hyuwah.imusic.features.search.data.mapper

import dev.hyuwah.imusic.features.search.data.remote.response.SearchResponse
import dev.hyuwah.imusic.features.search.domain.model.SearchModel
import dev.hyuwah.imusic.features.search.domain.model.SearchResultModel
import dev.hyuwah.imusic.features.search.domain.model.SearchWrapperType

fun SearchResponse.toDomain(): SearchResultModel {
    return SearchResultModel(
        counts = resultCount ?: 0,
        results = results.orEmpty().map { it.toDomain() }
    )
}

fun SearchResponse.Result.toDomain(): SearchModel {
    return SearchModel(
        artistId = artistId ?: -1,
        artistName = artistName.orEmpty(),
        artistViewUrl = artistViewUrl.orEmpty(),
        artworkUrl100 = artworkUrl100.orEmpty(),
        collectionName = collectionName.orEmpty(),
        collectionViewUrl = collectionViewUrl.orEmpty(),
        isStreamable = isStreamable ?: false,
        previewUrl = previewUrl.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        trackId = trackId ?: -1,
        trackName = trackName.orEmpty(),
        trackNumber = trackNumber ?: -1,
        trackTimeMillis = trackTimeMillis,
        trackViewUrl = trackViewUrl.orEmpty(),
        wrapperType = SearchWrapperType.parseFrom(wrapperType.orEmpty())
    )
}