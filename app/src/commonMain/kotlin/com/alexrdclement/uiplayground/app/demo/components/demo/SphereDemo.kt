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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.Demo
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
    state: SphereDemoState = rememberSphereDemoState(),
    control: SphereDemoControl = rememberSphereDemoControl(state),
) {
    Demo(
        controls = control.controls,
        modifier = modifier.fillMaxSize(),
    ) {
        Sphere(
            style = state.style,
            precisionDegree = state.precisionDegree,
            viewingAngle = state.viewingAngle,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val (dx, dy) = dragAmount
                        state.viewingAngle = state.viewingAngle.copy(
                            rotationY = (state.viewingAngle.rotationY + dx / 10f) % 360f,
                            rotationX = (state.viewingAngle.rotationX - dy / 10f) % 360f,
                        )
                    }
                }
        )
    }
}

@Composable
fun rememberSphereDemoState(
    strokeColor: Color = PlaygroundTheme.colorScheme.primary,
    outlineStrokeColor: Color = strokeColor,
) = remember(strokeColor, outlineStrokeColor) {
    SphereDemoState(
        strokeColor = strokeColor,
        outlineStrokeColor = outlineStrokeColor,
    )
}

class SphereDemoState(
    val strokeColor: Color,
    val outlineStrokeColor: Color,
) {
    var fill by mutableStateOf(false)
    var outline by mutableStateOf(true)
    var viewingAngle by mutableStateOf(ViewingAngle())
    var numLatitudeLines by mutableStateOf(12)
    var numLongitudeLines by mutableStateOf(12)
    var strokeWidth by mutableStateOf(2.dp)
    var outlineStrokeWidth by mutableStateOf(2.dp)
    var precisionDegree by mutableStateOf(1)

    val style
        get() = SphereStyle.Grid(
            numLatitudeLines = numLatitudeLines,
            numLongitudeLines = numLongitudeLines,
            strokeWidth = strokeWidth,
            strokeColor = strokeColor,
            outlineStrokeColor = if (outline) outlineStrokeColor else null,
            outlineStrokeWidth = outlineStrokeWidth,
            faceColor = if (fill) strokeColor.copy(alpha = 0.3f) else null,
        )
}

@Composable
fun rememberSphereDemoControl(
    state: SphereDemoState = rememberSphereDemoState(),
) = remember(state) { SphereDemoControl(state) }

class SphereDemoControl(
    private val state: SphereDemoState,
) {
    val fillControl
        get() = Control.Toggle(
            name = "Fill",
            value = state.fill,
            onValueChange = { state.fill = it },
        )

    val xRotationControl
        get() = Control.Slider(
            name = "X axis rotation",
            value = state.viewingAngle.rotationX,
            valueRange = -180f..180f,
            onValueChange = { state.viewingAngle = state.viewingAngle.copy(rotationX = it) },
        )
    val yRotationControl
        get() = Control.Slider(
            name = "Y axis rotation",
            value = state.viewingAngle.rotationY,
            valueRange = -180f..180f,
            onValueChange = { state.viewingAngle = state.viewingAngle.copy(rotationY = it) },
        )
    val zRotationControl
        get() = Control.Slider(
            name = "Z axis rotation",
            value = state.viewingAngle.rotationZ,
            valueRange = -180f..180f,
            onValueChange = { state.viewingAngle = state.viewingAngle.copy(rotationZ = it) },
        )

    val numLatitudeLinesControl
        get() = Control.Slider(
            name = "Latitude lines",
            value = state.numLatitudeLines.toFloat(),
            valueRange = 1f..100f,
            onValueChange = { state.numLatitudeLines = it.roundToInt() },
        )

    val numLongitudeLinesControl
        get() = Control.Slider(
            name = "Longitude lines",
            value = state.numLongitudeLines.toFloat(),
            valueRange = 1f..100f,
            onValueChange = { state.numLongitudeLines = it.roundToInt() },
        )

    val strokeWidthControl
        get() = Control.Slider(
            name = "Stroke width",
            value = state.strokeWidth.value,
            valueRange = 1f..100f,
            onValueChange = { state.strokeWidth = it.dp },
        )

    val outlineControl
        get() = Control.Toggle(
            name = "Outline",
            value = state.outline,
            onValueChange = { state.outline = it },
        )

    val outlineStrokeWidthControl
        get() = Control.Slider(
            name = "Outline stroke width",
            value = state.outlineStrokeWidth.value,
            valueRange = 1f..100f,
            onValueChange = { state.outlineStrokeWidth = it.dp },
        )

    val precisionDegreeControl
        get() = Control.Slider(
            name = "Precision degrees",
            value = state.precisionDegree.toFloat(),
            valueRange = 1f..100f,
            onValueChange = { state.precisionDegree = it.roundToInt() },
        )

    val outlineControls
        get() = if (state.outline) {
            persistentListOf(
                outlineControl,
                outlineStrokeWidthControl
            )
        } else persistentListOf(
            outlineControl,
        )

    val controls
        get() = persistentListOf(
            fillControl,
            xRotationControl,
            yRotationControl,
            zRotationControl,
            numLatitudeLinesControl,
            numLongitudeLinesControl,
            precisionDegreeControl,
            strokeWidthControl,
            *outlineControls.toTypedArray(),
        )
}
