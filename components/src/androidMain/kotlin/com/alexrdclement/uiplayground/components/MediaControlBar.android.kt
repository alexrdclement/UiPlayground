package com.alexrdclement.uiplayground.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.alexrdclement.uiplayground.components.model.Artist
import com.alexrdclement.uiplayground.components.model.MediaItem
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

private class ProgressPreviewParameterProvider : PreviewParameterProvider<Float> {
    override val values = sequenceOf(0f, 0.5f, 1f)
}

@Preview(showBackground = true)
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
        )
    }
}
