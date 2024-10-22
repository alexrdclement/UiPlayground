@file:OptIn(ExperimentalFoundationApi::class)

package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alexrdclement.uiplayground.components.model.Artist
import com.alexrdclement.uiplayground.components.model.MediaItem
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PlaygroundTheme {
        val mediaItem = MediaItem(
            artworkThumbnailUrl = null,
            artworkLargeUrl = null,
            title = "Title",
            artists = listOf(Artist("Artist 1"), Artist("Artist 2")),
        )
        var isPlaying by remember { mutableStateOf(false) }
        val state = rememberMediaControlSheetState(
            initialValue = MediaControlSheetAnchor.PartiallyExpanded,
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
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                Text("Content")
            }
        }
    }
}
