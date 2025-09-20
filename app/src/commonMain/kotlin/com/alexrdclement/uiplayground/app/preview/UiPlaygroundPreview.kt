package com.alexrdclement.uiplayground.app.preview

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.components.core.Surface
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
