package com.alexrdclement.uiplayground.app.demo.components.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.control.Controls
import com.alexrdclement.uiplayground.components.AngleWeb
import com.alexrdclement.uiplayground.components.AngleWebPointedShape
import com.alexrdclement.uiplayground.components.AngleWebShape
import com.alexrdclement.uiplayground.components.AngleWebStar
import com.alexrdclement.uiplayground.components.CircularWeb
import com.alexrdclement.uiplayground.components.HorizontalDivider
import com.alexrdclement.uiplayground.components.Surface
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.roundToInt

@Composable
fun WebDemo(
    modifier: Modifier = Modifier,
) {
    var strokeWidthPx by remember { mutableStateOf(1f) }
    val strokeWidth = with(LocalDensity.current) { strokeWidthPx.toDp() }

    var numLines by remember { mutableStateOf(8) }
    var numCircles by remember { mutableStateOf(16) }
    var numPoints by remember { mutableStateOf(4) }

    var innerRadius by remember { mutableStateOf(0.5f) }
    var webInsidePoints by remember { mutableStateOf(true) }
    var webOutsidePoints by remember { mutableStateOf(true) }

    var rotation by remember { mutableStateOf(0f) }

    val numLinesControl = Control.Slider(
        name = "Lines",
        value = numLines.toFloat(),
        onValueChange = {
            numLines = it.toInt()
        },
        valueRange = 1f..100f,
    )

    val numCirclesControl = Control.Slider(
        name = "Circles",
        value = numCircles.toFloat(),
        onValueChange = {
            numCircles = it.toInt()
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

    val webInsidePointsControl = Control.Toggle(
        name = "Inside points",
        value = webInsidePoints,
        onValueChange = {
            webInsidePoints = it
        },
    )

    val webOutsidePointsControl = Control.Toggle(
        name = "Outside points",
        value = webOutsidePoints,
        onValueChange = {
            webOutsidePoints = it
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .padding(PlaygroundTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .graphicsLayer { this.rotationZ = rotation }

            CircularWeb(
                numCircles = numCircles,
                numRadialLines = numLines,
                strokeWidth = strokeWidth,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = modifier,
            )
            AngleWeb(
                start = Offset(0f, 0f),
                vertex = Offset(0f, 1f),
                end = Offset(1f, 1f),
                numLines = numLines,
                strokeWidth = strokeWidth,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = modifier,
            )
            AngleWebStar(
                numLines = numLines,
                numPoints = numPoints,
                strokeWidth = strokeWidth,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = modifier,
            )
            AngleWebShape(
                numLines = numLines,
                numPoints = numPoints,
                strokeWidth = strokeWidth,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = modifier,
            )
            AngleWebPointedShape(
                webInsidePoints = webInsidePoints,
                webOutsidePoints = webOutsidePoints,
                numLines = numLines,
                numPoints = numPoints,
                innerRadius = innerRadius,
                strokeWidth = strokeWidth,
                color = PlaygroundTheme.colorScheme.primary,
                modifier = modifier,
            )
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        Controls(
            controls = persistentListOf(
                numCirclesControl,
                numLinesControl,
                numPointsControl,
                innerRadiusControl,
                webInsidePointsControl,
                webOutsidePointsControl,
                rotationControl,
                strokeWidthControl,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .verticalScroll(rememberScrollState())
                .padding(PlaygroundTheme.spacing.medium)
                .navigationBarsPadding(),
        )
    }
}

@Preview
@Composable
fun WebDemoPreview() {
    PlaygroundTheme {
        Surface {
            WebDemo()
        }
    }
}
