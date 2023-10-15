package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

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

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MediaControlBar(
                mediaItem = mediaItem,
                isPlaying = isPlaying,
                onClick = { /*TODO*/ },
                onPlayPauseClick = { isPlaying = !isPlaying }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}
