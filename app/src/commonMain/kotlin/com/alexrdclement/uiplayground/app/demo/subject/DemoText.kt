package com.alexrdclement.uiplayground.app.demo.subject

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.alexrdclement.palette.components.core.Text
import com.alexrdclement.palette.theme.PaletteTheme
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DemoText(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = PaletteTheme.typography.display,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PaletteTheme.colorScheme.surface)
    ) {
        Text(
            text = "Hello world",
            style = textStyle,
            modifier = modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        DemoText()
    }
}
