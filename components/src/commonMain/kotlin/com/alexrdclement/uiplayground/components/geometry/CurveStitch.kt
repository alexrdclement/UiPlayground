package com.alexrdclement.uiplayground.components.geometry

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.core.Surface
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CurveStitch(
    start: Offset,
    vertex: Offset,
    end: Offset,
    numLines: Int,
    strokeWidth: Dp,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier,
    ) {
        val strokeWidthPx = strokeWidth.toPx()
        drawCurveStitch(
            start = start,
            vertex = vertex,
            end = end,
            numLines = numLines,
            strokeWidth = strokeWidthPx,
            color = color,
        )
    }
}

@Composable
fun CurveStitchStar(
    numLines: Int,
    numPoints: Int,
    strokeWidth: Dp,
    color: Color,
    drawInsidePoints: Boolean = true,
    drawOutsidePoints: Boolean = true,
    innerRadius: Float = 0f,
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier,
    ) {
        val width = size.width
        val height = size.height
        val centerX = width / 2
        val centerY = height / 2
        val radius = minOf(centerX, centerY)
        val strokeWidthPx = strokeWidth.toPx()

        val angleStep = (2 * PI / numPoints).toFloat()

        val outerVertices = List(numPoints) { i ->
            val angle = i * angleStep
            Offset(
                x = centerX + radius * cos(angle),
                y = centerY + radius * sin(angle)
            )
        }
        val innerVertices = List(numPoints) { i ->
            val angle = (i + 0.5f) * angleStep
            Offset(
                x = centerX + (radius * innerRadius) * cos(angle),
                y = centerY + (radius * innerRadius) * sin(angle)
            )
        }
        val vertices = List(numPoints) { i ->
            listOf(outerVertices[i], innerVertices[i])
        }.flatten()

        if (innerRadius > 0f && drawInsidePoints) {
            for (i in 0 until numPoints) {
                val index = i * 2 + 1
                val start = vertices[index]
                val vertex = vertices[(index + 1) % vertices.size]
                val end = vertices[(index + 2) % vertices.size]

                drawCurveStitch(
                    start = Offset(start.x / size.width, start.y / size.height),
                    vertex = Offset(vertex.x / size.width, vertex.y / size.height),
                    end = Offset(end.x / size.width, end.y / size.height),
                    numLines = numLines,
                    strokeWidth = strokeWidthPx,
                    color = color,
                )
            }
        }

        if (drawOutsidePoints) {
            for (i in 0 until numPoints) {
                val index = i * 2
                val start = vertices[index]
                val vertex = vertices[(index + 1) % vertices.size]
                val end = vertices[(index + 2) % vertices.size]

                drawCurveStitch(
                    start = Offset(start.x / size.width, start.y / size.height),
                    vertex = Offset(vertex.x / size.width, vertex.y / size.height),
                    end = Offset(end.x / size.width, end.y / size.height),
                    numLines = numLines,
                    strokeWidth = strokeWidthPx,
                    color = color,
                )
            }
        }
    }
}

@Composable
fun CurveStitchShape(
    numLines: Int,
    numPoints: Int,
    strokeWidth: Dp,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = modifier
    ) {
        val width = size.width
        val height = size.height
        val centerX = width / 2
        val centerY = height / 2
        val radius = minOf(centerX, centerY)
        val strokeWidthPx = strokeWidth.toPx()

        val angleStep = (2 * PI / numPoints).toFloat()

        val vertices = List(numPoints) { i ->
            val angle = i * angleStep
            Offset(
                x = centerX + radius * cos(angle),
                y = centerY + radius * sin(angle)
            )
        }

        for (i in vertices.indices) {
            val start = vertices[i]
            val vertex = vertices[(i + 1) % vertices.size]
            val end = vertices[(i + 2) % vertices.size]

            drawCurveStitch(
                start = Offset(start.x / size.width, start.y / size.height),
                vertex = Offset(vertex.x / size.width, vertex.y / size.height),
                end = Offset(end.x / size.width, end.y / size.height),
                numLines = numLines,
                strokeWidth = strokeWidthPx,
                color = color,
            )
        }
    }
}

private fun DrawScope.drawCurveStitch(
    start: Offset,
    vertex: Offset,
    end: Offset,
    numLines: Int,
    strokeWidth: Float,
    color: Color,
) {
    val path = Path()
    path.moveTo(start.x * size.width, start.y * size.height)
    path.lineTo(vertex.x * size.width, vertex.y * size.height)
    path.lineTo(end.x * size.width, end.y * size.height)
    for (i in 0 until numLines) {
        val t = i.toFloat() / numLines
        val x1 = start.x + t * (vertex.x - start.x)
        val y1 = start.y + t * (vertex.y - start.y)
        val x2 = vertex.x + t * (end.x - vertex.x)
        val y2 = vertex.y + t * (end.y - vertex.y)
        path.moveTo(x1 * size.width, y1 * size.height)
        path.lineTo(x2 * size.width, y2 * size.height)
    }
    drawPath(
        path = path,
        color = color,
        style = Stroke(width = strokeWidth),
    )
}

@Preview
@Composable
fun CurveStitchPreview() {
    PlaygroundTheme {
        Surface {
            CurveStitch(
                start = Offset(0.1f, 0.1f),
                vertex = Offset(0.1f, 0.9f),
                end = Offset(0.9f, 0.9f),
                numLines = 12,
                strokeWidth = Dp.Hairline,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = Modifier.size(200.dp),
            )
        }
    }
}

@Preview
@Composable
fun CurveStitchStarInnerRadius0Preview() {
    PlaygroundTheme {
        Surface {
            CurveStitchStar(
                numLines = 12,
                numPoints = 5,
                innerRadius = 0f,
                strokeWidth = Dp.Hairline,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = Modifier.size(200.dp),
            )
        }
    }
}

@Preview
@Composable
fun CurveStitchStarPreview() {
    PlaygroundTheme {
        Surface {
            CurveStitchStar(
                drawInsidePoints = true,
                drawOutsidePoints = true,
                numLines = 12,
                numPoints = 6,
                strokeWidth = Dp.Hairline,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = Modifier.size(200.dp),
            )
        }
    }
}

@Preview
@Composable
fun CurveStitchStarInsideOnlyPreview() {
    PlaygroundTheme {
        Surface {
            CurveStitchStar(
                drawOutsidePoints = false,
                numLines = 12,
                numPoints = 6,
                strokeWidth = Dp.Hairline,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = Modifier.size(200.dp),
            )
        }
    }
}

@Preview
@Composable
fun CurveStitchStarOutsideOnlyPreview() {
    PlaygroundTheme {
        Surface {
            CurveStitchStar(
                drawInsidePoints = false,
                numLines = 12,
                numPoints = 6,
                strokeWidth = Dp.Hairline,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = Modifier.size(200.dp),
            )
        }
    }
}

@Preview
@Composable
fun CurveStitchShapePreview() {
    PlaygroundTheme {
        Surface {
            CurveStitchShape(
                numLines = 12,
                numPoints = 4,
                strokeWidth = Dp.Hairline,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = Modifier.size(200.dp),
            )
        }
    }
}
