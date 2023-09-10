package dev.hyuwah.imusic.features.search.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
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
): Parcelable {
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
