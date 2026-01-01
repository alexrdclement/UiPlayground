package com.alexrdclement.uiplayground.app.preview

import androidx.compose.runtime.Composable
import com.alexrdclement.palette.components.core.Surface
import com.alexrdclement.palette.theme.PaletteTheme

@Composable
fun UiPlaygroundPreview(
    content: @Composable () -> Unit,
) {
    PaletteTheme {
        Surface(
            content = content,
        )
    }
}
