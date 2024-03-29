package com.alexrdclement.uiplayground.demo.shaders

import android.os.Build
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.demo.control.Control
import com.alexrdclement.uiplayground.demo.control.ControlBar
import com.alexrdclement.uiplayground.demo.subject.DemoCircle
import com.alexrdclement.uiplayground.demo.subject.DemoSubject
import com.alexrdclement.uiplayground.demo.subject.DemoText
import com.alexrdclement.uiplayground.demo.subject.DemoTextField
import com.alexrdclement.uiplayground.shaders.ChromaticAberrationColorMode
import com.alexrdclement.uiplayground.shaders.chromaticAberration
import com.alexrdclement.uiplayground.shaders.noise
import com.alexrdclement.uiplayground.shaders.pixelate
import com.alexrdclement.uiplayground.ui.preview.UiPlaygroundPreview

@Composable
fun ShaderScreen() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        // Runtime shaders require API 33+
        return
    }

    var demoSubject by remember { mutableStateOf(DemoSubject.Circle) }
    val demoModifiers = remember {
        mutableStateListOf(
            DemoModifier.None,
            DemoModifier.Blur(
                radius = 0.dp,
                edgeTreatment = BlurredEdgeTreatment.Rectangle
            ),
            DemoModifier.ChromaticAberration(
                xAmount = 0f,
                yAmount = 0f,
                colorMode = ChromaticAberrationColorMode.RGB,
            ),
            DemoModifier.Noise(amount = 0f),
            DemoModifier.Pixelate(subdivisions = 0),
        )
    }
    var demoModifierIndex by remember { mutableStateOf(0) }
    val demoModifier by remember(demoModifiers, demoModifierIndex) {
        derivedStateOf { demoModifiers[demoModifierIndex] }
    }

    val controls: List<Control> by remember(demoModifier) {
        derivedStateOf {
            makeControls(
                demoModifier = demoModifier,
                demoModifierIndex = demoModifierIndex,
                demoModifiers = demoModifiers,
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            val modifier = when (val innerModifier = demoModifier) {
                DemoModifier.None -> Modifier
                is DemoModifier.Blur -> Modifier.blur(
                    radius = innerModifier.radius,
                    edgeTreatment = innerModifier.edgeTreatment,
                )
                is DemoModifier.ChromaticAberration -> Modifier.chromaticAberration(
                    xAmount = { innerModifier.xAmount },
                    yAmount = { innerModifier.yAmount },
                    colorMode = { innerModifier.colorMode },
                )
                is DemoModifier.Noise -> Modifier.noise(
                    amount = { innerModifier.amount },
                )
                is DemoModifier.Pixelate -> Modifier.pixelate(
                    subdivisions = { innerModifier.subdivisions },
                )
            }
            when (demoSubject) {
                DemoSubject.Circle -> DemoCircle(modifier = modifier)
                DemoSubject.CircleOutline -> DemoCircle(drawStyle = Stroke(2f), modifier = modifier)
                DemoSubject.Text -> DemoText(modifier = modifier)
                DemoSubject.TextField -> DemoTextField(modifier = modifier)
            }
        }

        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        ControlBar(
            controls = controls,
            demoSubject = demoSubject,
            demoModifier = demoModifier,
            demoModifiers = demoModifiers,
            onSubjectSelected = {
                demoSubject = it
            },
            onModifierSelected = {
                demoModifierIndex = it
            }
        )
    }
}

private fun makeControls(
    demoModifier: DemoModifier,
    demoModifierIndex: Int,
    demoModifiers: SnapshotStateList<DemoModifier>,
): List<Control> {
    return when (demoModifier) {
        DemoModifier.None -> emptyList()
        is DemoModifier.Blur -> {
            val edgeTreatments = listOf(
                BlurredEdgeTreatment.Rectangle,
                BlurredEdgeTreatment.Unbounded,
            )
            listOf(
                Control.Slider(
                    name = "Radius",
                    value = demoModifier.radius.value,
                    onValueChange = {
                        demoModifiers[demoModifierIndex] =
                            demoModifier.copy(radius = it.dp)
                    },
                    valueRange = 0f..16f
                ),
                Control.Dropdown(
                    name = "Edge treatment",
                    values = edgeTreatments.map {
                        Control.Dropdown.DropdownItem(
                            name = it.toString(),
                            value = it
                        )
                    },
                    selectedIndex = edgeTreatments.indexOf(demoModifier.edgeTreatment),
                    onValueChange = {
                        demoModifiers[demoModifierIndex] =
                            demoModifier.copy(edgeTreatment = edgeTreatments[it])
                    }
                )
            )
        }
        is DemoModifier.ChromaticAberration -> listOf(
            Control.Dropdown(
                name = "Color mode",
                values = ChromaticAberrationColorMode.entries.map {
                    Control.Dropdown.DropdownItem(
                        name = it.name,
                        value = it
                    )
                },
                selectedIndex = ChromaticAberrationColorMode.entries
                    .indexOf(demoModifier.colorMode),
                onValueChange = {
                    val colorMode = ChromaticAberrationColorMode.entries[it]
                    demoModifiers[demoModifierIndex] = demoModifier.copy(colorMode = colorMode)
                }
            ),
            Control.Slider(
                name = "X Amount",
                value = demoModifier.xAmount,
                onValueChange = {
                    demoModifiers[demoModifierIndex] = demoModifier.copy(xAmount = it)
                },
                valueRange = -1f..1f,
            ),
            Control.Slider(
                name = "Y Amount",
                value = demoModifier.yAmount,
                onValueChange = {
                    demoModifiers[demoModifierIndex] = demoModifier.copy(yAmount = it)
                },
                valueRange = -1f..1f,
            ),
        )
        is DemoModifier.Noise -> listOf(
            Control.Slider(
                name = "Amount",
                value = demoModifier.amount,
                onValueChange = {
                    demoModifiers[demoModifierIndex] = demoModifier.copy(amount = it)
                },
                valueRange = 0f..1f,
            )
        )
        is DemoModifier.Pixelate -> listOf(
            Control.Slider(
                name = "Amount",
                value = demoModifier.subdivisions.toFloat(),
                onValueChange = {
                    demoModifiers[demoModifierIndex] = demoModifier.copy(subdivisions = it.toInt())
                },
                valueRange = 0f..100f,
            )
        )
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        ShaderScreen()
    }
}
