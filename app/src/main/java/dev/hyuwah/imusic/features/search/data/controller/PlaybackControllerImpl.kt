package dev.hyuwah.imusic.features.search.data.controller

import android.content.ComponentName
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors
import dev.hyuwah.imusic.core.common.data.service.MediaPlaybackService
import dev.hyuwah.imusic.features.search.data.mapper.toSearchModel
import dev.hyuwah.imusic.features.search.domain.controller.PlaybackController
import dev.hyuwah.imusic.features.search.domain.model.PlayerState
import dev.hyuwah.imusic.features.search.domain.model.SearchModel

class PlaybackControllerImpl(context: Context) : PlaybackController {

    private var mediaControllerFuture: ListenableFuture<MediaController>
    private val mediaController: MediaController?
        get() = if (mediaControllerFuture.isDone) mediaControllerFuture.get() else null

    override var mediaControllerCallback: ((
        playerState: PlayerState,
        currentTrack: SearchModel?,
        currentPosition: Long,
        totalDuration: Long
    ) -> Unit)? = null

    init {
        val sessionToken =
            SessionToken(context, ComponentName(context, MediaPlaybackService::class.java))
        mediaControllerFuture = MediaController.Builder(context, sessionToken).buildAsync()
        mediaControllerFuture.addListener({ controllerListener() }, MoreExecutors.directExecutor())
    }

    private fun controllerListener() {
        mediaController?.addListener(object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                with(player) {
                    mediaControllerCallback?.invoke(
                        playbackState.toPlayerState(isPlaying),
                        currentMediaItem?.toSearchModel(),
                        currentPosition.coerceAtLeast(0L),
                        duration.coerceAtLeast(0L)
                    )
                }
            }
        })
    }

    private fun Int.toPlayerState(isPlaying: Boolean) = when (this) {
        Player.STATE_IDLE, Player.STATE_ENDED -> PlayerState.STOPPED
        else -> if (isPlaying) PlayerState.PLAYING else PlayerState.PAUSED
    }

    override fun play(track: SearchModel) {
        val mediaItem = MediaItem.Builder()
            .setUri(Uri.parse(track.previewUrl))
            .setMediaId(track.previewUrl)
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setTitle(track.trackName)
                    .setArtist(track.artistName)
                    .setArtworkUri(track.artworkUrl100.toUri())
                    .setAlbumTitle(track.collectionName)
                    .setExtras(Bundle().apply {
                        putParcelable(SearchModel::class.java.simpleName, track)
                    })
                    .build()
            )
            .build()
        mediaController?.run {
            stop()
            setMediaItem(mediaItem, true)
            playWhenReady = true
            prepare()
        }
    }

    override fun resume() {
        mediaController?.play()
    }

    override fun getCurrentPosition(): Long {
        return mediaController?.currentPosition ?: 0L
    }

    override fun pause() {
        mediaController?.pause()
    }

    override fun seekTo(position: Long) {
        mediaController?.seekTo(position)
    }

    override fun destroy() {
        MediaController.releaseFuture(mediaControllerFuture)
        mediaControllerCallback = null
    }

}