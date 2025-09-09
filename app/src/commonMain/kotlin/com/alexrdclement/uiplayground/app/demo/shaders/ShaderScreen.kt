package com.alexrdclement.uiplayground.app.demo.shaders

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.alexrdclement.uiplayground.app.demo.Demo
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
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShaderScreen(
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
    state : DemoShaderState = rememberDemoShaderState(),
    control: DemoShaderControl = rememberDemoShaderControl(state = state),
) {
    Scaffold(
        topBar = {
            DemoTopBar(
                title = "Shaders",
                onNavigateBack = onNavigateBack,
                onConfigureClick = onConfigureClick,
            )
        },
        modifier = Modifier
            .displayCutoutPadding(),
    ) { innerPadding ->
        Demo(
            controls = control.controls,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                var pointerOffset: Offset by remember { mutableStateOf(Offset.Zero) }
                val modifier = when (val innerModifier = state.demoModifier) {
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
                when (state.demoSubject) {
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

@Composable
fun rememberDemoShaderState(): DemoShaderState {
    return remember { DemoShaderState() }
}

@Stable
class DemoShaderState {
    var demoSubject by mutableStateOf(DemoSubject.Circle)

    var blurModifier by mutableStateOf(
        DemoModifier.Blur(
            radius = 0.dp,
            edgeTreatment = BlurredEdgeTreatment.Rectangle
        )
    )
    var colorInvertModifier by mutableStateOf(
        DemoModifier.ColorInvert(
            amount = 0f,
        )
    )
    var colorSplitModifier by mutableStateOf(
        DemoModifier.ColorSplit(
            xAmount = 0f,
            yAmount = 0f,
            colorMode = ColorSplitMode.RGB,
        )
    )
    var noiseModifier by mutableStateOf(
        DemoModifier.Noise(amount = 0f, colorMode = NoiseColorMode.Monochrome)
    )
    var pixelateModifier by mutableStateOf(
        DemoModifier.Pixelate(subdivisions = 0)
    )
    var warpModifier by mutableStateOf(
        DemoModifier.Warp(
            radius = 200.dp,
            amount = .2f,
        )
    )

    val demoModifiers
        get() = listOf(
            DemoModifier.None,
            blurModifier,
            colorInvertModifier,
            colorSplitModifier,
            noiseModifier,
            pixelateModifier,
            warpModifier,
        )

    var demoModifierIndex by mutableStateOf(0)
        internal set
    val demoModifier get() = demoModifiers[demoModifierIndex]
}

@Composable
fun rememberDemoShaderControl(
    state: DemoShaderState = rememberDemoShaderState(),
): DemoShaderControl {
    return remember(state) { DemoShaderControl(state) }
}

@Stable
class DemoShaderControl(
    val state: DemoShaderState,
) {
    val subjectControl
        get() = Control.Dropdown(
            name = "Subject",
            values = DemoSubject.entries.map {
                Control.Dropdown.DropdownItem(name = it.name, value = it)
            }.toPersistentList(),
            selectedIndex = DemoSubject.entries.indexOf(state.demoSubject),
            onValueChange = { state.demoSubject = DemoSubject.entries[it] },
            includeLabel = false,
        )

    val modifierControl
        get() = Control.Dropdown(
            name = "Modifier",
            values = state.demoModifiers.map {
                Control.Dropdown.DropdownItem(name = it.name, value = it)
            }.toPersistentList(),
            selectedIndex = state.demoModifierIndex,
            onValueChange = { state.demoModifierIndex = it },
            includeLabel = false,
        )

    val blurControls: PersistentList<Control>
        get() {
            val edgeTreatments = listOf(
                BlurredEdgeTreatment.Rectangle,
                BlurredEdgeTreatment.Unbounded,
            )
            return persistentListOf(
                Control.Slider(
                    name = "Radius",
                    value = state.blurModifier.radius.value,
                    onValueChange = {
                        state.blurModifier = state.blurModifier.copy(radius = it.dp)
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
                    selectedIndex = edgeTreatments.indexOf(state.blurModifier.edgeTreatment),
                    onValueChange = {
                        state.blurModifier = state.blurModifier.copy(edgeTreatment = edgeTreatments[it])
                    }
                )
            )
        }

    val colorInvertControls
        get() = persistentListOf(
            Control.Slider(
                name = "Amount",
                value = state.colorInvertModifier.amount,
                onValueChange = {
                    state.colorInvertModifier = state.colorInvertModifier.copy(amount = it)
                },
                valueRange = 0f..1f,
            )
        )


    val colorSplitControls
        get() = persistentListOf(
            Control.Dropdown(
                name = "Color mode",
                values = ColorSplitMode.entries.map {
                    Control.Dropdown.DropdownItem(
                        name = it.name,
                        value = it
                    )
                }.toPersistentList(),
                selectedIndex = ColorSplitMode.entries
                    .indexOf(state.colorSplitModifier.colorMode),
                onValueChange = {
                    val colorMode = ColorSplitMode.entries[it]
                    state.colorSplitModifier = state.colorSplitModifier.copy(colorMode = colorMode)
                }
            ),
            Control.Slider(
                name = "X Amount",
                value = state.colorSplitModifier.xAmount,
                onValueChange = {
                    state.colorSplitModifier = state.colorSplitModifier.copy(xAmount = it)
                },
                valueRange = -1f..1f,
            ),
            Control.Slider(
                name = "Y Amount",
                value = state.colorSplitModifier.yAmount,
                onValueChange = {
                    state.colorSplitModifier = state.colorSplitModifier.copy(yAmount = it)
                },
                valueRange = -1f..1f,
            ),
            Control.Dropdown(
                name = "Color mode",
                values = ColorSplitMode.entries.map {
                    Control.Dropdown.DropdownItem(
                        name = it.name,
                        value = it
                    )
                }.toPersistentList(),
                selectedIndex = ColorSplitMode.entries
                    .indexOf(state.colorSplitModifier.colorMode),
                onValueChange = {
                    val colorMode = ColorSplitMode.entries[it]
                    state.colorSplitModifier = state.colorSplitModifier.copy(colorMode = colorMode)
                }
            )
        )

    val noiseControl
        get() = persistentListOf(
            Control.Slider(
                name = "Amount",
                value = state.noiseModifier.amount,
                onValueChange = {
                    state.noiseModifier = state.noiseModifier.copy(amount = it)
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
                    .indexOf(state.noiseModifier.colorMode),
                onValueChange = {
                    val colorMode = NoiseColorMode.entries[it]
                    state.noiseModifier = state.noiseModifier.copy(colorMode = colorMode)
                }
            )
        )

    val pixelateControl
        get() = persistentListOf(
            Control.Slider(
                name = "Subdivisions",
                value = state.pixelateModifier.subdivisions.toFloat(),
                onValueChange = {
                    state.pixelateModifier = state.pixelateModifier.copy(subdivisions = it.toInt())
                },
                valueRange = 0f..100f,
            )
        )

    val warpControl
        get() = persistentListOf(
            Control.Slider(
                name = "Amount",
                value = state.warpModifier.amount,
                onValueChange = {
                    state.warpModifier = state.warpModifier.copy(amount = it)
                },
                valueRange = -5f..5f,
            ),
            Control.Slider(
                name = "Radius",
                value = state.warpModifier.radius.value,
                onValueChange = {
                    state.warpModifier = state.warpModifier.copy(radius = it.dp)
                },
                valueRange = 0f..1000f
            ),
        )

    val modifierControls
        get() = when (state.demoModifier) {
            DemoModifier.None -> persistentListOf()
            is DemoModifier.Blur -> blurControls
            is DemoModifier.ColorInvert -> colorInvertControls
            is DemoModifier.ColorSplit -> colorSplitControls
            is DemoModifier.Noise -> noiseControl
            is DemoModifier.Pixelate -> pixelateControl
            is DemoModifier.Warp -> warpControl
        }

    val subjectModifierControl
        get() = Control.ControlRow(
            controls = persistentListOf(subjectControl, modifierControl)
        )

    val controls
        get() = persistentListOf(
            *modifierControls.toTypedArray(),
            subjectModifierControl,
        )
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
