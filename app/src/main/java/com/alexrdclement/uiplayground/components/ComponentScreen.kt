package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Text(text = "Current value ${state.currentValue}")
        Text(text = "Target value ${state.targetValue}")
    }

    MediaControlSheet(
        mediaItem = mediaItem,
        isPlaying = isPlaying,
        onPlayPauseClick = { isPlaying = !isPlaying },
        onControlBarClick = {
            coroutineScope.launch {
                if (state.isExpanded) {
                    state.partialExpand()
                } else {
                    state.expand()
                }
            }
        },
        state = state,
        modifier = Modifier.systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    alpha = state.partialToFullProgress
                }
        ) {
            Text(text = "Current value ${state.currentValue}")
            Text(text = "Target value ${state.targetValue}")
        }
    }
}
