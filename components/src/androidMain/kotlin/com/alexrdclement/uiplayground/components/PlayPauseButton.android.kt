package com.alexrdclement.uiplayground.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.alexrdclement.uiplayground.components.preview.BoolPreviewParameterProvider
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

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
