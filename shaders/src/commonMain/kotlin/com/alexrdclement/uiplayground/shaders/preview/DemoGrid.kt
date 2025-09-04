package com.alexrdclement.uiplayground.shaders.preview

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun DemoGrid(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    background: Color = Color.White,
    strokeWidth: Dp = 1.dp,
    spacing: Dp = 20.dp,
) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(background)
    ) {
        val widthPX = strokeWidth.toPx()
        val spacingPx = spacing.toPx()

        var x = 0f
        while (x <= size.width) {
            drawLine(
                color = color,
                start = Offset(x, 0f),
                end = Offset(x, size.height),
                strokeWidth = widthPX
            )
            x += spacingPx
        }

        var y = 0f
        while (y <= size.height) {
            drawLine(
                color = color,
                start = Offset(0f, y),
                end = Offset(size.width, y),
                strokeWidth = widthPX
            )
            y += spacingPx
        }
    }
}

@ShaderPreview
@Composable
private fun Preview() {
    DemoGrid()
}
