package dev.hyuwah.imusic.features.search.domain.controller

import dev.hyuwah.imusic.features.search.domain.model.PlayerState
import dev.hyuwah.imusic.features.search.domain.model.SearchModel

interface PlaybackController {
    var mediaControllerCallback: ((
        playerState: PlayerState,
        currentTrack: SearchModel?,
        currentPosition: Long,
        totalDuration: Long
    ) -> Unit)?

    fun play(track: SearchModel)

    fun resume()

    fun pause()

    fun getCurrentPosition(): Long

    fun seekTo(position: Long)

    fun destroy()
}