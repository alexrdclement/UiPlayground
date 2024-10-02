package com.alexrdclement.uiplayground.ui.preview

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.components.Surface
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun UiPlaygroundPreview(
    content: @Composable () -> Unit,
) {
    PlaygroundTheme {
        Surface(
            content = content,
        )
    }
}
