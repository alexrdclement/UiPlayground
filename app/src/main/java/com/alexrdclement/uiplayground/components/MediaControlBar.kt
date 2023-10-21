package com.alexrdclement.uiplayground.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.util.UiPlaygroundPreview

data class Artist(val name: String)

data class MediaItem(
    val title: String,
    val artists: List<Artist>,
)

@Stable
class MediaControlBarState(val initialValue: Float) {

    // TODO: Use dp or pixels instead
    var value: Float by mutableFloatStateOf(initialValue)
        private set

    val isExpanded: Boolean by derivedStateOf { value == 1f }

    fun expand() {
        value = 1f
    }

    fun hide() {
        value = 0f
    }

    companion object {
        val Saver: Saver<MediaControlBarState, *> = Saver(
            save = { it.value },
            restore = { MediaControlBarState(it) }
        )
    }
}

@Composable
fun rememberMediaControlBarState(initialValue: Float = 0f): MediaControlBarState {
    return rememberSaveable(saver = MediaControlBarState.Saver) {
        MediaControlBarState(initialValue = initialValue)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MediaControlBar(
    mediaItem: MediaItem,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: MediaControlBarState = rememberMediaControlBarState(),
    onClick: () -> Unit = {},
    minHeight: Dp = 64.dp,
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val height by animateDpAsState(
        targetValue = if (state.isExpanded) {
            screenHeight
        } else {
            minHeight
        },
        label = "height"
    )
    val artworkSize by animateDpAsState(
        targetValue = if (state.isExpanded) {
            screenWidth
        } else {
            minHeight
        },
        label = "artworkSize"
    )

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                if (state.isExpanded) {
                    state.hide()
                } else {
                    state.expand()
                }
                onClick()
            }
            .navigationBarsPadding()
            .animateContentSize()
            .height(height)
    ) {
        MediaItemArtwork(
            modifier = Modifier
                .align(Alignment.Top)
                .animateContentSize()
                .size(artworkSize)
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
                .alpha(if (state.isExpanded) 0f else 1f)
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
                .alpha(if (state.isExpanded) 0f else 1f)
        )
    }
}

@Composable
fun MediaItemArtwork(
    modifier: Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = false)
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
            state = rememberMediaControlBarState(),
            onClick = { /*TODO*/ },
            onPlayPauseClick = { isPlaying = !isPlaying }
        )
    }
}
