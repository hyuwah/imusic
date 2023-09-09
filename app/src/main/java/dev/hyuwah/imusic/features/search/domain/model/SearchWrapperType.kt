package dev.hyuwah.imusic.features.search.domain.model

enum class SearchWrapperType {
    TRACK,
    COLLECTION,
    ARTIST,
    UNKNOWN;

    companion object {
        fun parseFrom(text: String): SearchWrapperType {
            return values().find { it.name == text.uppercase() } ?: UNKNOWN
        }
    }
}