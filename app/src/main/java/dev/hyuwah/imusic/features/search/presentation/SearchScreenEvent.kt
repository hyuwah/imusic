package dev.hyuwah.imusic.features.search.presentation

import dev.hyuwah.imusic.features.search.domain.model.SearchModel

sealed interface SearchScreenEvent {
    data class PlayTrack(val selectedTrack: SearchModel) : SearchScreenEvent
    data object ResumeTrack : SearchScreenEvent
    data object PauseTrack : SearchScreenEvent
    data class SeekTrackPosition(val pos: Long): SearchScreenEvent
    data class OnTrackSelected(val selectedTrack: SearchModel) : SearchScreenEvent
    data class Search(val query: String) : SearchScreenEvent
    data class RemoveRecentSearch(val query: String) : SearchScreenEvent
    data object ClearRecentSearch : SearchScreenEvent
}