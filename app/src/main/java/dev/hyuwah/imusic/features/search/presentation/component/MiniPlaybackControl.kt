package dev.hyuwah.imusic.features.search.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import dev.hyuwah.imusic.R
import dev.hyuwah.imusic.core.utilities.DateTimeUtils.getYear
import dev.hyuwah.imusic.core.utilities.DateTimeUtils.toTime
import dev.hyuwah.imusic.features.search.domain.model.PlayerState
import dev.hyuwah.imusic.features.search.domain.model.SearchModel
import dev.hyuwah.imusic.ui.theme.IMusicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiniPlaybackControl(
    modifier: Modifier = Modifier,
    track: SearchModel?,
    playerState: PlayerState?,
    seekbarPos: Long,
    seekbarDuration: Long,
    onSeekbarChanged: (Long) -> Unit,
    onResumeClicked: () -> Unit,
    onPauseClicked: () -> Unit,
    onClick: () -> Unit
) {
    val releaseYear by remember {
        mutableStateOf(track?.releaseDate?.getYear().orEmpty())
    }
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(modifier = Modifier.weight(1f)) {
                track?.run {
                    CoilImage(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(MaterialTheme.shapes.small),
                        imageModel = {
                            artworkUrl100
                        },
                        previewPlaceholder = R.drawable.genres,
                        imageOptions = ImageOptions(contentScale = ContentScale.FillBounds),
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = trackName,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = artistName,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "$collectionName (${releaseYear})",
                            style = MaterialTheme.typography.labelLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(15.dp))
            FloatingActionButton(
                elevation = FloatingActionButtonDefaults.loweredElevation(),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                onClick = {
                    when (playerState) {
                        PlayerState.PLAYING -> onPauseClicked()
                        PlayerState.PAUSED -> onResumeClicked()
                        else -> {}
                    }
                }
            ) {
                Icon(
                    imageVector = if (playerState == PlayerState.PLAYING) {
                        Icons.Rounded.Pause
                    } else {
                        Icons.Rounded.PlayArrow
                    },
                    contentDescription = null
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Preview",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.tertiaryContainer, shape = RoundedCornerShape(6.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.tertiary,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(vertical = 4.dp, horizontal = 6.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                Slider(
                    value = seekbarPos.toFloat(),
                    valueRange = 0f..seekbarDuration.toFloat(),
                    onValueChange = {
                        onSeekbarChanged.invoke(it.toLong())
                    },
                    colors = SliderDefaults.colors(
                        inactiveTrackColor = MaterialTheme.colorScheme.inversePrimary
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${seekbarPos.toTime()} / ${seekbarDuration.toTime()}",
                style = MaterialTheme.typography.labelMedium
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun previewMiniPlaybackControl() {
    IMusicTheme {
        MiniPlaybackControl(
            track = SearchModel.default().copy(
                artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music/v4/d0/b9/52/d0b9528d-c044-1c33-08ed-0913d8deedb6/05099924385657.jpg/100x100bb.jpg",
                artistName = "Artist",
                trackName = "Track Name 1",
                collectionName = "Album Name",
                releaseDate = "2022",
            ),
            playerState = PlayerState.PLAYING,
            seekbarPos = 50,
            seekbarDuration = 100,
            onSeekbarChanged = {},
            onResumeClicked = {},
            onPauseClicked = {}) {

        }
    }
}