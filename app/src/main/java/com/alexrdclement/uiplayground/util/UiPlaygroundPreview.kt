package com.alexrdclement.uiplayground.util

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.ui.theme.UiPlaygroundTheme

@Composable
fun UiPlaygroundPreview(
    content: @Composable () -> Unit,
) {
    UiPlaygroundTheme {
        Surface(
            content = content,
        )
    }
}
