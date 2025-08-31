package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

sealed class GridStyle {
    data class Line(
        val color: Color,
        val strokeWidth: Dp
    ) : GridStyle()

    sealed class Vertex(
        open val size: DpSize,
    ) : GridStyle() {
        data class Oval(
            override val size: DpSize,
            val color: Color,
            val drawStyle: DrawStyle,
        ) : Vertex(
            size = size,
        )

        data class Rect(
            override val size: DpSize,
            val color: Color,
            val drawStyle: DrawStyle,
        ) : Vertex(
            size = size,
        )

        data class Plus(
            override val size: DpSize,
            val color: Color,
            val strokeWidth: Dp,
        ) : Vertex(
            size = size,
        )
    }
}

@Composable
fun Grid(
    style: GridStyle,
    spacing: Dp,
    modifier: Modifier = Modifier,
    offset: Offset = Offset.Zero,
    clipToBounds: Boolean = true,
) {
    val modifier = if (clipToBounds) {
        modifier.then(Modifier.clipToBounds())
    } else {
        modifier
    }
    when (style) {
        is GridStyle.Line -> LineGrid(
            color = style.color,
            width = style.strokeWidth,
            spacing = spacing,
            modifier = modifier,
            offset = offset,
        )
        is GridStyle.Vertex.Oval -> {
            val size = with(LocalDensity.current) { style.size.toSize() }
            val radiusXPx = size.width / 2f
            val radiusYPx = size.height / 2f

            Grid(
                spacing = spacing,
                modifier = modifier,
                offset = offset,
            ) { x, y ->
                drawOval(
                    color = style.color,
                    topLeft = Offset(x - radiusXPx, y - radiusYPx),
                    size = style.size.toSize(),
                    style = style.drawStyle,
                )
            }
        }
        is GridStyle.Vertex.Rect -> {
            val size = with(LocalDensity.current) { style.size.toSize() }
            Grid(
                spacing = spacing,
                modifier = modifier,
                offset = offset,
            ) { x, y ->
                drawRect(
                    color = style.color,
                    topLeft = Offset(x - size.width / 2f, y - size.height / 2f),
                    size = size,
                    style = style.drawStyle,
                )
            }
        }
        is GridStyle.Vertex.Plus -> {
            val size = with(LocalDensity.current) { style.size.toSize() }
            val halfPlusWidthPx = size.width / 2f
            val halfPlusHeightPx = size.height / 2f
            val lineWidthPx = with(LocalDensity.current) { style.strokeWidth.toPx() }
            Grid(
                spacing = spacing,
                modifier = modifier,
                offset = offset,
            ) { x, y ->
                drawLine(
                    color = style.color,
                    start = Offset(x - halfPlusWidthPx, y),
                    end = Offset(x + halfPlusWidthPx, y),
                    strokeWidth = lineWidthPx,
                )
                drawLine(
                    color = style.color,
                    start = Offset(x, y - halfPlusHeightPx),
                    end = Offset(x, y + halfPlusHeightPx),
                    strokeWidth = lineWidthPx
                )
            }
        }
    }
}

@Composable
fun LineGrid(
    color: Color,
    width: Dp,
    spacing: Dp,
    modifier: Modifier = Modifier,
    offset: Offset = Offset.Zero,
) {
    Canvas(
        modifier = modifier
    ) {
        val widthPX = width.toPx()
        val spacingPx = spacing.toPx()
        val offset = Offset(
            x = offset.x % spacingPx,
            y = offset.y % spacingPx
        )

        var x = 0f
        while (x <= size.width) {
            drawLine(
                color = color,
                start = Offset(x + offset.x, 0f),
                end = Offset(x + offset.x, size.height),
                strokeWidth = widthPX
            )
            x += spacingPx
        }

        var y = 0f
        while (y <= size.height) {
            drawLine(
                color = color,
                start = Offset(0f, y + offset.y),
                end = Offset(size.width, y + offset.y),
                strokeWidth = widthPX
            )
            y += spacingPx
        }
    }
}

@Composable
fun Grid(
    spacing: Dp,
    modifier: Modifier = Modifier,
    offset: Offset = Offset.Zero,
    drawItem: DrawScope.(Float, Float) -> Unit,
) {
    Canvas(
        modifier = modifier,
    ) {
        val spacingPx = spacing.toPx()
        val offset = Offset(
            x = offset.x % spacingPx,
            y = offset.y % spacingPx,
        )

        var x = 0f
        while (x <= size.width) {
            var y = 0f
            while (y <= size.height) {
                this.drawItem(x + offset.x, y + offset.y)
                y += spacingPx
            }
            x += spacingPx
        }
    }
}

@Preview
@Composable
fun GridLinePreview() {
    PlaygroundTheme {
        Surface {
            Grid(
                style = GridStyle.Line(
                    color = PlaygroundTheme.colorScheme.primary,
                    strokeWidth = Dp.Hairline
                ),
                spacing = 20.dp,
                modifier = Modifier.size(200.dp),
                offset = Offset(0f, 10f),
            )
        }
    }
}

@Preview
@Composable
fun GridOvalPreview() {
    PlaygroundTheme {
        Surface {
            Grid(
                style = GridStyle.Vertex.Oval(
                    color = PlaygroundTheme.colorScheme.primary,
                    size = DpSize(PlaygroundTheme.spacing.xs / 2f, PlaygroundTheme.spacing.xs / 2f),
                    drawStyle = Stroke(width = 1f),
                ),
                spacing = 20.dp,
                modifier = Modifier.size(200.dp),
                offset = Offset(80f, 10f),
            )
        }
    }
}

@Preview
@Composable
fun GridRectPreview() {
    PlaygroundTheme {
        Surface {
            Grid(
                style = GridStyle.Vertex.Rect(
                    color = PlaygroundTheme.colorScheme.primary,
                    size = DpSize(PlaygroundTheme.spacing.small, PlaygroundTheme.spacing.small),
                    drawStyle = Stroke(width = 1f),
                ),
                spacing = 20.dp,
                modifier = Modifier.size(200.dp),
                offset = Offset(80f, 10f),
            )
        }
    }
}

@Preview
@Composable
fun GridPlusPreview() {
    PlaygroundTheme {
        Surface {
            Grid(
                style = GridStyle.Vertex.Plus(
                    color = PlaygroundTheme.colorScheme.primary,
                    size = DpSize(PlaygroundTheme.spacing.small, PlaygroundTheme.spacing.small),
                    strokeWidth = Dp.Hairline,
                ),
                spacing = 20.dp,
                modifier = Modifier.size(200.dp),
                offset = Offset(80f, 0f),
            )
        }
    }
}
