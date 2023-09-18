package dev.hyuwah.imusic.features.search.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.hyuwah.imusic.ui.theme.IMusicTheme

@AndroidEntryPoint
class SearchActivity: ComponentActivity() {

    private val viewModel by viewModels<SearchViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IMusicTheme {
                SearchScreen(
                    onEvent = viewModel::onEvent,
                    screenState = viewModel.screenState,
                    trackPlaybackState = viewModel.trackPlaybackState
                )
            }
        }
    }

}