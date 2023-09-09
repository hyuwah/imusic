package dev.hyuwah.imusic.features.search.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import dev.hyuwah.imusic.R
import dev.hyuwah.imusic.ui.theme.IMusicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultItem(
    thumbnailUrl: String,
    artistName: String,
    trackName: String,
    collectionName: String,
    releaseDate: String,
    isPlaying: Boolean,
    onClicked: () -> Unit
) {
    val elevation = if (isPlaying) {
        CardDefaults.outlinedCardElevation()
    } else {
        CardDefaults.outlinedCardElevation(
            defaultElevation = 6.dp
        )
    }
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = elevation,
        border = BorderStroke(1.dp, Color.LightGray),
        onClick = {
            onClicked()
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            CoilImage(
                modifier = Modifier.size(100.dp),
                imageModel = {
                    thumbnailUrl
                },
                imageOptions = ImageOptions(contentScale = ContentScale.FillBounds),
                previewPlaceholder = R.drawable.genres
            )
            Spacer(modifier = Modifier.width(6.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = trackName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 6.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = artistName,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier
                        .fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = collectionName,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = releaseDate,
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
            thumbnailUrl = "https://is1-ssl.mzstatic.com/image/thumb/Music/v4/d0/b9/52/d0b9528d-c044-1c33-08ed-0913d8deedb6/05099924385657.jpg/100x100bb.jpg",
            artistName = "Artist",
            trackName = "Track Name",
            collectionName = "Album Name",
            releaseDate = "2022",
            isPlaying = true
        ) {

        }
    }
}