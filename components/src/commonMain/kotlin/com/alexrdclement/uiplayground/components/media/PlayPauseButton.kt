package com.alexrdclement.uiplayground.components.media

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.PlayPauseButtonContentDescriptionPaused
import com.alexrdclement.uiplayground.components.PlayPauseButtonContentDescriptionPlaying
import com.alexrdclement.uiplayground.components.core.Button
import com.alexrdclement.uiplayground.components.preview.BoolPreviewParameterProvider
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter

@Composable
fun PlayPauseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPlaying: Boolean = false,
    isEnabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        enabled = isEnabled,
        contentPadding = PaddingValues(vertical = 2.dp, horizontal = 2.dp),
        modifier = modifier
            .aspectRatio(1f)
    ) {
        Image(
            imageVector = if (isPlaying) Icons.Default.Close else Icons.Default.PlayArrow,
            contentDescription = if (isPlaying) {
                PlayPauseButtonContentDescriptionPlaying
            } else PlayPauseButtonContentDescriptionPaused,
            colorFilter = ColorFilter.tint(PlaygroundTheme.colorScheme.onPrimary),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun PlayPreview(
    @PreviewParameter(BoolPreviewParameterProvider::class) isEnabled: Boolean,
) {
    PlaygroundTheme {
        PlayPauseButton(
            isEnabled = isEnabled,
            isPlaying = false,
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun PausePreview(
    @PreviewParameter(BoolPreviewParameterProvider::class) isEnabled: Boolean,
) {
    PlaygroundTheme {
        PlayPauseButton(
            isEnabled = isEnabled,
            isPlaying = true,
            onClick = {},
        )
    }
}
