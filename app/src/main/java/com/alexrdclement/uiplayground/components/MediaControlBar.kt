package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.util.UiPlaygroundPreview

data class Artist(val name: String)

data class MediaItem(
    val title: String,
    val artists: List<Artist>,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaControlBar(
    mediaItem: MediaItem,
    isPlaying: Boolean,
    onClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clickable { onClick() }
            .navigationBarsPadding()
    ) {
        MediaItemArtwork(
            modifier = Modifier
                .size(64.dp)
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = mediaItem.title,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                modifier = Modifier
                    .basicMarquee()
            )
            Text(
                text = mediaItem.artists.joinToString { it.name },
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                modifier = Modifier
                    .basicMarquee()
            )
        }

        PlayPauseButton(
            onClick = onPlayPauseClick,
            isPlaying = isPlaying,
            modifier = Modifier
                .size(52.dp)
                .padding(8.dp)
        )
    }
}

@Composable
fun MediaItemArtwork(
    modifier: Modifier,
) {
    Box(
        modifier = modifier
            .background(Color.Red)
    )
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        val mediaItem = MediaItem(
            title = "Title",
            artists = listOf(Artist("Artist 1"), Artist("Artist 2")),
        )
        var isPlaying by remember { mutableStateOf(false) }
        MediaControlBar(
            mediaItem = mediaItem,
            isPlaying = isPlaying,
            onClick = { /*TODO*/ },
            onPlayPauseClick = { isPlaying = !isPlaying }
        )
    }
}
