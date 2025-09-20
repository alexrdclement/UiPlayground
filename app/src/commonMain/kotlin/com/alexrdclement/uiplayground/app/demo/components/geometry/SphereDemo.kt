package com.alexrdclement.uiplayground.app.demo.components.geometry

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.components.geometry.Sphere
import com.alexrdclement.uiplayground.components.geometry.SphereStyle
import com.alexrdclement.uiplayground.components.util.ColorSaver
import com.alexrdclement.uiplayground.components.util.ViewingAngle
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.components.util.restore
import com.alexrdclement.uiplayground.components.util.save
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
) = rememberSaveable(
    strokeColor,
    outlineStrokeColor,
    saver = SphereDemoStateSaver,
) {
    SphereDemoState(
        strokeColor = strokeColor,
        outlineStrokeColor = outlineStrokeColor,
    )
}

class SphereDemoState(
    val strokeColor: Color,
    val outlineStrokeColor: Color,
    fillInitial: Boolean = false,
    outlineInitial: Boolean = true,
    viewingAngleInitial: ViewingAngle = ViewingAngle(),
    numLatitudeLinesInitial: Int = 12,
    numLongitudeLinesInitial: Int = 12,
    strokeWidthInitial: Dp = 2.dp,
    outlineStrokeWidthInitial: Dp = 2.dp,
    precisionDegreeInitial: Int = 1,
) {
    var fill by mutableStateOf(fillInitial)
        internal set
    var outline by mutableStateOf(outlineInitial)
        internal set
    var viewingAngle by mutableStateOf(viewingAngleInitial)
        internal set
    var numLatitudeLines by mutableStateOf(numLatitudeLinesInitial)
        internal set
    var numLongitudeLines by mutableStateOf(numLongitudeLinesInitial)
        internal set
    var strokeWidth by mutableStateOf(strokeWidthInitial)
        internal set
    var outlineStrokeWidth by mutableStateOf(outlineStrokeWidthInitial)
        internal set
    var precisionDegree by mutableStateOf(precisionDegreeInitial)
        internal set

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

private const val strokeColorKey = "strokeColor"
private const val outlineStrokeColorKey = "outlineStrokeColor"
private const val fillKey = "fill"
private const val outlineKey = "outline"
private const val rotationXKey = "rotationX"
private const val rotationYKey = "rotationY"
private const val rotationZKey = "rotationZ"
private const val numLatitudeLinesKey = "numLatitudeLines"
private const val numLongitudeLinesKey = "numLongitudeLines"
private const val strokeWidthKey = "strokeWidth"
private const val outlineStrokeWidthKey = "outlineStrokeWidth"
private const val precisionDegreeKey = "precisionDegree"

val SphereDemoStateSaver = mapSaverSafe(
    save = { value ->
        mapOf(
            strokeColorKey to save(value.strokeColor, ColorSaver, this),
            outlineStrokeColorKey to save(value.outlineStrokeColor, ColorSaver, this),
            fillKey to value.fill,
            outlineKey to value.outline,
            rotationXKey to value.viewingAngle.rotationX,
            rotationYKey to value.viewingAngle.rotationY,
            rotationZKey to value.viewingAngle.rotationZ,
            numLatitudeLinesKey to value.numLatitudeLines,
            numLongitudeLinesKey to value.numLongitudeLines,
            strokeWidthKey to value.strokeWidth.value,
            outlineStrokeWidthKey to value.outlineStrokeWidth.value,
            precisionDegreeKey to value.precisionDegree,
        )
    },
    restore = { map ->
        SphereDemoState(
            strokeColor = restore(map[strokeColorKey], ColorSaver)!!,
            outlineStrokeColor = restore(map[outlineStrokeColorKey], ColorSaver)!!,
            fillInitial = map[fillKey] as Boolean,
            outlineInitial = map[outlineKey] as Boolean,
            viewingAngleInitial = ViewingAngle(
                rotationX = map[rotationXKey] as Float,
                rotationY = map[rotationYKey] as Float,
                rotationZ = map[rotationZKey] as Float,
            ),
            numLatitudeLinesInitial = map[numLatitudeLinesKey] as Int,
            numLongitudeLinesInitial = map[numLongitudeLinesKey] as Int,
            strokeWidthInitial = (map[strokeWidthKey] as Float).dp,
            outlineStrokeWidthInitial = (map[outlineStrokeWidthKey] as Float).dp,
            precisionDegreeInitial = map[precisionDegreeKey] as Int,
        )
    },
)

@Composable
fun rememberSphereDemoControl(
    state: SphereDemoState = rememberSphereDemoState(),
) = remember(state) { SphereDemoControl(state) }

class SphereDemoControl(
    private val state: SphereDemoState,
) {
    val fillControl = Control.Toggle(
        name = "Fill",
        value = { state.fill },
        onValueChange = { state.fill = it },
    )

    val xRotationControl = Control.Slider(
        name = "X axis rotation",
        value = { state.viewingAngle.rotationX },
        valueRange = { -180f..180f },
        onValueChange = { state.viewingAngle = state.viewingAngle.copy(rotationX = it) },
    )
    val yRotationControl = Control.Slider(
        name = "Y axis rotation",
        value = { state.viewingAngle.rotationY },
        valueRange = { -180f..180f },
        onValueChange = { state.viewingAngle = state.viewingAngle.copy(rotationY = it) },
    )
    val zRotationControl = Control.Slider(
        name = "Z axis rotation",
        value = { state.viewingAngle.rotationZ },
        valueRange = { -180f..180f },
        onValueChange = { state.viewingAngle = state.viewingAngle.copy(rotationZ = it) },
    )

    val numLatitudeLinesControl = Control.Slider(
        name = "Latitude lines",
        value = { state.numLatitudeLines.toFloat() },
        valueRange = { 1f..100f },
        onValueChange = { state.numLatitudeLines = it.roundToInt() },
    )

    val numLongitudeLinesControl = Control.Slider(
        name = "Longitude lines",
        value = { state.numLongitudeLines.toFloat() },
        valueRange = { 1f..100f },
        onValueChange = { state.numLongitudeLines = it.roundToInt() },
    )

    val strokeWidthControl = Control.Slider(
        name = "Stroke width",
        value = { state.strokeWidth.value },
        valueRange = { 1f..100f },
        onValueChange = { state.strokeWidth = it.dp },
    )

    val outlineControl = Control.Toggle(
        name = "Outline",
        value = { state.outline },
        onValueChange = { state.outline = it },
    )

    val outlineStrokeWidthControl = Control.Slider(
        name = "Outline stroke width",
        value = { state.outlineStrokeWidth.value },
        valueRange = { 1f..100f },
        onValueChange = { state.outlineStrokeWidth = it.dp },
    )

    val precisionDegreeControl = Control.Slider(
        name = "Precision degrees",
        value = { state.precisionDegree.toFloat() },
        valueRange = { 1f..100f },
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
