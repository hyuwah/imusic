package dev.hyuwah.imusic.features.search.domain.usecase

import dev.hyuwah.imusic.features.search.domain.controller.PlaybackController
import dev.hyuwah.imusic.features.search.domain.model.PlayerState
import dev.hyuwah.imusic.features.search.domain.model.SearchModel
import javax.inject.Inject

class PlaybackControllerUseCaseImpl @Inject constructor(
    private val playbackController: PlaybackController
) : PlaybackControllerUseCase {

    override fun playTrack(track: SearchModel) {
        playbackController.play(track)
    }

    override fun pauseTrack() {
        playbackController.pause()
    }

    override fun resumeTrack() {
        playbackController.resume()
    }

    override fun getCurrentTrackPosition(): Long {
        return playbackController.getCurrentPosition()
    }

    override fun seekTrackPosition(position: Long) {
        playbackController.seekTo(position)
    }

    override fun setMediaControllerCallback(callback: (playerState: PlayerState, currentTrack: SearchModel?, currentPosition: Long, totalDuration: Long) -> Unit) {
        playbackController.mediaControllerCallback = callback
    }

    override fun destroyMediaController() {
        playbackController.destroy()
    }
}