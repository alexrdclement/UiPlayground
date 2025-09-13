package com.alexrdclement.uiplayground.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.util.ColorSaver
import com.alexrdclement.uiplayground.components.util.DpSizeSaver
import com.alexrdclement.uiplayground.components.util.DrawStyleSaver
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.components.util.restore
import com.alexrdclement.uiplayground.components.util.rotate
import com.alexrdclement.uiplayground.components.util.save

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

private enum class VertexType {
    Oval, Rect, Plus, X
}

private const val vertexTypeKey = "vertexType"
private const val sizeKey = "size"
private const val colorKey = "color"
private const val drawStyleKey = "drawStyle"
private const val strokeWidthKey = "strokeWidth"
private const val rotationDegreesKey = "rotationDegrees"

val GridVertexSaver = mapSaverSafe(
    save = { value ->
        when (value) {
            is GridVertex.Oval -> mapOf(
                vertexTypeKey to VertexType.Oval,
                sizeKey to save(value.size, DpSizeSaver, this),
                colorKey to save(value.color, ColorSaver, this),
                drawStyleKey to save(value.drawStyle, DrawStyleSaver, this),
                rotationDegreesKey to value.rotationDegrees,
            )

            is GridVertex.Rect -> mapOf(
                vertexTypeKey to VertexType.Rect,
                sizeKey to save(value.size, DpSizeSaver, this),
                colorKey to save(value.color, ColorSaver, this),
                drawStyleKey to save(value.drawStyle, DrawStyleSaver, this),
                rotationDegreesKey to value.rotationDegrees,
            )

            is GridVertex.Plus -> mapOf(
                vertexTypeKey to VertexType.Plus,
                sizeKey to save(value.size, DpSizeSaver, this),
                colorKey to save(value.color, ColorSaver, this),
                strokeWidthKey to value.strokeWidth.value,
                rotationDegreesKey to value.rotationDegrees,
            )

            is GridVertex.X -> mapOf(
                vertexTypeKey to VertexType.X,
                sizeKey to save(value.size, DpSizeSaver, this),
                colorKey to save(value.color, ColorSaver, this),
                strokeWidthKey to value.strokeWidth.value,
                rotationDegreesKey to value.rotationDegrees,
            )
        }
    },
    restore = { map ->
        when (map[vertexTypeKey] as VertexType) {
            VertexType.Oval -> GridVertex.Oval(
                size = restore(map[sizeKey], DpSizeSaver)!!,
                color = restore(map[colorKey], ColorSaver)!!,
                drawStyle = restore(map[drawStyleKey], DrawStyleSaver)!!,
                rotationDegrees = map[rotationDegreesKey] as Float,
            )
            VertexType.Rect -> GridVertex.Rect(
                size = restore(map[sizeKey], DpSizeSaver)!!,
                color = restore(map[colorKey], ColorSaver)!!,
                drawStyle = restore(map[drawStyleKey], DrawStyleSaver)!!,
                rotationDegrees = map[rotationDegreesKey] as Float,
            )
            VertexType.Plus -> GridVertex.Plus(
                size = restore(map[sizeKey], DpSizeSaver)!!,
                color = restore(map[colorKey], ColorSaver)!!,
                strokeWidth = (map[strokeWidthKey] as Float).dp,
                rotationDegrees = map[rotationDegreesKey] as Float,
            )
            VertexType.X -> GridVertex.X(
                size = restore(map[sizeKey], DpSizeSaver)!!,
                color = restore(map[colorKey], ColorSaver)!!,
                strokeWidth = (map[strokeWidthKey] as Float).dp,
                rotationDegrees = map[rotationDegreesKey] as Float,
            )
        }
    }
)
