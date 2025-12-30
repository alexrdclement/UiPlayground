package com.alexrdclement.uiplayground.shaders.preview

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill

@Composable
fun DemoCircle(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    background: Color = Color.White,
    drawStyle: DrawStyle = Fill,
) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(background)
    ) {
        drawCircle(color, style = drawStyle, radius = size.minDimension / 4f)
    }
}

@ShaderPreview
@Composable
private fun Preview() {
    DemoCircle()
}
