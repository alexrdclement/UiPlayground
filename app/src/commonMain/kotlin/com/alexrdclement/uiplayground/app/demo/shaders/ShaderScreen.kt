package com.alexrdclement.uiplayground.app.demo.shaders

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.DemoWithControls
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.subject.DemoCircle
import com.alexrdclement.uiplayground.app.demo.subject.DemoSubject
import com.alexrdclement.uiplayground.app.demo.subject.DemoText
import com.alexrdclement.uiplayground.app.demo.subject.DemoTextField
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import com.alexrdclement.uiplayground.components.Grid
import com.alexrdclement.uiplayground.components.GridCoordinateSystem
import com.alexrdclement.uiplayground.components.GridLineStyle
import com.alexrdclement.uiplayground.components.GridVertex
import com.alexrdclement.uiplayground.components.Scaffold
import com.alexrdclement.uiplayground.shaders.ColorSplitMode
import com.alexrdclement.uiplayground.shaders.NoiseColorMode
import com.alexrdclement.uiplayground.shaders.colorInvert
import com.alexrdclement.uiplayground.shaders.colorSplit
import com.alexrdclement.uiplayground.shaders.noise
import com.alexrdclement.uiplayground.shaders.pixelate
import com.alexrdclement.uiplayground.shaders.warp
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShaderScreen(
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
) {
    var demoSubject by remember { mutableStateOf(DemoSubject.Circle) }
    val demoModifiers = remember {
        mutableStateListOf(
            DemoModifier.None,
            DemoModifier.Blur(
                radius = 0.dp,
                edgeTreatment = BlurredEdgeTreatment.Rectangle
            ),
            DemoModifier.ColorInvert(
                amount = 0f,
            ),
            DemoModifier.ColorSplit(
                xAmount = 0f,
                yAmount = 0f,
                colorMode = ColorSplitMode.RGB,
            ),
            DemoModifier.Noise(amount = 0f, colorMode = NoiseColorMode.Monochrome),
            DemoModifier.Pixelate(subdivisions = 0),
            DemoModifier.Warp(
                radius = 200.dp,
                amount = .2f,
            ),
        )
    }
    var demoModifierIndex by remember { mutableIntStateOf(0) }
    val demoModifier by remember(demoModifiers, demoModifierIndex) {
        derivedStateOf { demoModifiers[demoModifierIndex] }
    }

    val subjectModifierControl = Control.ControlRow(
        controls = listOf(
            Control.Dropdown(
                name = "Subject",
                values = DemoSubject.entries.map {
                    Control.Dropdown.DropdownItem(name = it.name, value = it)
                }.toPersistentList(),
                selectedIndex = DemoSubject.entries.indexOf(demoSubject),
                onValueChange = { demoSubject = DemoSubject.entries[it] },
                includeLabel = false,
            ),
            Control.Dropdown(
                name = "Modifier",
                values = demoModifiers.map {
                    Control.Dropdown.DropdownItem(name = it.name, value = it)
                }.toPersistentList(),
                selectedIndex = demoModifiers.indexOf(demoModifier),
                onValueChange = { demoModifierIndex = it },
                includeLabel = false,
            )
        ).toPersistentList()
    )

    val controls: ImmutableList<Control> by remember(demoModifier, subjectModifierControl) {
        derivedStateOf {
            persistentListOf(
                *makeModifierControls(
                    demoModifier = demoModifier,
                    demoModifierIndex = demoModifierIndex,
                    demoModifiers = demoModifiers,
                ).toTypedArray(),
                subjectModifierControl,
            )
        }
    }
    Scaffold(
        topBar = {
            DemoTopBar(
                title = "Shaders",
                onNavigateBack = onNavigateBack,
                onConfigureClick = onConfigureClick,
            )
        }
    ) { innerPadding ->
        DemoWithControls(
            controls = controls.toPersistentList(),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                var pointerOffset: Offset by remember { mutableStateOf(Offset.Zero) }
                val modifier = when (val innerModifier = demoModifier) {
                    DemoModifier.None -> Modifier
                    is DemoModifier.Blur -> Modifier.blur(
                        radius = innerModifier.radius,
                        edgeTreatment = innerModifier.edgeTreatment,
                    )
                    is DemoModifier.ColorInvert -> Modifier.colorInvert(
                        amount = { innerModifier.amount },
                    )
                    is DemoModifier.ColorSplit -> Modifier.colorSplit(
                        xAmount = { innerModifier.xAmount },
                        yAmount = { innerModifier.yAmount },
                        colorMode = { innerModifier.colorMode },
                    )
                    is DemoModifier.Noise -> Modifier.noise(
                        amount = { innerModifier.amount },
                        colorMode = innerModifier.colorMode,
                    )
                    is DemoModifier.Pixelate -> Modifier.pixelate(
                        subdivisions = { innerModifier.subdivisions },
                    )
                    is DemoModifier.Warp -> Modifier.warp(
                        offset = { pointerOffset },
                        radius = { innerModifier.radius },
                        amount = { innerModifier.amount },
                    )
                }.pointerInput(Unit) {
                    detectTapGestures(
                        onPress =  { pointerOffset = it },
                        onTap = { pointerOffset = it },
                    )
                }.pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        change.consume()
                        pointerOffset = change.position
                    }
                }
                when (demoSubject) {
                    DemoSubject.Circle -> DemoCircle(modifier = modifier)
                    DemoSubject.CircleOutline -> DemoCircle(drawStyle = Stroke(2f), modifier = modifier)
                    DemoSubject.GridLine -> Grid(
                        coordinateSystem = GridCoordinateSystem.Cartesian(
                            spacing = 20.dp,
                        ),
                        lineStyle = GridLineStyle(
                            color = PlaygroundTheme.colorScheme.primary,
                            stroke = Stroke(width = 1f),
                        ),
                        modifier = modifier.fillMaxSize(),
                    )
                    DemoSubject.GridDot -> Grid(
                        coordinateSystem = GridCoordinateSystem.Cartesian(
                            spacing = 20.dp,
                        ),
                        lineStyle = null,
                        vertex = GridVertex.Oval(
                            color = PlaygroundTheme.colorScheme.primary,
                            size = DpSize(4.dp, 4.dp),
                            drawStyle = Fill,
                        ),
                        modifier = modifier.fillMaxSize(),
                    )
                    DemoSubject.GridRect -> Grid(
                        coordinateSystem = GridCoordinateSystem.Cartesian(
                            spacing = 20.dp,
                        ),
                        lineStyle = null,
                        vertex = GridVertex.Rect(
                            color = PlaygroundTheme.colorScheme.primary,
                            size = DpSize(4.dp, 4.dp),
                            drawStyle = Fill,
                        ),
                        modifier = modifier.fillMaxSize(),
                    )
                    DemoSubject.GridPlus -> Grid(
                        coordinateSystem = GridCoordinateSystem.Cartesian(
                            spacing = 20.dp,
                        ),
                        lineStyle = null,
                        vertex = GridVertex.Plus(
                            color = PlaygroundTheme.colorScheme.primary,
                            size = DpSize(8.dp, 8.dp),
                            strokeWidth = 1.dp,
                        ),
                        modifier = modifier.fillMaxSize(),
                    )
                    DemoSubject.Text -> DemoText(modifier = modifier)
                    DemoSubject.TextField -> DemoTextField(modifier = modifier)
                }
            }
        }
    }
}

private fun makeModifierControls(
    demoModifier: DemoModifier,
    demoModifierIndex: Int,
    demoModifiers: SnapshotStateList<DemoModifier>,
): ImmutableList<Control> {
    return when (demoModifier) {
        DemoModifier.None -> persistentListOf()
        is DemoModifier.Blur -> {
            val edgeTreatments = listOf(
                BlurredEdgeTreatment.Rectangle,
                BlurredEdgeTreatment.Unbounded,
            )
            persistentListOf(
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
                    }.toPersistentList(),
                    selectedIndex = edgeTreatments.indexOf(demoModifier.edgeTreatment),
                    onValueChange = {
                        demoModifiers[demoModifierIndex] =
                            demoModifier.copy(edgeTreatment = edgeTreatments[it])
                    }
                )
            )
        }
        is DemoModifier.ColorInvert -> persistentListOf(
            Control.Slider(
                name = "Amount",
                value = demoModifier.amount,
                onValueChange = {
                    demoModifiers[demoModifierIndex] = demoModifier.copy(amount = it)
                },
                valueRange = 0f..1f,
            )
        )
        is DemoModifier.ColorSplit -> persistentListOf(
            Control.Dropdown(
                name = "Color mode",
                values = ColorSplitMode.entries.map {
                    Control.Dropdown.DropdownItem(
                        name = it.name,
                        value = it
                    )
                }.toPersistentList(),
                selectedIndex = ColorSplitMode.entries
                    .indexOf(demoModifier.colorMode),
                onValueChange = {
                    val colorMode = ColorSplitMode.entries[it]
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
        is DemoModifier.Noise -> persistentListOf(
            Control.Slider(
                name = "Amount",
                value = demoModifier.amount,
                onValueChange = {
                    demoModifiers[demoModifierIndex] = demoModifier.copy(amount = it)
                },
                valueRange = 0f..1f,
            ),
            Control.Dropdown(
                name = "Color Mode",
                values = NoiseColorMode.entries.map {
                    Control.Dropdown.DropdownItem(
                        name = it.name,
                        value = it
                    )
                }.toPersistentList(),
                selectedIndex = NoiseColorMode.entries
                    .indexOf(demoModifier.colorMode),
                onValueChange = {
                    val colorMode = NoiseColorMode.entries[it]
                    demoModifiers[demoModifierIndex] = demoModifier.copy(colorMode = colorMode)
                }
            )
        )
        is DemoModifier.Pixelate -> persistentListOf(
            Control.Slider(
                name = "Amount",
                value = demoModifier.subdivisions.toFloat(),
                onValueChange = {
                    demoModifiers[demoModifierIndex] = demoModifier.copy(subdivisions = it.toInt())
                },
                valueRange = 0f..100f,
            )
        )
        is DemoModifier.Warp -> persistentListOf(
            Control.Slider(
                name = "Amount",
                value = demoModifier.amount,
                onValueChange = {
                    demoModifiers[demoModifierIndex] = demoModifier.copy(amount = it)
                },
                valueRange = -5f..5f,
            ),
            Control.Slider(
                name = "Radius",
                value = demoModifier.radius.value,
                onValueChange = {
                    demoModifiers[demoModifierIndex] =
                        demoModifier.copy(radius = it.dp)
                },
                valueRange = 0f..1000f
            ),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        ShaderScreen(
            onNavigateBack = {},
            onConfigureClick = {},
        )
    }
}
