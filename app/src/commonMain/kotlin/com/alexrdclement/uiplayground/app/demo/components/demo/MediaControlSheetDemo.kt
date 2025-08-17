package com.alexrdclement.uiplayground.app.demo.components.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.alexrdclement.uiplayground.components.MediaControlSheet
import com.alexrdclement.uiplayground.components.MediaControlSheetAnchor
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.components.model.Artist
import com.alexrdclement.uiplayground.components.model.MediaItem
import com.alexrdclement.uiplayground.components.rememberMediaControlSheetState
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.coroutines.launch

@Composable
fun MediaControlSheetDemo(
    modifier: Modifier = Modifier,
) {
    val mediaItem = MediaItem(
        artworkThumbnailUrl = null,
        artworkLargeUrl = null,
        title = "Title",
        artists = listOf(Artist("Artist 1"), Artist("Artist 2"))
    )
    var isPlaying by remember { mutableStateOf(false) }
    val state = rememberMediaControlSheetState(
        initialValue = MediaControlSheetAnchor.PartiallyExpanded,
    )
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Current value ${state.currentValue}", style = PlaygroundTheme.typography.labelLarge)
        Text(text = "Target value ${state.targetValue}", style = PlaygroundTheme.typography.labelLarge)
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
        modifier = modifier.navigationBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    alpha = state.partialToFullProgress
                }
        ) {
            Text(text = "Current value ${state.currentValue}", style = PlaygroundTheme.typography.labelLarge)
            Text(text = "Target value ${state.targetValue}", style = PlaygroundTheme.typography.labelLarge)
        }
    }
}
