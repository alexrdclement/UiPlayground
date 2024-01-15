package com.alexrdclement.uiplayground.components.demo

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
import com.alexrdclement.uiplayground.components.model.Artist
import com.alexrdclement.uiplayground.components.MediaControlSheetAnchorState
import com.alexrdclement.uiplayground.components.MediaControlSheet
import com.alexrdclement.uiplayground.components.model.MediaItem
import com.alexrdclement.uiplayground.components.rememberMediaControlSheetState
import kotlinx.coroutines.launch

@Composable
fun MediaControlSheetDemo() {
    val mediaItem = MediaItem(
        artworkThumbnailUrl = null,
        artworkLargeUrl = null,
        title = "Title",
        artists = listOf(Artist("Artist 1"), Artist("Artist 2"))
    )
    var isPlaying by remember { mutableStateOf(false) }
    val state = rememberMediaControlSheetState(
        initialValue = MediaControlSheetAnchorState.PartiallyExpanded,
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
