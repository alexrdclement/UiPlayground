package com.alexrdclement.uiplayground.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.alexrdclement.uiplayground.components.preview.BoolPreviewParameterProvider
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@PreviewLightDark
@Composable
private fun Preview() {
    PlaygroundTheme {
        Surface {
            Text("Hello world")
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewClickable(
    @PreviewParameter(BoolPreviewParameterProvider::class) enabled: Boolean,
) {
    PlaygroundTheme {
        Surface(
            onClick = {},
            enabled = enabled
        ) {
            Text("Hello world")
        }
    }
}
