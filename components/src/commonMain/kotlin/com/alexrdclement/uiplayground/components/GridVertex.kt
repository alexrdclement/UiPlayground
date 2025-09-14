package com.alexrdclement.uiplayground.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import com.alexrdclement.uiplayground.components.util.rotate

enum class GridVertexType {
    Oval,
    Rect,
    Plus,
    X,
}

sealed class GridVertex(
    open val size: DpSize,
    open val rotationDegrees: Float,
) {
    data class Oval(
        override val size: DpSize,
        val color: Color,
        val drawStyle: DrawStyle,
        override val rotationDegrees: Float = 0f,
    ) : GridVertex(
        size = size,
        rotationDegrees = rotationDegrees,
    )

    data class Rect(
        override val size: DpSize,
        val color: Color,
        val drawStyle: DrawStyle,
        override val rotationDegrees: Float = 0f,
    ) : GridVertex(
        size = size,
        rotationDegrees = rotationDegrees
    )

    data class Plus(
        override val size: DpSize,
        val color: Color,
        val strokeWidth: Dp,
        override val rotationDegrees: Float = 0f,
    ) : GridVertex(
        size = size,
        rotationDegrees = rotationDegrees,
    )

    data class X(
        override val size: DpSize,
        val color: Color,
        val strokeWidth: Dp,
        override val rotationDegrees: Float = 0f,
    ) : GridVertex(
        size = size,
        rotationDegrees,
    )
}

fun DrawScope.drawVertex(
    vertex: GridVertex,
    x: Float,
    y: Float,
    density: Density,
) {
    drawContext.transform.rotate(
        degrees = vertex.rotationDegrees,
        pivot = Offset(x, y),
    ) {
        when (vertex) {
            is GridVertex.Oval -> {
                val size = vertex.size.toSize()
                val radiusXPx = size.width / 2f
                val radiusYPx = size.height / 2f
                drawOval(
                    color = vertex.color,
                    topLeft = Offset(x - radiusXPx, y - radiusYPx),
                    size = size,
                    style = vertex.drawStyle,
                )
            }

            is GridVertex.Rect -> {
                val size = vertex.size.toSize()
                drawRect(
                    color = vertex.color,
                    topLeft = Offset(x - size.width / 2f, y - size.height / 2f),
                    size = size,
                    style = vertex.drawStyle,
                )
            }

            is GridVertex.Plus -> {
                val size = vertex.size.toSize()
                val halfPlusWidthPx = size.width / 2f
                val halfPlusHeightPx = size.height / 2f
                drawLine(
                    color = vertex.color,
                    start = Offset(x - halfPlusWidthPx, y),
                    end = Offset(x + halfPlusWidthPx, y),
                    strokeWidth = with(density) { vertex.strokeWidth.toPx() },
                )
                drawLine(
                    color = vertex.color,
                    start = Offset(x, y - halfPlusHeightPx),
                    end = Offset(x, y + halfPlusHeightPx),
                    strokeWidth = with(density) { vertex.strokeWidth.toPx() }
                )
            }

            is GridVertex.X -> {
                val size = vertex.size.toSize()
                val halfXWidthPx = size.width / 2f
                val halfXHeightPx = size.height / 2f
                drawLine(
                    color = vertex.color,
                    start = Offset(x - halfXWidthPx, y - halfXHeightPx),
                    end = Offset(x + halfXWidthPx, y + halfXHeightPx),
                    strokeWidth = with(density) { vertex.strokeWidth.toPx() },
                )
                drawLine(
                    color = vertex.color,
                    start = Offset(x - halfXWidthPx, y + halfXHeightPx),
                    end = Offset(x + halfXWidthPx, y - halfXHeightPx),
                    strokeWidth = with(density) { vertex.strokeWidth.toPx() }
                )
            }
        }
    }
}
