package com.alexrdclement.uiplayground.app.demo.components.demo

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.DemoWithControls
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.components.Sphere
import com.alexrdclement.uiplayground.components.SphereStyle
import com.alexrdclement.uiplayground.components.util.ViewingAngle
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import kotlin.math.roundToInt

@Composable
fun SphereDemo(
    modifier: Modifier = Modifier,
) {
    val strokeColor = PlaygroundTheme.colorScheme.primary
    val outlineStrokeColor = strokeColor
    val initialStyle = SphereStyle.Grid(
        numLatitudeLines = 12,
        numLongitudeLines = 12,
        strokeWidth = 2.dp,
        strokeColor = strokeColor,
        outlineStrokeColor = outlineStrokeColor,
        outlineStrokeWidth = 2.dp,
        faceColor = null,
    )
    var style: SphereStyle by remember { mutableStateOf(initialStyle) }

    var fill by remember { mutableStateOf(false) }
    val faceColor = strokeColor.copy(alpha = 0.3f)
    val fillControl = Control.Toggle(
        name = "Fill",
        value = fill,
        onValueChange = {
            fill = it
            style = when (val style = style) {
                is SphereStyle.Grid -> style.copy(
                    faceColor = if (fill) faceColor else null,
                )
            }
        },
    )

    var outline by remember { mutableStateOf(true) }
    val outlineControl = Control.Toggle(
        name = "Outline",
        value = outline,
        onValueChange = {
            outline = it
            style = when (val style = style) {
                is SphereStyle.Grid -> style.copy(
                    outlineStrokeColor = outlineStrokeColor.takeIf { outline },
                )
            }
        },
    )

    var viewingAngle by remember { mutableStateOf(ViewingAngle()) }

    val xRotationControl = Control.Slider(
        name = "X axis rotation",
        value = viewingAngle.rotationX,
        valueRange = -180f..180f,
        onValueChange = { viewingAngle = viewingAngle.copy(rotationX = it) },
    )
    val yRotationControl = Control.Slider(
        name = "Y axis rotation",
        value = viewingAngle.rotationY,
        valueRange = -180f..180f,
        onValueChange = { viewingAngle = viewingAngle.copy(rotationY = it) },
    )
    val zRotationControl = Control.Slider(
        name = "Z axis rotation",
        value = viewingAngle.rotationZ,
        valueRange = -180f..180f,
        onValueChange = { viewingAngle = viewingAngle.copy(rotationZ = it) },
    )

    var numLatitudeLines by remember { mutableStateOf(initialStyle.numLatitudeLines) }
    val numLatitudeLinesControl = Control.Slider(
        name = "Latitude lines",
        value = numLatitudeLines.toFloat(),
        valueRange = 1f..100f,
        onValueChange = {
            numLatitudeLines = it.roundToInt()
            style = when (val style = style) {
                is SphereStyle.Grid -> style.copy(numLatitudeLines = numLatitudeLines)
            }
        },
    )

    var numLongitudeLines by remember { mutableStateOf(initialStyle.numLongitudeLines) }
    val numLongitudeLinesControl = Control.Slider(
        name = "Longitude lines",
        value = numLongitudeLines.toFloat(),
        valueRange = 1f..100f,
        onValueChange = {
            numLongitudeLines = it.roundToInt()
            style = when (val style = style) {
                is SphereStyle.Grid -> style.copy(numLongitudeLines = numLongitudeLines)
            }
        },
    )

    var strokeWidth by remember { mutableStateOf(2.dp) }
    val strokeWidthControl = Control.Slider(
        name = "Stroke width",
        value = strokeWidth.value,
        valueRange = 1f..100f,
        onValueChange = {
            strokeWidth = it.dp
            style = when (val style = style) {
                is SphereStyle.Grid -> style.copy(strokeWidth = strokeWidth)
            }
        },
    )

    var outlineStrokeWidth by remember { mutableStateOf(2.dp) }
    val outlineStrokeWidthControl = Control.Slider(
        name = "Outline stroke width",
        value = outlineStrokeWidth.value,
        valueRange = 1f..100f,
        onValueChange = {
            outlineStrokeWidth = it.dp
            style = when (val style = style) {
                is SphereStyle.Grid -> style.copy(outlineStrokeWidth = outlineStrokeWidth)
            }
        },
    )

    var precisionDegree by remember { mutableStateOf(1) }
    val precisionDegreeControl = Control.Slider(
        name = "Precision degrees",
        value = precisionDegree.toFloat(),
        valueRange = 1f..100f,
        onValueChange = { precisionDegree = it.roundToInt() },
    )

    DemoWithControls(
        controls = persistentListOf(
            fillControl,
            outlineControl,
            xRotationControl,
            yRotationControl,
            zRotationControl,
            numLatitudeLinesControl,
            numLongitudeLinesControl,
            precisionDegreeControl,
            strokeWidthControl,
            outlineStrokeWidthControl,
        ),
        modifier = modifier.fillMaxSize(),
    ) {
        Sphere(
            style = style,
            precisionDegree = precisionDegree,
            viewingAngle = viewingAngle,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val (dx, dy) = dragAmount
                        viewingAngle = viewingAngle.copy(
                            rotationY = (viewingAngle.rotationY + dx / 10f) % 360f,
                            rotationX = (viewingAngle.rotationX - dy / 10f) % 360f,
                        )
                    }
                }
        )
    }
}
