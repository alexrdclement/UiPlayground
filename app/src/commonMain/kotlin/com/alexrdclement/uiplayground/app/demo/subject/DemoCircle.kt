package com.alexrdclement.uiplayground.app.demo.subject

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun DemoCircle(
    modifier: Modifier = Modifier,
    drawStyle: DrawStyle = Fill,
) {
    val color = PlaygroundTheme.colorScheme.primary
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(PlaygroundTheme.colorScheme.surface)
    ) {
        drawCircle(color, style = drawStyle, radius = size.minDimension / 4f)
    }
}
