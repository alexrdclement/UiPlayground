package com.alexrdclement.uiplayground.app.preview

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.embarrasdf.palette.theme.PaletteTheme
import com.embarrasdf.palette.theme.components.core.Surface

@Composable
fun UiPlaygroundPreview(
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    PaletteTheme {
        Surface(
            modifier = modifier,
            content = content,
        )
    }
}
