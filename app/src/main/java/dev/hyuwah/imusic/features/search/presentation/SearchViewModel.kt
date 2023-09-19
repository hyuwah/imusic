package dev.hyuwah.imusic.features.search.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hyuwah.imusic.core.common.domain.model.AsyncResult
import dev.hyuwah.imusic.features.search.domain.model.PlayerState
import dev.hyuwah.imusic.features.search.domain.model.TrackPlaybackState
import dev.hyuwah.imusic.features.search.domain.usecase.PlaybackControllerUseCase
import dev.hyuwah.imusic.features.search.domain.usecase.SearchMusicUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val searchMusicUseCase: SearchMusicUseCase,
    private val playbackControllerUseCase: PlaybackControllerUseCase
) : ViewModel() {

    var screenState by mutableStateOf(SearchScreenState())
        private set

    var trackPlaybackState by mutableStateOf(TrackPlaybackState())
        private set

    init {
        setMediaControllerCallback()
    }

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.Search -> {
                search(event.query)
                addQueryToRecentSearch(event.query)
            }
            is SearchScreenEvent.RemoveRecentSearch -> {
                val recentQuery = screenState.searchHistories.toMutableList()
                recentQuery.remove(event.query)
                screenState = screenState.copy(
                    searchHistories = recentQuery
                )
            }
            SearchScreenEvent.ClearRecentSearch -> {
                screenState = screenState.copy(
                    searchHistories = emptyList()
                )
            }
            is SearchScreenEvent.OnTrackSelected -> {
                screenState = screenState.copy(selectedTrack = event.selectedTrack)
            }

            SearchScreenEvent.PauseTrack -> {
                playbackControllerUseCase.pauseTrack()
            }

            is SearchScreenEvent.PlayTrack -> {
                playbackControllerUseCase.playTrack(event.selectedTrack)
            }

            SearchScreenEvent.ResumeTrack -> {
                playbackControllerUseCase.resumeTrack()
            }

            is SearchScreenEvent.SeekTrackPosition -> {
                playbackControllerUseCase.seekTrackPosition(event.pos)
            }
        }
    }

    private fun search(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) return@launch
            screenState = screenState.copy(isLoading = true)
            screenState = when (val result = searchMusicUseCase.invoke(query)) {
                is AsyncResult.Error -> {
                    screenState.copy(isLoading = false, searchError = result.type)
                }

                AsyncResult.Loading -> {
                    screenState.copy(isLoading = true)
                }

                is AsyncResult.Success -> {
                    screenState.copy(isLoading = false, searchResult = result.data)
                }
            }
        }
    }

    private fun addQueryToRecentSearch(query: String) {
        viewModelScope.launch {
            delay(200)
            val newHistories = screenState.searchHistories.toMutableList()
            if (screenState.searchHistories.contains(query)) {
                newHistories.remove(query)
            }
            newHistories.add(0, query)
            screenState = screenState.copy(
                searchHistories = newHistories
            )
        }
    }

    private fun setMediaControllerCallback() {
        playbackControllerUseCase.setMediaControllerCallback { playerState, currentTrack, currentPosition, totalDuration ->
            trackPlaybackState = trackPlaybackState.copy(
                playerState = playerState,
                currentTrack = currentTrack,
                currentPosition = currentPosition,
                totalDuration = totalDuration
            )

            when (playerState) {
                PlayerState.PLAYING -> {
                    viewModelScope.launch {
                        while (true) {
                            delay(1.seconds)
                            trackPlaybackState = trackPlaybackState.copy(
                                currentPosition = playbackControllerUseCase.getCurrentTrackPosition()
                            )
                        }
                    }
                }
                PlayerState.STOPPED -> {
                    screenState = screenState.copy(selectedTrack = null)
                }
                PlayerState.PAUSED -> {}
            }

        }
    }

    private fun destroyMediaController() {
        playbackControllerUseCase.destroyMediaController()
    }

    override fun onCleared() {
        destroyMediaController()
        super.onCleared()

    }

}