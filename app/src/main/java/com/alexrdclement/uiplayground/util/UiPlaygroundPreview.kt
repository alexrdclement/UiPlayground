package com.alexrdclement.uiplayground.util

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.ui.theme.UiPlaygroundTheme

@Composable
fun UiPlaygroundPreview(
    content: @Composable () -> Unit,
) {
    UiPlaygroundTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            content = content,
        )
    }
}
