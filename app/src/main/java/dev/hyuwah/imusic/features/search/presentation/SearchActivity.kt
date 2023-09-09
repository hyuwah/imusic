package dev.hyuwah.imusic.features.search.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import dev.hyuwah.imusic.features.search.data.remote.response.ITunesSearchService
import dev.hyuwah.imusic.ui.theme.IMusicTheme
import javax.inject.Inject

@AndroidEntryPoint
class SearchActivity: ComponentActivity() {

    @Inject
    lateinit var iTunesSearchService: ITunesSearchService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IMusicTheme {

            }
        }
    }

}