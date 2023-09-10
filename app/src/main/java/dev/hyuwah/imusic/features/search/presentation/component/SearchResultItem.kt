package dev.hyuwah.imusic.features.search.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayCircleOutline
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import dev.hyuwah.imusic.R
import dev.hyuwah.imusic.features.search.domain.model.SearchModel
import dev.hyuwah.imusic.ui.theme.IMusicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultItem(
    model: SearchModel,
    isPlaying: Boolean,
    onClicked: () -> Unit
) {
    var elevation by remember { mutableStateOf(0.dp) }
    val playingImageTint by remember {
      mutableStateOf(ColorFilter.colorMatrix(
          ColorMatrix().apply {
              setToScale(0.5f, 0.5f, 0.5f, 1f)
          }
      ))
    }

    elevation = if (isPlaying) 6.dp else 0.dp

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.outlinedCardElevation(
            defaultElevation = elevation
        ),
        border = BorderStroke(1.dp, Color.LightGray),
        onClick = {
            onClicked()
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.size(100.dp),
                contentAlignment = Alignment.Center
            ) {
                CoilImage(
                    modifier = Modifier.size(100.dp),
                    imageModel = {
                        model.artworkUrl100
                    },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.FillBounds,
                        colorFilter = if (isPlaying) playingImageTint else null
                    ),
                    previewPlaceholder = R.drawable.genres
                )
                if (isPlaying) {
                    Icon(
                        modifier = Modifier.size(36.dp),
                        imageVector = Icons.Rounded.PlayCircleOutline,
                        contentDescription = null
                    )
                }
            }
            Spacer(modifier = Modifier.width(6.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = model.trackName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 6.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = model.artistName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = model.collectionName,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = model.releaseDate,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewSearchResultItem() {
    IMusicTheme {
        SearchResultItem(
            model = SearchModel.default().copy(
                artworkUrl100 = "https://is1-ssl.mzstatic.com/image/thumb/Music/v4/d0/b9/52/d0b9528d-c044-1c33-08ed-0913d8deedb6/05099924385657.jpg/100x100bb.jpg",
                artistName = "Artist",
                trackName = "Track Name",
                collectionName = "Album Name",
                releaseDate = "2022",
            ),
            isPlaying = true
        ) {

        }
    }
}