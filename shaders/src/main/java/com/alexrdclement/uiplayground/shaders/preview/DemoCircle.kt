package com.alexrdclement.uiplayground.shaders.preview

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill

@Composable
internal fun DemoCircle(
    modifier: Modifier = Modifier,
    drawStyle: DrawStyle = Fill,
) {
    val color = MaterialTheme.colorScheme.primary
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        drawCircle(color, style = drawStyle, radius = size.minDimension / 4f)
    }
}

@ShaderPreview
@Composable
private fun Preview() {
    DemoCircle()
}
