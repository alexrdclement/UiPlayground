package com.alexrdclement.uiplayground.app.demo.components.demo

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.control.Controls
import com.alexrdclement.uiplayground.components.HorizontalDivider
import com.alexrdclement.uiplayground.components.Sphere
import com.alexrdclement.uiplayground.components.SphereStyle
import com.alexrdclement.uiplayground.components.ViewingAngle
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import kotlin.math.roundToInt

@Composable
fun SphereDemo(
    modifier: Modifier = Modifier,
) {
    val initialStyle = SphereStyle.Grid(
        numLatitudeLines = 16,
        numLongitudeLines = 8,
        strokeWidth = 2.dp,
        strokeColor = PlaygroundTheme.colorScheme.primary,
        faceColor = null,
    )
    var style: SphereStyle by remember { mutableStateOf(initialStyle) }

    var fill by remember { mutableStateOf(false) }
    val faceColor =PlaygroundTheme.colorScheme.primary.copy(alpha = 0.2f)
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

    var numLatitudeLines by remember { mutableStateOf(initialStyle.numLatitudeLines) }
    val numLatitudeLinesControl = Control.Slider(
        name = "Latitude lines",
        value = numLatitudeLines.toFloat(),
        valueRange = 2f..100f,
        onValueChange = { numLatitudeLines = it.roundToInt() },
    )

    var numLongitudeLines by remember { mutableStateOf(initialStyle.numLongitudeLines) }
    val numLongitudeLinesControl = Control.Slider(
        name = "Longitude lines",
        value = numLongitudeLines.toFloat(),
        valueRange = 2f..100f,
        onValueChange = { numLongitudeLines = it.roundToInt() },
    )

    var strokeWidth by remember { mutableStateOf(2.dp) }
    val strokeWidthControl = Control.Slider(
        name = "Stroke width",
        value = strokeWidth.value,
        valueRange = 1f..100f,
        onValueChange = { strokeWidth = it.dp },
    )

    var precisionDegree by remember { mutableStateOf(1) }
    val precisionDegreeControl = Control.Slider(
        name = "Precision steps",
        value = precisionDegree.toFloat(),
        valueRange = 1f..100f,
        onValueChange = { precisionDegree = it.roundToInt() },
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = PlaygroundTheme.spacing.medium),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = PlaygroundTheme.spacing.medium),
            contentAlignment = Alignment.Center,
        ) {
            Sphere(
                style = style,
                precisionDegree = precisionDegree,
                viewingAngle = viewingAngle,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = PlaygroundTheme.spacing.large)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            val (dx, dy) = dragAmount
                            viewingAngle = viewingAngle.copy(
                                rotationZ = (viewingAngle.rotationZ - dx / 10f) % 360f,
                                rotationX = (viewingAngle.rotationX + dy / 10f) % 360f,
                            )
                        }
                    }
            )
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        Controls(
            controls = persistentListOf(
                fillControl,
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
