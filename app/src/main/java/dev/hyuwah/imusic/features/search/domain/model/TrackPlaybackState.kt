package dev.hyuwah.imusic.features.search.domain.model

data class TrackPlaybackState(
    val playerState: PlayerState? = null,
    val currentTrack: SearchModel? = null,
    val currentPosition: Long = 0L,
    val totalDuration: Long = 0L,
)
