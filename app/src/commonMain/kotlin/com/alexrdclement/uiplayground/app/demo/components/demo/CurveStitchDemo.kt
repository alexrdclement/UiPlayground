package com.alexrdclement.uiplayground.app.demo.components.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.components.CurveStitch
import com.alexrdclement.uiplayground.components.CurveStitchShape
import com.alexrdclement.uiplayground.components.CurveStitchStar
import com.alexrdclement.uiplayground.components.CurveStitchStarShape
import com.alexrdclement.uiplayground.components.Surface
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(PlaygroundTheme.spacing.large),
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                .graphicsLayer { this.rotationZ = state.rotation }
            CurveStitch(
                start = Offset(0f, 0f),
                vertex = Offset(0f, 1f),
                end = Offset(1f, 1f),
                numLines = state.numLines,
                strokeWidth = state.strokeWidth,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = modifier,
            )
            CurveStitchStar(
                numLines = state.numLines,
                numPoints = state.numPoints,
                strokeWidth = state.strokeWidth,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = modifier,
            )
            CurveStitchShape(
                numLines = state.numLines,
                numPoints = state.numPoints,
                strokeWidth = state.strokeWidth,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = modifier,
            )
            CurveStitchStarShape(
                drawInsidePoints = state.starShapeInsidePoints,
                drawOutsidePoints = state.starShapeOutsidePoints,
                numLines = state.numLines,
                numPoints = state.numPoints,
                innerRadius = state.innerRadius,
                strokeWidth = state.strokeWidth,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = modifier,
            )
        }
    }
}

@Composable
fun rememberCurveStitchDemoState(): CurveStitchDemoState = remember { CurveStitchDemoState() }

@Stable
class CurveStitchDemoState {
    var strokeWidth by mutableStateOf(1.dp)

    var numLines by mutableStateOf(8)
    var numPoints by mutableStateOf(4)

    var innerRadius by mutableStateOf(0.5f)
    var starShapeInsidePoints by mutableStateOf(true)
    var starShapeOutsidePoints by mutableStateOf(true)

    var rotation by mutableStateOf(0f)
}

@Composable
fun rememberCurveStitchDemoControl(
    state: CurveStitchDemoState,
): CurveStitchDemoControl = remember(state) { CurveStitchDemoControl(state) }

@Stable
class CurveStitchDemoControl(
    val state: CurveStitchDemoState,
) {
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
        valueRange = { 2f..100f },
    )

    val innerRadius = Control.Slider(
        name = "Inner radius",
        value = { state.innerRadius },
        onValueChange = {
            state.innerRadius = it
        },
        valueRange = { 0f..1f },
    )

    val starShapeInsidePoints = Control.Toggle(
        name = "Inside points",
        value = { state.starShapeInsidePoints },
        onValueChange = {
            state.starShapeInsidePoints = it
        },
    )

    val starShapeOutsidePoints = Control.Toggle(
        name = "Outside points",
        value = { state.starShapeOutsidePoints },
        onValueChange = {
            state.starShapeOutsidePoints = it
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

    val controls = persistentListOf(
        numLines,
        numPoints,
        innerRadius,
        starShapeInsidePoints,
        starShapeOutsidePoints,
        rotation,
        strokeWidth,
    )
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
