package com.alexrdclement.uiplayground.app.demo.components.demo

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.control.Controls
import com.alexrdclement.uiplayground.components.GridSphere
import com.alexrdclement.uiplayground.components.HorizontalDivider
import com.alexrdclement.uiplayground.components.ViewingAngle
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import kotlin.math.roundToInt

@Composable
fun GridSphereDemo(
    modifier: Modifier = Modifier,
) {
    var viewingAngle by remember { mutableStateOf(ViewingAngle()) }

    val xRotationControl = Control.Slider(
        name = "X axis rotation",
        value = viewingAngle.rotationX,
        valueRange = 0f..360f,
        onValueChange = { viewingAngle = viewingAngle.copy(rotationX = it) },
    )
    val yRotationControl = Control.Slider(
        name = "Y axis rotation",
        value = viewingAngle.rotationY,
        valueRange = 0f..360f,
        onValueChange = { viewingAngle = viewingAngle.copy(rotationY = it) },
    )
    val zRotationControl = Control.Slider(
        name = "Z axis rotation",
        value = viewingAngle.rotationZ,
        valueRange = 0f..360f,
        onValueChange = { viewingAngle = viewingAngle.copy(rotationZ = it) },
    )

    var numLatitudeLines by remember { mutableStateOf(16) }
    val numLatitudeLinesControl = Control.Slider(
        name = "Latitude lines",
        value = numLatitudeLines.toFloat(),
        valueRange = 2f..100f,
        onValueChange = { numLatitudeLines = it.roundToInt() },
    )

    var numLongitudeLines by remember { mutableStateOf(8) }
    val numLongitudeLinesControl = Control.Slider(
        name = "Longitude lines",
        value = numLongitudeLines.toFloat(),
        valueRange = 2f..100f,
        onValueChange = { numLongitudeLines = it.roundToInt() },
    )

    var precisionDegree by remember { mutableStateOf(1) }
    val precisionDegreeControl = Control.Slider(
        name = "Precision steps",
        value = precisionDegree.toFloat(),
        valueRange = 1f..100f,
        onValueChange = { precisionDegree = it.roundToInt() },
    )

    var strokeWidth by remember { mutableStateOf(2.dp) }
    val strokeWidthControl = Control.Slider(
        name = "Stroke width",
        value = strokeWidth.value,
        valueRange = 1f..100f,
        onValueChange = { strokeWidth = it.dp },
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        GridSphere(
            numLatitudeLines = numLatitudeLines,
            numLongitudeLines = numLongitudeLines,
            precisionDegree = precisionDegree,
            viewingAngle = viewingAngle,
            color = PlaygroundTheme.colorScheme.primary,
            strokeWidth = strokeWidth,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(PlaygroundTheme.spacing.medium)
        )
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        Controls(
            controls = persistentListOf(
                xRotationControl,
                yRotationControl,
                zRotationControl,
                numLatitudeLinesControl,
                numLongitudeLinesControl,
                precisionDegreeControl,
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
