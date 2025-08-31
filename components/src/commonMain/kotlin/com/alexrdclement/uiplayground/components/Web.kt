package com.alexrdclement.uiplayground.components

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
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CircularWeb(
    numCircles: Int,
    numRadialLines: Int,
    strokeWidth: Dp,
    color: Color,
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

        for (i in 1..numCircles) {
            val r = radius * i / numCircles
            drawCircle(
                radius = r,
                color = color,
                center = Offset(centerX, centerY),
                style = Stroke(width = strokeWidthPx)
            )
        }

        for (i in 0 until numRadialLines) {
            val angle = (2 * PI / numRadialLines) * i
            val endX = centerX + radius * cos(angle).toFloat()
            val endY = centerY + radius * sin(angle).toFloat()
            drawLine(
                start = Offset(centerX, centerY),
                end = Offset(endX, endY),
                color = color,
                strokeWidth = strokeWidthPx,
            )
        }
    }
}

@Composable
fun AngleWeb(
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
        drawAngleWebPath(
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
fun AngleWebStar(
    numLines: Int,
    numPoints: Int,
    strokeWidth: Dp,
    color: Color,
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

        for (i in 0 until numPoints) {
            val angle = i * angleStep
            val nextAngle = ((i + 1) % numPoints) * angleStep

            val startX = centerX + radius * cos(angle)
            val startY = centerY + radius * sin(angle)
            val vertexX = centerX
            val vertexY = centerY
            val endX = centerX + radius * cos(nextAngle)
            val endY = centerY + radius * sin(nextAngle)

            drawAngleWebPath(
                start = Offset(startX / size.width, startY / size.height),
                vertex = Offset(vertexX / size.width, vertexY / size.height),
                end = Offset(endX / size.width, endY / size.height),
                numLines = numLines,
                strokeWidth = strokeWidthPx,
                color = color,
            )
        }
    }
}

@Composable
fun AngleWebShape(
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

            drawAngleWebPath(
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

@Composable
fun AngleWebPointedShape(
    numLines: Int,
    numPoints: Int,
    strokeWidth: Dp,
    color: Color,
    webInsidePoints: Boolean = true,
    webOutsidePoints: Boolean = true,
    innerRadius: Float = 0.5f,
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

        if (webInsidePoints) {
            for (i in 0 until numPoints) {
                val index = i * 2 + 1
                val start = vertices[index]
                val vertex = vertices[(index + 1) % vertices.size]
                val end = vertices[(index + 2) % vertices.size]

                drawAngleWebPath(
                    start = Offset(start.x / size.width, start.y / size.height),
                    vertex = Offset(vertex.x / size.width, vertex.y / size.height),
                    end = Offset(end.x / size.width, end.y / size.height),
                    numLines = numLines,
                    strokeWidth = strokeWidthPx,
                    color = color,
                )
            }
        }

        if (webOutsidePoints) {
            for (i in 0 until numPoints) {
                val index = i * 2
                val start = vertices[index]
                val vertex = vertices[(index + 1) % vertices.size]
                val end = vertices[(index + 2) % vertices.size]

                drawAngleWebPath(
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

private fun DrawScope.drawAngleWebPath(
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
fun CircularWebPreview() {
    PlaygroundTheme {
        Surface {
            CircularWeb(
                numCircles = 9,
                numRadialLines = 12,
                strokeWidth = Dp.Hairline,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = Modifier.size(200.dp),
            )
        }
    }
}

@Preview
@Composable
fun AngleWebPreview() {
    PlaygroundTheme {
        Surface {
            AngleWeb(
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
fun AngleWebStarPreview() {
    PlaygroundTheme {
        Surface {
            AngleWebStar(
                numLines = 12,
                numPoints = 5,
                strokeWidth = Dp.Hairline,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = Modifier.size(200.dp),
            )
        }
    }
}

@Preview
@Composable
fun AngleWebConvexShapePreview() {
    PlaygroundTheme {
        Surface {
            AngleWebShape(
                numLines = 12,
                numPoints = 4,
                strokeWidth = Dp.Hairline,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = Modifier.size(200.dp),
            )
        }
    }
}

@Preview
@Composable
fun AngleWebPointedShapeInsideOnlyPreview() {
    PlaygroundTheme {
        Surface {
            AngleWebPointedShape(
                webOutsidePoints = false,
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
fun AngleWebPointedShapeOutsideOnlyPreview() {
    PlaygroundTheme {
        Surface {
            AngleWebPointedShape(
                webInsidePoints = false,
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
fun AngleWebPointedShapePreview() {
    PlaygroundTheme {
        Surface {
            AngleWebPointedShape(
                webInsidePoints = true,
                webOutsidePoints = true,
                numLines = 12,
                numPoints = 6,
                strokeWidth = Dp.Hairline,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = Modifier.size(200.dp),
            )
        }
    }
}
