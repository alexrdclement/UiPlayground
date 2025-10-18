package com.alexrdclement.uiplayground.app.demo.components.geometry

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.demo.Demo
import com.alexrdclement.uiplayground.components.demo.control.Control
import com.alexrdclement.uiplayground.components.demo.control.enumControl
import com.alexrdclement.uiplayground.app.demo.util.OffsetSaver
import com.alexrdclement.uiplayground.components.core.Surface
import com.alexrdclement.uiplayground.components.geometry.CurveStitch
import com.alexrdclement.uiplayground.components.geometry.CurveStitchShape
import com.alexrdclement.uiplayground.components.geometry.CurveStitchStar
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.components.util.restore
import com.alexrdclement.uiplayground.components.util.save
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

@Composable
fun CurveStitchDemo(
    modifier: Modifier = Modifier,
    state: CurveStitchDemoState = rememberCurveStitchDemoState(),
    control: CurveStitchDemoControl = rememberCurveStitchDemoControl(state),
) {
    Demo(
        controls = control.controls,
        modifier = modifier.fillMaxSize(),
    ) {
        val modifier = Modifier
            .then(
                if (this@Demo.maxHeight < this@Demo.maxWidth) {
                    val navigationBarsPadding = WindowInsets.navigationBars.asPaddingValues()
                    val bottomPadding = navigationBarsPadding.calculateBottomPadding()
                    Modifier
                        .size(this@Demo.maxHeight - bottomPadding)
                        .aspectRatio(1f)
                } else {
                    Modifier.size(this@Demo.maxWidth)
                }
            )
            .align(Alignment.Center)
            .graphicsLayer { this.rotationZ = state.rotation }

        when (state.currentDemo) {
            CurveStitchDemo.Angle -> CurveStitch(
                start = state.angleStartOffset,
                vertex = state.angleVertexOffset,
                end = state.angleEndOffset,
                numLines = state.numLines,
                strokeWidth = state.strokeWidth,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = modifier
                    .pointerInput(Unit) {
                        detectTapGestures { position ->
                            val distStart = getDistanceFromOffset(
                                normalizedOffset = state.angleStartOffset,
                                position = position,
                                size = this.size,
                            )
                            val distVertex = getDistanceFromOffset(
                                normalizedOffset = state.angleVertexOffset,
                                position = position,
                                size = this.size,
                            )
                            val distEnd = getDistanceFromOffset(
                                normalizedOffset = state.angleEndOffset,
                                position = position,
                                size = this.size,
                            )
                            when (minOf(distStart, distVertex, distEnd)) {
                                distStart -> state.angleStartOffset = normalizeOffsetPx(position, size)
                                distVertex -> state.angleVertexOffset = normalizeOffsetPx(position, size)
                                distEnd -> state.angleEndOffset = normalizeOffsetPx(position, size)
                            }
                        }
                    }
                    .pointerInput(Unit) {
                        detectDragGestures { change, _ ->
                            val position = change.position
                            val distStart = getDistanceFromOffset(
                                normalizedOffset = state.angleStartOffset,
                                position = position,
                                size = this.size,
                            )
                            val distVertex = getDistanceFromOffset(
                                normalizedOffset = state.angleVertexOffset,
                                position = position,
                                size = this.size,
                            )
                            val distEnd = getDistanceFromOffset(
                                normalizedOffset = state.angleEndOffset,
                                position = position,
                                size = this.size,
                            )
                            when (minOf(distStart, distVertex, distEnd)) {
                                distStart -> state.angleStartOffset = normalizeOffsetPx(position, size)
                                distVertex -> state.angleVertexOffset = normalizeOffsetPx(position, size)
                                distEnd -> state.angleEndOffset = normalizeOffsetPx(position, size)
                            }
                        }
                    }
            )

            CurveStitchDemo.Star -> CurveStitchStar(
                numLines = state.numLines,
                numPoints = state.numPoints,
                strokeWidth = state.strokeWidth,
                color = PlaygroundTheme.colorScheme.primary,
                innerRadius = state.innerRadius,
                drawInsidePoints = state.starInsidePoints,
                drawOutsidePoints = state.starOutsidePoints,
                modifier = modifier,
            )

            CurveStitchDemo.Shape -> CurveStitchShape(
                numLines = state.numLines,
                numPoints = state.numPoints,
                strokeWidth = state.strokeWidth,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = modifier,
            )
        }
    }
}

private fun getDistanceFromOffset(
    normalizedOffset: Offset,
    position: Offset,
    size: IntSize,
): Float {
    val offsetPx = Offset(
        x = normalizedOffset.x * size.width,
        y = normalizedOffset.y * size.height,
    )
    return (offsetPx - position).getDistanceSquared()
}

private fun normalizeOffsetPx(
    position: Offset,
    size: IntSize,
) = Offset(
    x = (position.x / size.width).coerceIn(0f, 1f),
    y = (position.y / size.height).coerceIn(0f, 1f),
)

enum class CurveStitchDemo {
    Angle,
    Star,
    Shape,
}

@Composable
fun rememberCurveStitchDemoState(): CurveStitchDemoState = rememberSaveable(
    saver = CurveStitchDemoStateSaver,
) { CurveStitchDemoState() }

@Stable
class CurveStitchDemoState(
    currentDemoInitial: CurveStitchDemo = CurveStitchDemo.Angle,
    strokeWidthInitial: Dp = 1.dp,
    numLinesInitial: Int = 8,
    angleStartOffsetInitial: Offset = Offset(0f, 0f),
    angleVertexOffsetInitial: Offset = Offset(0f, 1f),
    angleEndOffsetInitial: Offset = Offset(1f, 1f),
    numPointsInitial: Int = 4,
    innerRadiusInitial: Float = 0f,
    starInsidePointsInitial: Boolean = true,
    starOutsidePointsInitial: Boolean = true,
    rotationInitial: Float = 0f,
) {
    var currentDemo by mutableStateOf(currentDemoInitial)
        internal set
    var strokeWidth by mutableStateOf(strokeWidthInitial)
        internal set
    var numLines by mutableStateOf(numLinesInitial)
        internal set
    var angleStartOffset by mutableStateOf(angleStartOffsetInitial)
        internal set
    var angleVertexOffset by mutableStateOf(angleVertexOffsetInitial)
        internal set
    var angleEndOffset by mutableStateOf(angleEndOffsetInitial)
        internal set
    var numPoints by mutableStateOf(numPointsInitial)
        internal set
    var innerRadius by mutableStateOf(innerRadiusInitial)
        internal set
    var starInsidePoints by mutableStateOf(starInsidePointsInitial)
        internal set
    var starOutsidePoints by mutableStateOf(starOutsidePointsInitial)
        internal set
    var rotation by mutableStateOf(rotationInitial)
        internal set
}

private const val currentDemoKey = "currentDemo"
private const val strokeWidthKey = "strokeWidth"
private const val numLinesKey = "numLines"
private const val angleStartOffsetKey = "angleStartOffset"
private const val angleVertexOffsetKey = "angleVertexOffset"
private const val angleEndOffsetKey = "angleEndOffset"
private const val numPointsKey = "numPoints"
private const val innerRadiusKey = "innerRadius"
private const val starInsidePointsKey = "starInsidePoints"
private const val starOutsidePointsKey = "starOutsidePoints"
private const val rotationKey = "rotation"

val CurveStitchDemoStateSaver = mapSaverSafe(
    save = { value ->
        mapOf(
            currentDemoKey to value.currentDemo.name,
            strokeWidthKey to value.strokeWidth.value,
            numLinesKey to value.numLines,
            angleEndOffsetKey to save(value.angleStartOffset, OffsetSaver, this),
            angleVertexOffsetKey to save(value.angleVertexOffset, OffsetSaver, this),
            angleStartOffsetKey to save(value.angleEndOffset, OffsetSaver, this),
            numPointsKey to value.numPoints,
            innerRadiusKey to value.innerRadius,
            starInsidePointsKey to value.starInsidePoints,
            starOutsidePointsKey to value.starOutsidePoints,
            rotationKey to value.rotation,
        )
    },
    restore = { map ->
        CurveStitchDemoState(
            currentDemoInitial = CurveStitchDemo.valueOf(map[currentDemoKey] as String),
            strokeWidthInitial = (map[strokeWidthKey] as Float).dp,
            numLinesInitial = map[numLinesKey] as Int,
            angleStartOffsetInitial = restore(map[angleStartOffsetKey], OffsetSaver)!!,
            angleVertexOffsetInitial = restore(map[angleVertexOffsetKey], OffsetSaver)!!,
            angleEndOffsetInitial = restore(map[angleEndOffsetKey], OffsetSaver)!!,
            numPointsInitial = map[numPointsKey] as Int,
            innerRadiusInitial = map[innerRadiusKey] as Float,
            starInsidePointsInitial = map[starInsidePointsKey] as Boolean,
            starOutsidePointsInitial = map[starOutsidePointsKey] as Boolean,
            rotationInitial = map[rotationKey] as Float,
        )
    },
)

@Composable
fun rememberCurveStitchDemoControl(
    state: CurveStitchDemoState,
): CurveStitchDemoControl = remember(state) { CurveStitchDemoControl(state) }

@Stable
class CurveStitchDemoControl(
    val state: CurveStitchDemoState,
) {
    val currentDemoControl = enumControl(
        name = "Demo",
        includeLabel = false,
        values = { CurveStitchDemo.entries },
        selectedValue = { state.currentDemo },
        onValueChange = { state.currentDemo = it },
    )

    val numLines = Control.Slider(
        name = "Lines",
        value = { state.numLines.toFloat() },
        onValueChange = {
            state.numLines = it.toInt()
        },
        valueRange = { 1f..100f },
    )

    val numPoints = Control.Slider(
        name = "Points",
        value = { state.numPoints.toFloat() },
        onValueChange = {
            state.numPoints = it.toInt()
        },
        valueRange = { 3f..50f },
    )

    val innerRadius = Control.Slider(
        name = "Inner radius",
        value = { state.innerRadius },
        onValueChange = {
            state.innerRadius = it
        },
        valueRange = { 0f..1f },
    )

    val starInsidePoints = Control.Toggle(
        name = "Inside points",
        value = { state.starInsidePoints },
        onValueChange = {
            state.starInsidePoints = it
        },
    )

    val starOutsidePoints = Control.Toggle(
        name = "Outside points",
        value = { state.starOutsidePoints },
        onValueChange = {
            state.starOutsidePoints = it
        },
    )

    val rotation = Control.Slider(
        name = "Rotation",
        value = { state.rotation },
        onValueChange = {
            state.rotation = (it / 45f).roundToInt() * 45f // snap to 45 degree increments
        },
        valueRange = { 0f..360f },
    )

    val strokeWidth = Control.Slider(
        name = "Stroke Width",
        value = { state.strokeWidth.value },
        onValueChange = {
            state.strokeWidth = it.dp
        },
        valueRange = { 1f..100f },
    )

    val controls
        get() = buildList {
            add(currentDemoControl)
            add(numLines)

            val controls = when (state.currentDemo) {
                CurveStitchDemo.Angle -> emptyList()
                CurveStitchDemo.Star -> listOf(
                    numPoints,
                    innerRadius,
                    starInsidePoints,
                    starOutsidePoints,
                )
                CurveStitchDemo.Shape -> listOf(
                    numPoints,
                )
            }
            addAll(controls)

            add(rotation)
            add(strokeWidth)
        }.toPersistentList()
}

@Preview
@Composable
fun CurveStitchDemoPreview() {
    PlaygroundTheme {
        Surface {
            CurveStitchDemo()
        }
    }
}
