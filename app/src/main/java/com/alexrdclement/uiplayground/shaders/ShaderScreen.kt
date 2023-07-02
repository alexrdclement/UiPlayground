package com.alexrdclement.uiplayground.shaders

import android.os.Build
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.demo.subject.DemoCircle
import com.alexrdclement.uiplayground.demo.control.Control
import com.alexrdclement.uiplayground.demo.control.ControlBar
import com.alexrdclement.uiplayground.demo.subject.DemoSubject
import com.alexrdclement.uiplayground.demo.subject.DemoText
import com.alexrdclement.uiplayground.ui.theme.UiPlaygroundTheme

@Composable
fun ShaderScreen() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        // Bummer
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
            DemoModifier.ChromaticAberration(amount = 0f)
        )
    }
    var demoModifierIndex by remember { mutableStateOf(0) }
    val demoModifier by remember(demoModifiers, demoModifierIndex) {
        derivedStateOf { demoModifiers[demoModifierIndex] }
    }

    val controls: List<Control> by remember(demoModifier) {
        derivedStateOf {
            when (val innerModifier = demoModifier) {
                DemoModifier.None -> emptyList()
                is DemoModifier.Blur -> {
                    val edgeTreatments = listOf(
                        BlurredEdgeTreatment.Rectangle,
                        BlurredEdgeTreatment.Unbounded,
                    )
                    listOf(
                        Control.Slider(
                            name = "Radius",
                            value = innerModifier.radius.value,
                            onValueChange = {
                                demoModifiers[demoModifierIndex] =
                                    innerModifier.copy(radius = it.dp)
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
                            selectedIndex = edgeTreatments.indexOf(innerModifier.edgeTreatment),
                            onValueChange = {
                                demoModifiers[demoModifierIndex] =
                                    innerModifier.copy(edgeTreatment = edgeTreatments[it])
                            }
                        )
                    )
                }
                is DemoModifier.ChromaticAberration -> listOf(
                    Control.Slider(
                        name = "Amount",
                        value = innerModifier.amount,
                        onValueChange = {
                            demoModifiers[demoModifierIndex] = innerModifier.copy(amount = it)
                        },
                        valueRange = 0f..1024f,
                    )
                )
            }
        }
    }

    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                val modifier = when (val innerModifier = demoModifier) {
                    DemoModifier.None -> Modifier
                    is DemoModifier.Blur -> Modifier.blur(
                        radius = innerModifier.radius,
                    )

                    is DemoModifier.ChromaticAberration -> Modifier.chromaticAberration(
                        amount = { innerModifier.amount }
                    )
                }.border(
                    1.dp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                when (demoSubject) {
                    DemoSubject.Circle -> DemoCircle(modifier = modifier)
                    DemoSubject.Text -> DemoText(modifier = modifier)
                }
            }

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
                },
            )
        }
    }
}


@Preview
@Composable
private fun Preview() {
    UiPlaygroundTheme {
        ShaderScreen()
    }
}
