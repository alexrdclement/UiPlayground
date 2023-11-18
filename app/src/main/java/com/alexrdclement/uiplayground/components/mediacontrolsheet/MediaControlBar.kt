package com.alexrdclement.uiplayground.components.mediacontrolsheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaControlBar(
    mediaItem: MediaItem,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    modifier: Modifier = Modifier,
    minHeight: Dp = 64.dp,
    progress: () -> Float,
    onClick: () -> Unit = {},
) {
    BoxWithConstraints {
        val artworkSize = minHeight
        val maxWidthPx = this.constraints.maxWidth
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .fillMaxWidth()
                .clickable { onClick() }
        ) {
            MediaItemArtwork(
                modifier = Modifier
                    .align(Alignment.Top)
                    .size(artworkSize)
                    .graphicsLayer {
                        val computedProgress = progress()
                        val minHeightPx = minHeight.toPx()
                        val maxRatio = maxWidthPx / minHeightPx
                        val scale = 1f + (maxRatio - 1f) * computedProgress
                        val height = minHeightPx + ((maxWidthPx - minHeightPx) * computedProgress)
                        scaleX = scale
                        scaleY = scale
                        translationX = ((maxWidthPx) / 2f) * computedProgress - (size.width / 2f) * computedProgress
                        translationY = (height - minHeightPx) / 2f
                    }
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .graphicsLayer {
                        alpha = 1f - progress()
                    }
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
                    .graphicsLayer {
                        alpha = 1f - progress()
                    }
            )
        }
    }
}