package com.alexrdclement.uiplayground.app.demo.components.color

import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.components.color.ColorPicker
import com.alexrdclement.uiplayground.components.util.ColorSaver
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.components.util.restore
import com.alexrdclement.uiplayground.components.util.save
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ColorPickerDemo(
    state: ColorPickerDemoState = rememberColorPickerDemoState(),
    control: ColorPickerDemoControl = rememberColorPickerDemoControl(state),
    modifier: Modifier = Modifier,
) {
    Demo(
        controls = control.controls,
        modifier = modifier
            .fillMaxSize()
    ) {
        ColorPickerDemo(
            state = state,
            control = control,
        )
    }
}

@Composable
fun BoxWithConstraintsScope.ColorPickerDemo(
    modifier: Modifier = Modifier,
    state: ColorPickerDemoState = rememberColorPickerDemoState(),
    control: ColorPickerDemoControl = rememberColorPickerDemoControl(state),
) {
    ColorPicker(
        color = state.color,
        onColorChange = control::onColorChange,
        modifier = modifier
            .fillMaxSize()
            .padding(PlaygroundTheme.spacing.medium)
    )
}

@Composable
fun rememberColorPickerDemoState(
    colorInitial: Color = PlaygroundTheme.colorScheme.primary,
): ColorPickerDemoState = rememberSaveable(
    saver = ColorPickerDemoStateSaver,
) {
    ColorPickerDemoState(
        colorInitial = colorInitial,
    )
}

@Stable
class ColorPickerDemoState(
    colorInitial: Color,
) {
    var color by mutableStateOf(colorInitial)
        internal set
}

private const val colorKey = "color"

val ColorPickerDemoStateSaver = mapSaverSafe(
    save = { value ->
        mapOf(
            colorKey to save(value.color, ColorSaver, this),
        )
    },
    restore = { map ->
        ColorPickerDemoState(
            colorInitial = restore(map[colorKey], ColorSaver)!!,
        )
    },
)

@Composable
fun rememberColorPickerDemoControl(
    state: ColorPickerDemoState,
): ColorPickerDemoControl = remember(state) { ColorPickerDemoControl(state) }

@Stable
class ColorPickerDemoControl(
    val state: ColorPickerDemoState,
) {
    val controls = persistentListOf<Control>()

    fun onColorChange(color: Color) {
        state.color = color
    }
}
