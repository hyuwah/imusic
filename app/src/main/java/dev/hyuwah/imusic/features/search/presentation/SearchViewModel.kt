package dev.hyuwah.imusic.features.search.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.hyuwah.imusic.core.common.domain.model.AsyncResult
import dev.hyuwah.imusic.features.search.domain.usecase.SearchMusicUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val searchMusicUseCase: SearchMusicUseCase,
) : ViewModel() {

    var screenState by mutableStateOf(SearchScreenState())
        private set

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.Search -> {
                search(event.query)
            }
            is SearchScreenEvent.OnTrackSelected -> {
                screenState = screenState.copy(selectedTrack = event.selectedTrack)
            }

            SearchScreenEvent.PauseTrack -> {
                // Call usecase
            }

            SearchScreenEvent.PlayTrack -> {
                // Call usecase
            }

            SearchScreenEvent.ResumeTrack -> {
                // Call usecase
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

}