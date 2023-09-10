package dev.hyuwah.imusic.features.search.domain.usecase

import dev.hyuwah.imusic.features.search.domain.model.PlayerState
import dev.hyuwah.imusic.features.search.domain.model.SearchModel

interface PlaybackControllerUseCase {
    fun playTrack(track: SearchModel)
    fun pauseTrack()
    fun resumeTrack()
    fun getCurrentTrackPosition(): Long
    fun seekTrackPosition(position: Long)
    fun setMediaControllerCallback(
        callback: (
            playerState: PlayerState,
            currentTrack: SearchModel?,
            currentPosition: Long,
            totalDuration: Long,
        ) -> Unit
    )
    fun destroyMediaController()
}