package dev.hyuwah.imusic.features.search.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.hyuwah.imusic.core.common.domain.model.ErrorType
import dev.hyuwah.imusic.features.search.domain.model.PlayerState
import dev.hyuwah.imusic.features.search.domain.model.SearchModel
import dev.hyuwah.imusic.features.search.domain.model.SearchResultModel
import dev.hyuwah.imusic.features.search.domain.model.TrackPlaybackState
import dev.hyuwah.imusic.features.search.presentation.component.MiniPlaybackControl
import dev.hyuwah.imusic.features.search.presentation.component.RecentSearchQueries
import dev.hyuwah.imusic.features.search.presentation.component.SearchResultItem
import dev.hyuwah.imusic.ui.theme.IMusicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onEvent: (SearchScreenEvent) -> Unit,
    screenState: SearchScreenState,
    trackPlaybackState: TrackPlaybackState,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    var searchQuery by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(title = { Text(text = "iMusic") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
        ) {
            SearchBar(
                query = searchQuery,
                onQueryChange = {
                    searchQuery = it
                },
                onSearch = {
                    if (searchQuery.isNotBlank()) {
                        onEvent(SearchScreenEvent.Search(it.trim()))
                    } else {
                        searchQuery = ""
                    }
                    active = false
                },
                active = active,
                onActiveChange = {
                    active = it
                },
                placeholder = {
                    Text(text = "Search artist / songs here")
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    if (active) {
                        Icon(
                            modifier = Modifier.clickable {
                                if (searchQuery.isNotEmpty()) {
                                    searchQuery = ""
                                } else {
                                    active = false
                                }
                            },
                            imageVector = Icons.Default.Close,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                RecentSearchQueries(
                    searchHistories = screenState.searchHistories,
                    onItemClick = { query ->
                        if (query != searchQuery) {
                            searchQuery = query
                            if (searchQuery.isNotBlank()) {
                                onEvent(SearchScreenEvent.Search(query))
                            } else {
                                searchQuery = ""
                            }
                        }
                        active = false
                    },
                    onClearAll = {
                        onEvent(SearchScreenEvent.ClearRecentSearch)
                    },
                    onRemoveItem = { query ->
                        onEvent(SearchScreenEvent.RemoveRecentSearch(query))
                    },
                )
            }
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                with(screenState) {
                    when {
                        isLoading == true -> {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentSize(Alignment.Center)
                            )
                        }

                        isLoading == false && searchError == null -> {
                            val searchResults = searchResult?.results
                            if (!searchResults.isNullOrEmpty()) {
                                LazyColumn(
                                    contentPadding = PaddingValues(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 16.dp,
                                        bottom = 48.dp
                                    ),
                                    verticalArrangement = Arrangement.spacedBy(12.dp),
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    items(searchResults, key = { it.hashCode() }) { model ->
                                        SearchResultItem(
                                            model = model,
                                            isPlaying = selectedTrack == model
                                        ) {
                                            onEvent(SearchScreenEvent.OnTrackSelected(model))
                                            if (model == screenState.selectedTrack && trackPlaybackState.playerState == PlayerState.PLAYING) {
                                                onEvent(SearchScreenEvent.PauseTrack)
                                            } else {
                                                onEvent(SearchScreenEvent.PlayTrack(model))
                                            }
                                        }
                                    }
                                }
                            } else {
                                Text(
                                    text = "No result found for \"$searchQuery\"",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                        searchError != null -> {
                            LaunchedEffect(snackbarHostState) {
                                snackbarHostState.showSnackbar(
                                    message = when (searchError) {
                                        is ErrorType.ExceptionError -> {
                                            searchError.message.orEmpty()
                                        }

                                        is ErrorType.ServerError -> {
                                            "Server error (${searchError.httpCode})"
                                        }
                                    }
                                )
                            }
                        }
                    }
                }

                with(trackPlaybackState) {
                    if (playerState != null && playerState != PlayerState.STOPPED) {
                        MiniPlaybackControl(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(horizontal = 12.dp, vertical = 16.dp),
                            track = currentTrack,
                            playerState = playerState,
                            seekbarPos = currentPosition,
                            seekbarDuration = totalDuration,
                            onSeekbarChanged = { onEvent(SearchScreenEvent.SeekTrackPosition(it)) },
                            onResumeClicked = { onEvent(SearchScreenEvent.ResumeTrack) },
                            onPauseClicked = { onEvent(SearchScreenEvent.PauseTrack) }) {
                            // [enhancement] Open Playback Control Detail
                        }
                    }
                }
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun previewSearchScreen() {
    IMusicTheme {
        SearchScreen(
            onEvent = {},
            screenState = SearchScreenState(
                isLoading = false,
                searchResult = SearchResultModel(
                    2,
                    listOf(
                        SearchModel.default().copy(
                            artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music/v4/d0/b9/52/d0b9528d-c044-1c33-08ed-0913d8deedb6/05099924385657.jpg/100x100bb.jpg",
                            artistName = "Artist",
                            trackName = "Track Name 1",
                            collectionName = "Album Name",
                            releaseDate = "2022",
                        ),
                        SearchModel.default().copy(
                            artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music/v4/d0/b9/52/d0b9528d-c044-1c33-08ed-0913d8deedb6/05099924385657.jpg/100x100bb.jpg",
                            artistName = "Artist",
                            trackName = "Track Name 2",
                            collectionName = "Album Name",
                            releaseDate = "2023",
                        )
                    )
                )
            ),
            trackPlaybackState = TrackPlaybackState()
        )
    }
}