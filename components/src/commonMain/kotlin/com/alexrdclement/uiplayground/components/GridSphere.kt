package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

data class ViewingAngle(
    val rotationX: Float = 0f,
    val rotationY: Float = 0f,
    val rotationZ: Float = 0f,
)

@Composable
fun GridSphere(
    numLatitudeLines: Int,
    numLongitudeLines: Int,
    modifier: Modifier = Modifier,
    viewingAngle: ViewingAngle = ViewingAngle(),
    precisionDegree: Int = 1,
    color: Color = PlaygroundTheme.colorScheme.primary,
    strokeWidth: Dp = Dp.Hairline,
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

        drawCircle(
            color = color,
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
                color = color,
                style = drawStyle,
            )
        }

        // Draw latitude lines (parallels)
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
                color = color,
                style = drawStyle,
            )
        }
    }
}

private data class Point3D(val x: Double, val y: Double, val z: Double)

private fun rotatePoint3D(
    x: Double,
    y: Double,
    z: Double,
    cosX: Double,
    sinX: Double,
    cosY: Double,
    sinY: Double,
    cosZ: Double,
    sinZ: Double,
): Point3D {
    val y1 = y * cosX - z * sinX
    val z1 = y * sinX + z * cosX

    val x2 = x * cosY + z1 * sinY
    val z2 = -x * sinY + z1 * cosY

    val x3 = x2 * cosZ - y1 * sinZ
    val y3 = x2 * sinZ + y1 * cosZ

    return Point3D(x3, y3, z2)
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
