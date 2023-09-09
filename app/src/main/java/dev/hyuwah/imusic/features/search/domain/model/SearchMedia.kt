package dev.hyuwah.imusic.features.search.domain.model

enum class SearchMedia {
    MUSIC;

    fun asQuery(): String = name.lowercase()
}