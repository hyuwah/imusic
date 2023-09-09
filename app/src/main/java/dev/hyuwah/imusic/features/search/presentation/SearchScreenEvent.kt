package dev.hyuwah.imusic.features.search.presentation

import dev.hyuwah.imusic.features.search.domain.model.SearchModel

sealed interface SearchScreenEvent {
    data object PlayTrack : SearchScreenEvent
    data object ResumeTrack : SearchScreenEvent
    data object PauseTrack : SearchScreenEvent
    data class OnTrackSelected(val selectedTrack: SearchModel) : SearchScreenEvent
    data class Search(val query: String) : SearchScreenEvent
}