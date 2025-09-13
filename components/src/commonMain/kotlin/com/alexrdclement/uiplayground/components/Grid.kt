package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

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
            xSpacing = { coordinateSystem.scaleX.scale(it, this) },
            ySpacing = {coordinateSystem.scaleY.scale(it, this) },
            lineStyle = lineStyle,
            modifier = modifier,
            offset = offset,
            rotationDegrees = coordinateSystem.rotationDegrees,
            drawVertex = vertex?.let { vertex ->
                { x, y -> drawVertex(vertex, x, y, density) }
            },
        )
        is GridCoordinateSystem.Polar -> PolarGrid(
            radiusSpacing = { coordinateSystem.radiusScale.scale(it, this) },
            theta = { coordinateSystem.theta },
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
        drawContext.transform.rotate(
            degrees = rotationDegrees,
            pivot = Offset(size.width / 2f + offset.x, size.height / 2f + offset.y),
        )

        lineStyle?.let {
            var x = offset.x
            var xInterval = 0

            // Positive X direction
            while (x <= size.width) {
                drawLine(
                    color = lineStyle.color,
                    start = Offset(x, 0f),
                    end = Offset(x, size.height),
                    strokeWidth = lineStyle.stroke.width,
                )
                x += xSpacing(xInterval)
                xInterval += 1
            }

            // Negative X direction
            x = offset.x
            xInterval = 0
            while (x + offset.x >= 0f) {
                drawLine(
                    color = lineStyle.color,
                    start = Offset(x, 0f),
                    end = Offset(x, size.height),
                    strokeWidth = lineStyle.stroke.width,
                )
                x -= xSpacing(xInterval)
                xInterval += 1
            }

            var y = offset.y
            var yInterval = 0

            // Positive Y
            while (y <= size.height) {
                drawLine(
                    color = lineStyle.color,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = lineStyle.stroke.width
                )
                y += ySpacing(yInterval)
                yInterval += 1
            }

            // Negative Y
            y = offset.y
            yInterval = 0
            while (y + offset.y >= 0f) {
                drawLine(
                    color = lineStyle.color,
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = lineStyle.stroke.width
                )
                y -= ySpacing(yInterval)
                yInterval += 1
            }
        }

        drawVertex?.let { drawVertex ->
            var x = offset.x
            var xInterval = 0
            // Positive X
            while (x <= size.width) {
                var y = offset.y
                var yInterval = 0
                // Positive Y
                while (y <= size.height) {
                    drawVertex(x, y)
                    y += ySpacing(yInterval)
                    yInterval += 1
                }

                // Negative Y
                y = offset.y
                yInterval = 0
                while (y + offset.y >= 0f) {
                    drawVertex(x, y)
                    y -= ySpacing(yInterval)
                    yInterval += 1
                }

                x += xSpacing(xInterval)
                xInterval += 1
            }

            // Negative X
            x = offset.x
            xInterval = 0
            while (x + offset.x >= 0f) {
                var y = offset.y
                var yInterval = 0
                // Positive Y
                while (y <= size.height) {
                    drawVertex(x, y)
                    y += ySpacing(yInterval)
                    yInterval += 1
                }

                // Negative Y
                y = offset.y
                yInterval = 0
                while (y + offset.y >= 0f) {
                    drawVertex(x, y)
                    y -= ySpacing(yInterval)
                    yInterval += 1
                }

                x -= xSpacing(xInterval)
                xInterval += 1
            }
        }
    }
}

@Composable
fun PolarGrid(
    radiusSpacing: Density.(Int) -> Float,
    theta: (Int) -> Float, // return angle step in radians
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
        val radiusSpacingInitial = radiusSpacing(0)
        val radius = if (clipLinesToRadius) {
            val maxRadiusFromX = (centerX / radiusSpacingInitial).toInt() * radiusSpacingInitial
            val maxRadiusFromY = (centerY / radiusSpacingInitial).toInt() * radiusSpacingInitial
            minOf(maxRadiusFromX, maxRadiusFromY)
        } else {
            minOf(centerX, centerY)
        }

        drawContext.transform.rotate(
            degrees = rotationDegrees,
            pivot = Offset(centerX, centerY),
        )

        lineStyle?.let {
            var currentRadius = radiusSpacingInitial
            var radiusInterval = 0
            while (currentRadius - radius <= precision) {
                drawCircle(
                    radius = currentRadius,
                    color = lineStyle.color,
                    center = Offset(centerX, centerY),
                    style = lineStyle.stroke,
                )
                currentRadius += radiusSpacing(radiusInterval)
                radiusInterval += 1
            }

            var currentLineAngle = 0f
            var thetaInterval = 0
            val lineLength = when {
                !clipLinesToRadius -> maxOf(width, height)
                currentRadius > radius -> currentRadius - radiusSpacing(radiusInterval - 1)
                else -> currentRadius
            }
            while (currentLineAngle < 2 * PI) {
                val endX = centerX + lineLength * cos(currentLineAngle)
                val endY = centerY + lineLength * sin(currentLineAngle)
                drawLine(
                    color = lineStyle.color,
                    start = Offset(centerX, centerY),
                    end = Offset(endX, endY),
                    strokeWidth = lineStyle.stroke.width,
                )
                currentLineAngle += theta(thetaInterval)
                thetaInterval += 1
            }
        }

        drawVertex?.let { drawVertex ->
            var currentRadius = radiusSpacingInitial
            var radiusInterval = 0
            while (currentRadius - radius <= precision) {
                var currentLineAngle = 0f
                var thetaInterval = 0
                while (currentLineAngle < 2 * PI) {
                    val endX = centerX + currentRadius * cos(currentLineAngle)
                    val endY = centerY + currentRadius * sin(currentLineAngle)
                    drawVertex(endX, endY)
                    currentLineAngle += theta(thetaInterval)
                    thetaInterval += 1
                }
                currentRadius += radiusSpacing(radiusInterval)
                radiusInterval += 1
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
                radiusSpacing = { 30.dp.toPx() },
                theta = { (PI / 3).toFloat() },
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
