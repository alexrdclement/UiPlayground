package com.alexrdclement.uiplayground.app.demo.shaders

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.control.enumControl
import com.alexrdclement.uiplayground.app.demo.subject.DemoCircle
import com.alexrdclement.uiplayground.app.demo.subject.DemoSubject
import com.alexrdclement.uiplayground.app.demo.subject.DemoText
import com.alexrdclement.uiplayground.app.demo.subject.DemoTextField
import com.alexrdclement.uiplayground.app.demo.util.OffsetSaver
import com.alexrdclement.uiplayground.components.geometry.Grid
import com.alexrdclement.uiplayground.components.geometry.GridCoordinateSystem
import com.alexrdclement.uiplayground.components.geometry.GridLineStyle
import com.alexrdclement.uiplayground.components.geometry.GridVertex
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.components.util.restore
import com.alexrdclement.uiplayground.components.util.save
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

@Composable
fun ShaderDemo(
    modifier: Modifier = Modifier,
    state: ShaderDemoState = rememberDemoShaderState(),
    control: ShaderDemoControl = rememberShaderDemoControl(state = state),
) {
    Demo(
        controls = control.controls,
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
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
                    offset = { state.pointerOffset },
                    radius = { innerModifier.radius },
                    amount = { innerModifier.amount },
                )
            }.pointerInput(Unit) {
                detectTapGestures(
                    onPress = { control.onPointerOffsetChanged(it) },
                    onTap = { control.onPointerOffsetChanged(it) },
                )
            }.pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    change.consume()
                    control.onPointerOffsetChanged(change.position)
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


@Composable
fun rememberDemoShaderState(): ShaderDemoState {
    return rememberSaveable(saver = ShaderDemoStateSaver) { ShaderDemoState() }
}

@Stable
class ShaderDemoState(
    demoSubjectInitial: DemoSubject = DemoSubject.Circle,
    blurModifierInitial: DemoModifier.Blur = DemoModifier.Blur(
        radius = 0.dp,
        edgeTreatment = BlurredEdgeTreatment.Rectangle
    ),
    colorInvertModifierInitial: DemoModifier.ColorInvert = DemoModifier.ColorInvert(
        amount = 0f,
    ),
    colorSplitModifierInitial: DemoModifier.ColorSplit = DemoModifier.ColorSplit(
        xAmount = 0f,
        yAmount = 0f,
        colorMode = ColorSplitMode.RGB,
    ),
    noiseModifierInitial: DemoModifier.Noise = DemoModifier.Noise(
        amount = 0f,
        colorMode = NoiseColorMode.Monochrome,
    ),
    pixelateModifierInitial: DemoModifier.Pixelate = DemoModifier.Pixelate(subdivisions = 0),
    warpModifierInitial: DemoModifier.Warp = DemoModifier.Warp(
        radius = 200.dp,
        amount = .2f,
    ),
    demoModifierIndexInitial: Int = 0,
    pointerOffsetInitial: Offset = Offset.Zero,
) {
    var demoSubject by mutableStateOf(demoSubjectInitial)
        internal set

    var blurModifier by mutableStateOf(blurModifierInitial)
        internal set
    var colorInvertModifier by mutableStateOf(colorInvertModifierInitial)
        internal set
    var colorSplitModifier by mutableStateOf(colorSplitModifierInitial)
        internal set
    var noiseModifier by mutableStateOf(noiseModifierInitial)
        internal set
    var pixelateModifier by mutableStateOf(pixelateModifierInitial)
        internal set
    var warpModifier by mutableStateOf(warpModifierInitial)
        internal set

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

    var demoModifierIndex by mutableStateOf(demoModifierIndexInitial)
        internal set
    val demoModifier get() = demoModifiers[demoModifierIndex]

    var pointerOffset by mutableStateOf(pointerOffsetInitial)
        internal set
}

private const val demoSubjectInitialKey = "demoSubject"
private const val blurModifierInitialKey = "blurModifier"
private const val colorInvertModifierInitialKey = "colorInvertModifier"
private const val colorSplitModifierInitialKey = "colorSplitModifier"
private const val noiseModifierInitialKey = "noiseModifier"
private const val pixelateModifierInitialKey = "pixelateModifier"
private const val warpModifierInitialKey = "warpModifier"
private const val demoModifierIndexInitialKey = "demoModifierIndex"
private const val pointerOffsetInitialKey = "pointerOffset"

val ShaderDemoStateSaver = mapSaverSafe(
    save = { value ->
        mapOf(
            demoSubjectInitialKey to value.demoSubject,
            blurModifierInitialKey to save(value.blurModifier, DemoModifierSaver, this),
            colorInvertModifierInitialKey to save(
                value = value.colorInvertModifier,
                saver = DemoModifierSaver,
                scope = this,
            ),
            colorSplitModifierInitialKey to save(
                value = value.colorSplitModifier,
                saver = DemoModifierSaver,
                scope = this,
            ),
            noiseModifierInitialKey to save(
                value = value.noiseModifier,
                saver = DemoModifierSaver,
                scope = this,
            ),
            pixelateModifierInitialKey to save(
                value = value.pixelateModifier,
                saver = DemoModifierSaver,
                scope = this,
            ),
            warpModifierInitialKey to save(value.warpModifier, DemoModifierSaver, this),
            demoModifierIndexInitialKey to value.demoModifierIndex,
            pointerOffsetInitialKey to save(value.pointerOffset, OffsetSaver, this),
        )
    },
    restore = { map ->
        ShaderDemoState(
            demoSubjectInitial = map[demoSubjectInitialKey] as DemoSubject,
            blurModifierInitial = restore(
                value = map[blurModifierInitialKey],
                saver = DemoModifierSaver,
            )!!,
            colorInvertModifierInitial = restore(
                value = map[colorInvertModifierInitialKey],
                saver = DemoModifierSaver,
            )!!,
            colorSplitModifierInitial = restore(
                value = map[colorSplitModifierInitialKey],
                saver = DemoModifierSaver,
            )!!,
            noiseModifierInitial = restore(
                value = map[noiseModifierInitialKey],
                saver = DemoModifierSaver,
            )!!,
            pixelateModifierInitial = restore(
                value = map[pixelateModifierInitialKey],
                saver = DemoModifierSaver,
            )!!,
            warpModifierInitial = restore(
                value = map[warpModifierInitialKey],
                saver = DemoModifierSaver,
            )!!,
            demoModifierIndexInitial = map[demoModifierIndexInitialKey] as Int,
            pointerOffsetInitial = restore(map[pointerOffsetInitialKey], OffsetSaver)!!,
        )
    },
)

@Composable
fun rememberShaderDemoControl(
    state: ShaderDemoState = rememberDemoShaderState(),
): ShaderDemoControl {
    return remember(state) { ShaderDemoControl(state) }
}

@Stable
class ShaderDemoControl(
    val state: ShaderDemoState,
) {
    val subjectControl = enumControl(
        name = "Subject",
        values = { DemoSubject.entries },
        selectedValue = { state.demoSubject },
        onValueChange = { state.demoSubject = it },
        includeLabel = false,
    )

    val modifierControl = Control.Dropdown(
        name = "Modifier",
        values = {
            state.demoModifiers.map {
                Control.Dropdown.DropdownItem(name = it.name, value = it)
            }.toPersistentList()
        },
        selectedIndex = { state.demoModifierIndex },
        onValueChange = { state.demoModifierIndex = it },
        includeLabel = false,
    )

    val blurredEdgeTreatments = listOf(
        BlurredEdgeTreatment.Rectangle,
        BlurredEdgeTreatment.Unbounded,
    )
    val blurControls: PersistentList<Control> = persistentListOf(
        Control.Slider(
            name = "Radius",
            value = { state.blurModifier.radius.value },
            onValueChange = {
                state.blurModifier = state.blurModifier.copy(radius = it.dp)
            },
            valueRange = { 0f..16f },
        ),
        Control.Dropdown(
            name = "Edge treatment",
            values = {
                blurredEdgeTreatments.map {
                    Control.Dropdown.DropdownItem(
                        name = it.toString(),
                        value = it
                    )
                }.toPersistentList()
            },
            selectedIndex = { blurredEdgeTreatments.indexOf(state.blurModifier.edgeTreatment) },
            onValueChange = {
                state.blurModifier =
                    state.blurModifier.copy(edgeTreatment = blurredEdgeTreatments[it])
            }
        )
    )

    val colorInvertControls = persistentListOf(
        Control.Slider(
            name = "Amount",
            value = { state.colorInvertModifier.amount },
            onValueChange = {
                state.colorInvertModifier = state.colorInvertModifier.copy(amount = it)
            },
            valueRange = { 0f..1f },
        )
    )

    val colorSplitControls = persistentListOf(
        enumControl(
            name = "Color mode",
            values = { ColorSplitMode.entries },
            selectedValue = { state.colorSplitModifier.colorMode },
            onValueChange = { colorMode ->
                state.colorSplitModifier = state.colorSplitModifier.copy(colorMode = colorMode)
            },
        ),
        Control.Slider(
            name = "X Amount",
            value = { state.colorSplitModifier.xAmount },
            onValueChange = {
                state.colorSplitModifier = state.colorSplitModifier.copy(xAmount = it)
            },
            valueRange = { -1f..1f },
        ),
        Control.Slider(
            name = "Y Amount",
            value = { state.colorSplitModifier.yAmount },
            onValueChange = {
                state.colorSplitModifier = state.colorSplitModifier.copy(yAmount = it)
            },
            valueRange = { -1f..1f },
        ),
    )

    val noiseControl = persistentListOf(
        Control.Slider(
            name = "Amount",
            value = { state.noiseModifier.amount },
            onValueChange = {
                state.noiseModifier = state.noiseModifier.copy(amount = it)
            },
            valueRange = { 0f..1f },
        ),
        enumControl(
            name = "Color Mode",
            values = { NoiseColorMode.entries },
            selectedValue = { state.noiseModifier.colorMode },
            onValueChange = { colorMode ->
                state.noiseModifier = state.noiseModifier.copy(colorMode = colorMode)
            },
        ),
    )

    val pixelateControl = persistentListOf(
        Control.Slider(
            name = "Subdivisions",
            value = { state.pixelateModifier.subdivisions.toFloat() },
            onValueChange = {
                state.pixelateModifier = state.pixelateModifier.copy(subdivisions = it.toInt())
            },
            valueRange = { 0f..100f },
        )
    )

    val warpControl = persistentListOf(
        Control.Slider(
            name = "Amount",
            value = { state.warpModifier.amount },
            onValueChange = {
                state.warpModifier = state.warpModifier.copy(amount = it)
            },
            valueRange = { -5f..5f },
        ),
        Control.Slider(
            name = "Radius",
            value = { state.warpModifier.radius.value },
            onValueChange = {
                state.warpModifier = state.warpModifier.copy(radius = it.dp)
            },
            valueRange = { 0f..1000f }
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

    val subjectModifierControl = Control.ControlRow(
        controls = { persistentListOf(subjectControl, modifierControl) },
    )

    val controls
        get() = persistentListOf(
            *modifierControls.toTypedArray(),
            subjectModifierControl,
        )

    fun onPointerOffsetChanged(offset: Offset) {
        state.pointerOffset = offset
    }
}
