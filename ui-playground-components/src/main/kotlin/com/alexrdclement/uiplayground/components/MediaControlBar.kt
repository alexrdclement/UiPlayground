package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

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
        val maxWidthPx = this.constraints.maxWidth

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .heightIn(minHeight)
                .fillMaxWidth()
                .clickable { onClick() }
        ) {
            MediaItemArtwork(
                modifier = Modifier
                    .layout { measurable, constraints ->
                        val computedProgress = progress()
                        val minHeightPx = minHeight.toPx()
                        val height = minHeightPx + ((maxWidthPx - minHeightPx) * computedProgress)

                        val placeable = measurable.measure(
                            constraints.copy(
                                minHeight = minHeightPx.roundToInt(),
                                minWidth = minHeightPx.roundToInt(),
                                maxHeight = height.roundToInt(),
                                maxWidth = height.roundToInt(),
                            )
                        )
                        layout(height.roundToInt(), height.roundToInt()) {
                            placeable.place(0, 0)
                        }
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
