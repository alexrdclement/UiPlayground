package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.components.mediacontrolsheet.Artist
import com.alexrdclement.uiplayground.components.mediacontrolsheet.MediaControlBar
import com.alexrdclement.uiplayground.components.mediacontrolsheet.MediaControlSheet
import com.alexrdclement.uiplayground.components.mediacontrolsheet.MediaControlBarAnchorState
import com.alexrdclement.uiplayground.components.mediacontrolsheet.MediaItem
import com.alexrdclement.uiplayground.components.mediacontrolsheet.rememberMediaControlBarState
import kotlinx.coroutines.launch

@Composable
fun ComponentScreen(
    component: Component,
) {
    when (component) {
        Component.MediaControlBar -> MediaPlaybackBarDemo()
    }
}

@Composable
fun MediaPlaybackBarDemo() {
    val mediaItem = MediaItem("Title", listOf(Artist("Artist 1"), Artist("Artist 2")))
    var isPlaying by remember { mutableStateOf(false) }
    val state = rememberMediaControlBarState(
        initialValue = MediaControlBarAnchorState.Expanded,
    )
    val coroutineScope = rememberCoroutineScope()

    MediaControlSheet(
        mediaItem = mediaItem,
        isPlaying = isPlaying,
        onPlayPauseClick = { isPlaying = !isPlaying },
        onControlBarClick = {
            coroutineScope.launch {
                state.expand()
            }
        },
        state = state,
        modifier = Modifier.systemBarsPadding()
    )
}
