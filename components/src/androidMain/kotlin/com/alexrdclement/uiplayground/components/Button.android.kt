package com.alexrdclement.uiplayground.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.alexrdclement.uiplayground.components.preview.BoolPreviewParameterProvider
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@PreviewLightDark
@Composable
private fun PreviewFillStyle(
    @PreviewParameter(BoolPreviewParameterProvider::class) enabled: Boolean,
) {
    PlaygroundTheme {
        Button(
            style = ButtonStyle.Fill,
            enabled = enabled,
            onClick = {},
        ) {
            Text("Button")
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewOutlineStyle(
    @PreviewParameter(BoolPreviewParameterProvider::class) enabled: Boolean,
) {
    PlaygroundTheme {
        Button(
            style = ButtonStyle.Outline,
            enabled = enabled,
            onClick = {},
        ) {
            Text("Button")
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewBorderlessStyle(
    @PreviewParameter(BoolPreviewParameterProvider::class) enabled: Boolean,
) {
    PlaygroundTheme {
        Surface {
            Button(
                style = ButtonStyle.Borderless,
                enabled = enabled,
                onClick = {},
            ) {
                Text("Button")
            }
        }
    }
}
