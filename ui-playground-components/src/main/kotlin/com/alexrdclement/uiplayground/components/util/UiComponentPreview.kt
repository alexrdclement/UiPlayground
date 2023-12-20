package com.alexrdclement.uiplayground.components.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun UiComponentPreview(
    content: @Composable () -> Unit
) {
    MaterialTheme {
        content()
    }
}
