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
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.util.rotate
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.pow
import kotlin.math.sin

sealed class GridCoordinateSystem {
    data class Cartesian(
        val scaleX: GridScale,
        val scaleY: GridScale,
        val rotationDegrees: Float = 0f,
    ) : GridCoordinateSystem() {
        constructor(
            spacing: Dp,
            rotationDegrees: Float = 0f,
        ) : this(
            scaleX = GridScale.Linear(spacing = spacing),
            scaleY = GridScale.Linear(spacing = spacing),
            rotationDegrees = rotationDegrees,
        )
    }

    data class Polar(
        val radiusSpacing: Dp,
        val theta: Float, // angle step in radians
        val rotationDegrees: Float = 0f,
    ) : GridCoordinateSystem()
}

sealed class GridScale {
    data class Linear(
        val spacing: Dp,
    ) : GridScale()

    data class Logarithmic(
        val spacing: Dp,
        val base: Float = 10f,
    ) : GridScale()

    data class LogarithmicDecay(
        val spacing: Dp,
        val base: Float = 10f,
    ) : GridScale()

    data class ExponentialDecay(
        val spacing: Dp,
        val exponent: Float = 10f,
    ) : GridScale()

    data class Exponential(
        val spacing: Dp,
        val exponent: Float = 10f,
    ) : GridScale()
}

data class GridLineStyle(
    val color: Color,
    val stroke: Stroke,
)

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

@Composable
fun Grid(
    coordinateSystem: GridCoordinateSystem,
    lineStyle: GridLineStyle?,
    vertex: GridVertex? = null,
    modifier: Modifier = Modifier,
    offset: Offset = Offset.Zero,
    clipToBounds: Boolean = true,
) {
    val density = LocalDensity.current
    val modifier = if (clipToBounds) {
        modifier.then(Modifier.clipToBounds())
    } else {
        modifier
    }
    when (coordinateSystem) {
        is GridCoordinateSystem.Cartesian -> CartesianGrid(
            xSpacing = {
                when (val scaleX = coordinateSystem.scaleX)  {
                    is GridScale.Linear -> scaleX.spacing.toPx()
                    is GridScale.Logarithmic -> if (it == 0) 1f else {
                        val scaling = ln(scaleX.base.toDouble()).toFloat() * it
                        scaleX.spacing.toPx() * scaling
                    }
                    is GridScale.LogarithmicDecay -> if (it == 0) 1f else {
                        val scaling = 1 / (ln(scaleX.base.toDouble()).toFloat() * it)
                        (scaleX.spacing.toPx() * scaling).coerceAtLeast(1f)
                    }
                    is GridScale.Exponential -> if (it == 0) scaleX.spacing.toPx() else {
                        val scaling = scaleX.exponent.toDouble().pow(it.toDouble()).toFloat()
                        scaleX.spacing.toPx() * scaling
                    }
                    is GridScale.ExponentialDecay -> if (it == 0) scaleX.spacing.toPx() else {
                        val scaling = 1 / scaleX.exponent.toDouble().pow(it.toDouble()).toFloat()
                        (scaleX.spacing.toPx() * scaling).coerceAtLeast(1f)
                    }
                }
            },
            ySpacing = {
                when (val scaleY = coordinateSystem.scaleY)  {
                    is GridScale.Linear -> scaleY.spacing.toPx()
                    is GridScale.Logarithmic -> if (it == 0) 1f else {
                        val scaling = ln(scaleY.base.toDouble()).toFloat() * it
                        scaleY.spacing.toPx() * scaling
                    }
                    is GridScale.LogarithmicDecay -> if (it == 0) 1f else {
                        val scaling = 1 / (ln(scaleY.base.toDouble()).toFloat() * it)
                        (scaleY.spacing.toPx() * scaling).coerceAtLeast(1f)
                    }
                    is GridScale.Exponential -> if (it == 0) scaleY.spacing.toPx() else {
                        val scaling = scaleY.exponent.toDouble().pow(it.toDouble()).toFloat()
                        scaleY.spacing.toPx() * scaling
                    }
                    is GridScale.ExponentialDecay -> if (it == 0) scaleY.spacing.toPx() else {
                        val scaling = 1 / scaleY.exponent.toDouble().pow(it.toDouble()).toFloat()
                        (scaleY.spacing.toPx() * scaling).coerceAtLeast(1f)
                    }
                }
            },
            lineStyle = lineStyle,
            modifier = modifier,
            offset = offset,
            rotationDegrees = coordinateSystem.rotationDegrees,
            drawVertex = vertex?.let { vertex ->
                { x, y -> drawVertex(vertex, x, y, density) }
            },
        )
        is GridCoordinateSystem.Polar -> PolarGrid(
            radiusSpacing = coordinateSystem.radiusSpacing,
            theta = coordinateSystem.theta,
            lineStyle = lineStyle,
            modifier = modifier,
            offset = offset,
            rotationDegrees = coordinateSystem.rotationDegrees,
            drawVertex = vertex?.let { vertex ->
                { x, y -> drawVertex(vertex, x, y, density) }
            },
        )
    }
}

@Composable
fun CartesianGrid(
    xSpacing: Density.(Int) -> Float,
    ySpacing: Density.(Int) -> Float,
    lineStyle: GridLineStyle?,
    modifier: Modifier = Modifier,
    rotationDegrees: Float = 0f,
    offset: Offset = Offset.Zero,
    drawVertex: (DrawScope.(Float, Float) -> Unit)? = null,
) {
    Canvas(
        modifier = modifier
    ) {
        val offset = Offset(
            x = offset.x % xSpacing(1),
            y = offset.y % ySpacing(1),
        )

        drawContext.transform.rotate(
            degrees = rotationDegrees,
            pivot = Offset(size.width / 2f + offset.x, size.height / 2f + offset.y),
        )

        lineStyle?.let {
            var xInterval = 0
            var x = 0f
            while (x <= size.width) {
                drawLine(
                    color = lineStyle.color,
                    start = Offset(x + offset.x, 0f),
                    end = Offset(x + offset.x, size.height),
                    strokeWidth = lineStyle.stroke.width,
                )
                x += xSpacing(xInterval)
                xInterval += 1
            }

            var yInterval = 0
            var y = 0f
            while (y <= size.height) {
                drawLine(
                    color = lineStyle.color,
                    start = Offset(0f, y + offset.y),
                    end = Offset(size.width, y + offset.y),
                    strokeWidth = lineStyle.stroke.width
                )
                y += ySpacing(yInterval)
                yInterval += 1
            }
        }

        drawVertex?.let { drawVertex ->
            var x = 0f
            var xInterval = 0
            while (x <= size.width) {
                var y = 0f
                var yInterval = 0
                while (y <= size.height) {
                    drawVertex(x + offset.x, y + offset.y)
                    y += ySpacing(yInterval)
                    yInterval += 1
                }
                x += xSpacing(xInterval)
                xInterval += 1
            }
        }
    }
}

@Composable
fun PolarGrid(
    radiusSpacing: Dp,
    theta: Float, // angle step in radians
    lineStyle: GridLineStyle?,
    modifier: Modifier = Modifier,
    offset: Offset = Offset.Zero,
    rotationDegrees: Float = 0f,
    clipLinesToRadius: Boolean = true,
    precision: Float = 0.001f,
    drawVertex: (DrawScope.(Float, Float) -> Unit)? = null,
) {
    Canvas(
        modifier = modifier,
    ) {
        val width = size.width
        val height = size.height
        val centerX = width / 2 + offset.x
        val centerY = height / 2 + offset.y
        val radiusSpacingPx = radiusSpacing.toPx()
        val radius = if (clipLinesToRadius) {
            val maxRadiusFromX = (centerX / radiusSpacingPx).toInt() * radiusSpacingPx
            val maxRadiusFromY = (centerY / radiusSpacingPx).toInt() * radiusSpacingPx
            minOf(maxRadiusFromX, maxRadiusFromY)
        } else {
            minOf(centerX, centerY)
        }

        drawContext.transform.rotate(
            degrees = rotationDegrees,
            pivot = Offset(centerX, centerY),
        )

        lineStyle?.let {
            var currentRadius = radiusSpacingPx
            while (currentRadius - radius <= precision) {
                drawCircle(
                    radius = currentRadius,
                    color = lineStyle.color,
                    center = Offset(centerX, centerY),
                    style = lineStyle.stroke,
                )
                currentRadius += radiusSpacingPx
            }

            var currentLineAngle = 0f
            while (currentLineAngle < 2 * PI) {
                val endX = centerX + radius * cos(currentLineAngle)
                val endY = centerY + radius * sin(currentLineAngle)
                drawLine(
                    color = lineStyle.color,
                    start = Offset(centerX, centerY),
                    end = Offset(endX, endY),
                    strokeWidth = lineStyle.stroke.width,
                )
                currentLineAngle += theta
            }
        }

        drawVertex?.let { drawVertex ->
            var currentRadius = radiusSpacingPx
            while (currentRadius - radius <= precision) {
                var currentLineAngle = 0f
                while (currentLineAngle < 2 * PI) {
                    val endX = centerX + currentRadius * cos(currentLineAngle)
                    val endY = centerY + currentRadius * sin(currentLineAngle)
                    drawVertex(endX, endY)
                    currentLineAngle += theta
                }
                currentRadius += radiusSpacingPx
            }
        }
    }
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

@Preview
@Composable
fun CartesianGridPreview() {
    PlaygroundTheme {
        Surface {
            CartesianGrid(
                xSpacing = { 20.dp.toPx() },
                ySpacing = { 20.dp.toPx() },
                lineStyle = GridLineStyle(
                    color = PlaygroundTheme.colorScheme.primary,
                    stroke = Stroke(width = 1f),
                ),
                modifier = Modifier.size(200.dp),
                offset = Offset(0f, 10f),
            )
        }
    }
}

@Preview
@Composable
fun CartesianGridLogarithmicScalePreview() {
    PlaygroundTheme {
        Surface {
            Grid(
                coordinateSystem = GridCoordinateSystem.Cartesian(
                    scaleX = GridScale.Logarithmic(spacing = 1.dp, base = 2f),
                    scaleY = GridScale.Logarithmic(spacing = 1.dp, base = 2f),
                ),
                lineStyle = GridLineStyle(
                    color = PlaygroundTheme.colorScheme.primary,
                    stroke = Stroke(width = 1f),
                ),
                modifier = Modifier.size(200.dp),
            )
        }
    }
}

@Preview
@Composable
fun CartesianGridLogarithmicDecayScalePreview() {
    PlaygroundTheme {
        Surface {
            Grid(
                coordinateSystem = GridCoordinateSystem.Cartesian(
                    scaleX = GridScale.LogarithmicDecay(spacing = 50.dp, base = 2f),
                    scaleY = GridScale.LogarithmicDecay(spacing = 50.dp, base = 2f),
                ),
                lineStyle = GridLineStyle(
                    color = PlaygroundTheme.colorScheme.primary,
                    stroke = Stroke(width = 1f),
                ),
                modifier = Modifier.size(200.dp),
            )
        }
    }
}

@Preview
@Composable
fun CartesianGridExponentialScalePreview() {
    PlaygroundTheme {
        Surface {
            Grid(
                coordinateSystem = GridCoordinateSystem.Cartesian(
                    scaleX = GridScale.Exponential(spacing = 1.dp, exponent = 2f),
                    scaleY = GridScale.Exponential(spacing = 1.dp, exponent = 2f),
                ),
                lineStyle = GridLineStyle(
                    color = PlaygroundTheme.colorScheme.primary,
                    stroke = Stroke(width = 1f),
                ),
                modifier = Modifier.size(200.dp),
            )
        }
    }
}

@Preview
@Composable
fun CartesianGridExponentialDecayScalePreview() {
    PlaygroundTheme {
        Surface {
            Grid(
                coordinateSystem = GridCoordinateSystem.Cartesian(
                    scaleX = GridScale.ExponentialDecay(spacing = 100.dp, exponent = 2f),
                    scaleY = GridScale.ExponentialDecay(spacing = 100.dp, exponent = 2f),
                ),
                lineStyle = GridLineStyle(
                    color = PlaygroundTheme.colorScheme.primary,
                    stroke = Stroke(width = 1f),
                ),
                modifier = Modifier.size(200.dp),
            )
        }
    }
}

@Preview
@Composable
fun PolarGridPreview() {
    PlaygroundTheme {
        Surface {
            PolarGrid(
                radiusSpacing = 30.dp,
                theta = (PI / 3).toFloat(),
                lineStyle = GridLineStyle(
                    color = PlaygroundTheme.colorScheme.primary,
                    stroke = Stroke(width = 1f),
                ),
                modifier = Modifier.size(200.dp),
            )
        }
    }
}

@Preview
@Composable
fun OvalGridPreview() {
    val coordinateSystem = GridCoordinateSystem.Cartesian(spacing = 20.dp)
    PlaygroundTheme {
        Surface {
            Grid(
                coordinateSystem = coordinateSystem,
                lineStyle = null,
                vertex = GridVertex.Oval(
                    color = PlaygroundTheme.colorScheme.primary,
                    size = DpSize(PlaygroundTheme.spacing.xs / 2f, PlaygroundTheme.spacing.xs / 2f),
                    drawStyle = Stroke(width = 1f),
                ),
                modifier = Modifier.size(200.dp),
                offset = Offset(80f, 10f),
            )
        }
    }
}

@Preview
@Composable
fun RectGridPreview() {
    val coordinateSystem = GridCoordinateSystem.Cartesian(spacing = 20.dp)
    PlaygroundTheme {
        Surface {
            Grid(
                coordinateSystem = coordinateSystem,
                lineStyle = null,
                vertex = GridVertex.Rect(
                    color = PlaygroundTheme.colorScheme.primary,
                    size = DpSize(PlaygroundTheme.spacing.small, PlaygroundTheme.spacing.small),
                    drawStyle = Stroke(width = 1f),
                ),
                modifier = Modifier.size(200.dp),
                offset = Offset(80f, 10f),
            )
        }
    }
}

@Preview
@Composable
fun PlusGridPreview() {
    val coordinateSystem = GridCoordinateSystem.Cartesian(spacing = 20.dp)
    PlaygroundTheme {
        Surface {
            Grid(
                coordinateSystem = coordinateSystem,
                lineStyle = null,
                vertex = GridVertex.Plus(
                    color = PlaygroundTheme.colorScheme.primary,
                    size = DpSize(PlaygroundTheme.spacing.small, PlaygroundTheme.spacing.small),
                    strokeWidth = Dp.Hairline,
                ),
                modifier = Modifier.size(200.dp),
                offset = Offset(80f, 0f),
            )
        }
    }
}
