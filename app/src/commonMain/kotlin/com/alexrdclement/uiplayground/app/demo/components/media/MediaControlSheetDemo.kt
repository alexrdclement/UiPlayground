package com.alexrdclement.uiplayground.app.demo.components.media

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.components.media.MediaControlSheet
import com.alexrdclement.uiplayground.components.media.MediaControlSheetAnchor
import com.alexrdclement.uiplayground.components.media.model.Artist
import com.alexrdclement.uiplayground.components.media.model.MediaItem
import com.alexrdclement.uiplayground.components.media.rememberMediaControlSheetState
import com.alexrdclement.uiplayground.components.util.copy
import com.alexrdclement.uiplayground.components.util.horizontalPaddingValues
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
        modifier = modifier
            .fillMaxSize()
            .padding(WindowInsets.safeDrawing.horizontalPaddingValues())
    ) {
        Text(text = "Current value ${state.currentValue}", style = PlaygroundTheme.typography.labelLarge)
        Text(text = "Target value ${state.targetValue}", style = PlaygroundTheme.typography.labelLarge)
    }

    BoxWithConstraints {
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
            maxContentSize = DpSize(
                width = Dp.Infinity,
                height = with(LocalDensity.current) { constraints.maxHeight.toDp() / 2f },
            ),
            modifier = modifier
                .padding(WindowInsets.safeDrawing.asPaddingValues().copy(top = 0.dp)),
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
}
