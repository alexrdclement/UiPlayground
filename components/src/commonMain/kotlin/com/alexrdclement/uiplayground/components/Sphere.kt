package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.util.Point3D
import com.alexrdclement.uiplayground.components.util.ViewingAngle
import com.alexrdclement.uiplayground.components.util.rotatePoint3D
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

sealed class SphereStyle {
    data class Grid(
        val numLatitudeLines: Int,
        val numLongitudeLines: Int,
        val strokeColor: Color,
        val strokeWidth: Dp = Dp.Hairline,
        val faceColor: Color? = null,
    ) : SphereStyle()
}

@Composable
fun Sphere(
    style: SphereStyle,
    modifier: Modifier = Modifier,
    viewingAngle: ViewingAngle = ViewingAngle(),
    precisionDegree: Int = 1,
) {
    when (style) {
        is SphereStyle.Grid -> {
            GridSphere(
                numLatitudeLines = style.numLatitudeLines,
                numLongitudeLines = style.numLongitudeLines,
                modifier = modifier,
                viewingAngle = viewingAngle,
                precisionDegree = precisionDegree,
                strokeColor = style.strokeColor,
                strokeWidth = style.strokeWidth,
                faceColor = style.faceColor,
            )
        }
    }
}

@Composable
fun GridSphere(
    numLatitudeLines: Int,
    numLongitudeLines: Int,
    modifier: Modifier = Modifier,
    viewingAngle: ViewingAngle = ViewingAngle(),
    precisionDegree: Int = 1,
    strokeColor: Color = PlaygroundTheme.colorScheme.primary,
    strokeWidth: Dp = Dp.Hairline,
    faceColor: Color? = PlaygroundTheme.colorScheme.primary.copy(alpha = 0.1f),
) {
    val latSteps = (360 / numLatitudeLines).coerceAtLeast(2)
    val lonSteps = (180 / numLongitudeLines).coerceAtLeast(2)

    val rotationCache = remember(viewingAngle) {
        mutableMapOf<Pair<Int, Int>, Point3D>()
    }

    Canvas(
        modifier = modifier
    ) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension / 2f

        val drawStyle = Stroke(width = strokeWidth.toPx())

        val rotX = viewingAngle.rotationX.toRadians()
        val rotY = viewingAngle.rotationY.toRadians()
        val rotZ = viewingAngle.rotationZ.toRadians()

        val cosX = cos(rotX)
        val sinX = sin(rotX)
        val cosY = cos(rotY)
        val sinY = sin(rotY)
        val cosZ = cos(rotZ)
        val sinZ = sin(rotZ)

        fun getRotatedPoint(lat: Int, lon: Int): Point3D {
            val key = lat to lon
            return rotationCache.getOrPut(key) {
                val latRad = lat.toRadians()
                val lonRad = lon.toRadians()

                val x = radius * cos(latRad) * cos(lonRad)
                val y = radius * cos(latRad) * sin(lonRad)
                val z = radius * sin(latRad)

                rotatePoint3D(x, y, z, cosX, sinX, cosY, sinY, cosZ, sinZ)
            }
        }

        faceColor?.let {
            drawCircle(
                color = it,
                center = center,
                radius = radius,
                style = Fill,
            )
        }

        drawCircle(
            color = strokeColor,
            center = center,
            radius = radius,
            style = drawStyle,
        )

        for (lon in 0..360 step lonSteps) {
            val path = Path()
            var pathStarted = false

            for (lat in -90..90 step precisionDegree) {
                val rotatedPoint = getRotatedPoint(lat, lon)

                val projectedX = center.x + rotatedPoint.x.toFloat()
                val projectedY = center.y + rotatedPoint.z.toFloat()

                if (rotatedPoint.y >= 0) {
                    if (!pathStarted) {
                        path.moveTo(projectedX, projectedY)
                        pathStarted = true
                    } else {
                        path.lineTo(projectedX, projectedY)
                    }
                } else {
                    pathStarted = false
                }
            }

            drawPath(
                path = path,
                color = strokeColor,
                style = drawStyle,
            )
        }

        for (lat in -90..90 step latSteps) {
            val path = Path()
            var pathStarted = false

            for (lon in 0..360 step precisionDegree) {
                val rotatedPoint = getRotatedPoint(lat, lon)

                val projectedX = center.x + rotatedPoint.x.toFloat()
                val projectedY = center.y + rotatedPoint.z.toFloat()

                if (rotatedPoint.y >= 0) {
                    if (!pathStarted) {
                        path.moveTo(projectedX, projectedY)
                        pathStarted = true
                    } else {
                        path.lineTo(projectedX, projectedY)
                    }
                } else {
                    pathStarted = false
                }
            }

            drawPath(
                path = path,
                color = strokeColor,
                style = drawStyle,
            )
        }
    }
}

fun Int.toRadians(): Double {
    return this * (PI / 180.0)
}

fun Float.toRadians(): Double {
    return this * (PI / 180.0)
}

@Preview
@Composable
fun GridSpherePreview() {
    PlaygroundTheme {
        Surface {
            GridSphere(
                numLatitudeLines = 20,
                numLongitudeLines = 10,
                modifier = Modifier.size(200.dp),
                viewingAngle = ViewingAngle(
                    rotationX = 20f,
                ),
            )
        }
    }
}
