package com.alexrdclement.uiplayground.components.media

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.trace
import com.alexrdclement.uiplayground.components.MediaControlBarContentDescription
import com.alexrdclement.uiplayground.components.core.Surface
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.components.media.model.Artist
import com.alexrdclement.uiplayground.components.media.model.MediaItem
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import kotlin.math.roundToInt

private const val TraceName = "MediaControlBar"
private const val ArtworkTraceName = "$TraceName:MediaItemArtwork"

@Composable
fun MediaControlBar(
    mediaItem: MediaItem,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    modifier: Modifier = Modifier,
    minContentSize: DpSize = DpSize(width = 64.dp, height = 64.dp),
    maxContentSize: DpSize = DpSize(width = Dp.Infinity, height = Dp.Infinity),
    progress: () -> Float = { 0f },
    onClick: () -> Unit = {},
    stateDescription: String? = null,
) {
    trace(TraceName) {
        BoxWithConstraints {
            val density = LocalDensity.current
            val (minContentWidth, minContentHeight) = with(density) {
                maxOf(minContentSize.width.toPx(), constraints.minWidth.toFloat()) to
                    maxOf(minContentSize.height.toPx(), constraints.minHeight.toFloat())
            }
            val (maxContentWidth, maxContentHeight) = with(density) {
                minOf(maxContentSize.width.toPx(), constraints.maxWidth.toFloat()) to
                    minOf(maxContentSize.height.toPx(), constraints.maxHeight.toFloat())
            }
            val contentStartX = 0f
            val contentEndX = with(density) {
                (constraints.maxWidth - maxContentWidth) / 2f
            }
            val contentStartY = 0f
            val contentEndY = 0f

            Surface {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .heightIn(minHeight)
                        .fillMaxWidth()
                        .clickable { onClick() }
                        .semantics {
                            contentDescription =
                                MediaControlBarContentDescription
                            stateDescription?.let {
                                this@semantics.stateDescription = it
                            }
                        }
                ) {
                    MediaItemArtwork(
                        imageUrl = mediaItem.artworkLargeUrl,
                        modifier = Modifier
                            .layout { measurable, constraints ->
                                trace("$ArtworkTraceName:measure") {
                                    val computedProgress = progress()

                                    val width = minContentWidth +
                                        ((maxContentWidth - minContentWidth) * computedProgress)
                                    val height = minContentHeight +
                                        ((maxContentHeight - minContentHeight) * computedProgress)

                                    val x = contentStartX + ((contentEndX - contentStartX) * computedProgress)
                                    val y = contentStartY + ((contentEndY - contentStartY) * computedProgress)

                                    val placeable = measurable.measure(
                                        constraints.copy(
                                            minWidth = width.roundToInt(),
                                            minHeight = height.roundToInt(),
                                            maxWidth = width.roundToInt(),
                                            maxHeight = height.roundToInt(),
                                        )
                                    )
                                    layout(width.roundToInt(), height.roundToInt()) {
                                        placeable.place(x.roundToInt(), y.roundToInt())
                                    }
                                }
                            }
                    )

                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = PlaygroundTheme.spacing.small)
                            .graphicsLayer {
                                alpha = 1f - progress()
                            }
                    ) {
                        Text(
                            text = mediaItem.title,
                            style = PlaygroundTheme.typography.titleMedium,
                            maxLines = 1,
                            modifier = Modifier
                                .basicMarquee()
                        )
                        Text(
                            text = mediaItem.artists.joinToString { it.name },
                            style = PlaygroundTheme.typography.bodyMedium,
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
                            .padding(PlaygroundTheme.spacing.small)
                            .graphicsLayer {
                                alpha = 1f - progress()
                            }
                    )
                }
            }
        }
    }
}

private class ProgressPreviewParameterProvider : PreviewParameterProvider<Float> {
    override val values = sequenceOf(0f, 0.5f, 1f)
}

@Preview
@Composable
private fun Preview(
    @PreviewParameter(ProgressPreviewParameterProvider::class) progress: Float
) {
    PlaygroundTheme {
        var isPlaying by remember { mutableStateOf(false) }
        MediaControlBar(
            mediaItem = MediaItem(
                artworkThumbnailUrl = null,
                artworkLargeUrl = null,
                title = "Title",
                artists = listOf(Artist("Artist 1"), Artist("Artist 2")),
            ),
            isPlaying = isPlaying,
            onPlayPauseClick = { isPlaying = !isPlaying },
            progress = { progress },
            minContentSize = DpSize(64.dp, 64.dp),
        )
    }
}
