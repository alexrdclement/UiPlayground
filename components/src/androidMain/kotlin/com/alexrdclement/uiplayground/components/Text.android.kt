package com.alexrdclement.uiplayground.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.preview.TextStylePreviewParameterProvider

@PreviewLightDark
@Composable
private fun Preview(
    @PreviewParameter(TextStylePreviewParameterProvider::class) textStylePair: Pair<String, TextStyle>,
) {
    PlaygroundTheme {
        Surface {
            Text(
                text = textStylePair.first,
                style = textStylePair.second,
            )
        }
    }
}
