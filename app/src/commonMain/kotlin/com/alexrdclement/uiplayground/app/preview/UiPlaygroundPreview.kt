package com.alexrdclement.uiplayground.app.preview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.embarrasdf.palette.theme.components.core.Surface
import com.embarrasdf.palette.theme.PaletteTheme

@Composable
fun UiPlaygroundPreview(
    content: @Composable (PaddingValues) -> Unit,
) {
    PaletteTheme {
        Surface(
            content = content,
        )
    }
}
