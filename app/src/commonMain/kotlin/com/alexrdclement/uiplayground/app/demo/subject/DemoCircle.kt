package com.alexrdclement.uiplayground.app.demo.subject

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import com.alexrdclement.palette.theme.PaletteTheme
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DemoCircle(
    modifier: Modifier = Modifier,
    drawStyle: DrawStyle = Fill,
) {
    val color = PaletteTheme.colorScheme.primary
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(PaletteTheme.colorScheme.surface)
    ) {
        drawCircle(color, style = drawStyle, radius = size.minDimension / 4f)
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        DemoCircle()
    }
}
