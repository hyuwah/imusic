package dev.hyuwah.imusic.features.search.domain.model

data class SearchModel(
    val artistId: Int,
    val artistName: String,
    val artistViewUrl: String,
    val artworkUrl100: String,
    val collectionName: String,
    val collectionViewUrl: String,
    val isStreamable: Boolean,
    val previewUrl: String,
    val releaseDate: String,
    val trackId: Int,
    val trackName: String,
    val trackNumber: Int,
    val trackTimeMillis: Int?,
    val trackViewUrl: String,
    val wrapperType: SearchWrapperType
) {
    companion object {
        fun default(): SearchModel {
            return SearchModel(
                -1,
                "",
                "",
                "",
                "",
                "",
                false,
                "",
                "",
                -1,
                "",
                -1,
                null,
                "",
                SearchWrapperType.UNKNOWN
            )
        }
    }
}
