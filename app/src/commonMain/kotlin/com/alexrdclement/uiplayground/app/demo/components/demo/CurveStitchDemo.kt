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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import com.alexrdclement.uiplayground.app.demo.DemoWithControls
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
) {
    var strokeWidthPx by remember { mutableStateOf(1f) }
    val strokeWidth = with(LocalDensity.current) { strokeWidthPx.toDp() }

    var numLines by remember { mutableStateOf(8) }
    var numPoints by remember { mutableStateOf(4) }

    var innerRadius by remember { mutableStateOf(0.5f) }
    var starShapeInsidePoints by remember { mutableStateOf(true) }
    var starShapeOutsidePoints by remember { mutableStateOf(true) }

    var rotation by remember { mutableStateOf(0f) }

    val numLinesControl = Control.Slider(
        name = "Lines",
        value = numLines.toFloat(),
        onValueChange = {
            numLines = it.toInt()
        },
        valueRange = 1f..100f,
    )

    val numPointsControl = Control.Slider(
        name = "Points",
        value = numPoints.toFloat(),
        onValueChange = {
            numPoints = it.toInt()
        },
        valueRange = 2f..100f,
    )

    val innerRadiusControl = Control.Slider(
        name = "Inner radius",
        value = innerRadius,
        onValueChange = {
            innerRadius = it
        },
        valueRange = 0f..1f,
    )

    val starShapeInsidePointsControl = Control.Toggle(
        name = "Inside points",
        value = starShapeInsidePoints,
        onValueChange = {
            starShapeInsidePoints = it
        },
    )

    val starShapeOutsidePointsControl = Control.Toggle(
        name = "Outside points",
        value = starShapeOutsidePoints,
        onValueChange = {
            starShapeOutsidePoints = it
        },
    )

    val rotationControl = Control.Slider(
        name = "Rotation",
        value = rotation,
        onValueChange = {
            rotation = (it / 45f).roundToInt() * 45f // snap to 45 degree increments
        },
        valueRange = 0f..360f,
    )

    val strokeWidthControl = Control.Slider(
        name = "Stroke Width",
        value = strokeWidthPx,
        onValueChange = {
            strokeWidthPx = it
        },
        valueRange = 1f..100f,
    )

    DemoWithControls(
        controls = persistentListOf(
            numLinesControl,
            numPointsControl,
            innerRadiusControl,
            starShapeInsidePointsControl,
            starShapeOutsidePointsControl,
            rotationControl,
            strokeWidthControl,
        ),
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
                    if (this@DemoWithControls.maxHeight < this@DemoWithControls.maxWidth) {
                        val navigationBarsPadding = WindowInsets.navigationBars.asPaddingValues()
                        val bottomPadding = navigationBarsPadding.calculateBottomPadding()
                        Modifier
                            .size(this@DemoWithControls.maxHeight - bottomPadding)
                            .aspectRatio(1f)
                    } else {
                        Modifier.size(this@DemoWithControls.maxWidth)
                    }
                )
                .graphicsLayer { this.rotationZ = rotation }
            CurveStitch(
                start = Offset(0f, 0f),
                vertex = Offset(0f, 1f),
                end = Offset(1f, 1f),
                numLines = numLines,
                strokeWidth = strokeWidth,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = modifier,
            )
            CurveStitchStar(
                numLines = numLines,
                numPoints = numPoints,
                strokeWidth = strokeWidth,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = modifier,
            )
            CurveStitchShape(
                numLines = numLines,
                numPoints = numPoints,
                strokeWidth = strokeWidth,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = modifier,
            )
            CurveStitchStarShape(
                drawInsidePoints = starShapeInsidePoints,
                drawOutsidePoints = starShapeOutsidePoints,
                numLines = numLines,
                numPoints = numPoints,
                innerRadius = innerRadius,
                strokeWidth = strokeWidth,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = modifier,
            )
        }
    }
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
