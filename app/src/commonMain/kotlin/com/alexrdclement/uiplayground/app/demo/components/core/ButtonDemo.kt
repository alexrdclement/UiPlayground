package com.alexrdclement.uiplayground.app.demo.components.core

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.control.enumControl
import com.alexrdclement.uiplayground.components.core.Button
import com.alexrdclement.uiplayground.components.core.ButtonStyle
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.components.util.restore
import com.alexrdclement.uiplayground.components.util.save
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.ShapeToken
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ButtonDemo(
    state: ButtonDemoState = rememberButtonDemoState(),
    control: ButtonDemoControl = rememberButtonDemoControl(state),
    modifier: Modifier = Modifier,
) {
    Demo(
        controls = control.controls,
        modifier = modifier
            .fillMaxSize()
    ) {
        ButtonDemo(
            state = state,
            control = control,
        )
    }
}

@Composable
fun BoxWithConstraintsScope.ButtonDemo(
    modifier: Modifier = Modifier,
    state: ButtonDemoState = rememberButtonDemoState(),
    control: ButtonDemoControl = rememberButtonDemoControl(state),
) {
    val density = LocalDensity.current
    LaunchedEffect(control, this.maxWidth, density) {
        with(density) { control.onSizeChanged(this@ButtonDemo.maxWidth) }
    }
    Button(
        onClick = {},
        style = state.style,
        shape = state.shapeToken,
        enabled = state.enabled,
        modifier = modifier
            .width(state.width)
            .align(Alignment.Center)
            .padding(PlaygroundTheme.spacing.medium)
    ) {
        BoxWithConstraints(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextDemo(
                state = state.textDemoState,
                control = control.textDemoControl,
            )
        }
    }
}

@Composable
fun rememberButtonDemoState(): ButtonDemoState = rememberSaveable(
    saver = ButtonDemoStateSaver,
) { ButtonDemoState() }

@Stable
class ButtonDemoState(
    shapeInitial: ShapeToken = ShapeToken.Primary,
    enabledInitial: Boolean = true,
    styleInitial: ButtonStyle = ButtonStyle.Outline,
    maxWidthInitial: Dp = 0.dp,
    widthInitial: Dp = 200.dp,
    val textDemoState: TextDemoState = TextDemoState(
        initialText = "Button",
        textAlignInitial = TextAlign.Center,
    ),
) {
    var shapeToken by mutableStateOf(shapeInitial)
        internal set
    var enabled by mutableStateOf(enabledInitial)
        internal set
    var style by mutableStateOf(styleInitial)
        internal set
    var maxWidth by mutableStateOf(maxWidthInitial)
        internal set
    var width by mutableStateOf(widthInitial)
        internal set
}

private const val enabledKey = "enabled"
private const val styleKey = "style"
private const val maxWidthKey = "maxWidth"
private const val widthKey = "width"
private const val textDemoStateKey = "textDemoState"

val ButtonDemoStateSaver = mapSaverSafe(
    save = { value ->
        mapOf(
            enabledKey to value.enabled,
            styleKey to value.style.name,
            maxWidthKey to value.maxWidth.value,
            widthKey to value.width.value,
            textDemoStateKey to save(value.textDemoState, TextDemoStateSaver, this),
        )
    },
    restore = { map ->
        ButtonDemoState(
            enabledInitial = map[enabledKey] as Boolean,
            styleInitial = ButtonStyle.valueOf(map[styleKey] as String),
            maxWidthInitial = (map[maxWidthKey] as Float).dp,
            widthInitial = (map[widthKey] as Float).dp,
            textDemoState = restore(map[textDemoStateKey], TextDemoStateSaver)!!
        )
    },
)

@Composable
fun rememberButtonDemoControl(
    state: ButtonDemoState,
): ButtonDemoControl = remember(state) { ButtonDemoControl(state) }

@Stable
class ButtonDemoControl(
    val state: ButtonDemoState,
) {
    val shapeControl = enumControl(
        name = "Shape",
        values = { ShapeToken.entries },
        selectedValue = { state.shapeToken },
        onValueChange = { state.shapeToken = it },
    )

    val styleControl = enumControl(
        name = "Style",
        values = { ButtonStyle.entries },
        selectedValue = { state.style },
        onValueChange = { state.style = it },
    )

    val enabledControl = Control.Toggle(
        name = "Enabled",
        value = { state.enabled },
        onValueChange = { state.enabled = it },
    )

    val widthControl = Control.Slider(
        name = "Width",
        value = { state.width.value },
        onValueChange = { state.width = it.dp },
        valueRange = { 0f..state.maxWidth.value },
    )

    val textDemoControl = TextDemoControl(state.textDemoState)
    val textDemoControls = Control.ControlColumn(
        name = "Text",
        indent = true,
        controls = { textDemoControl.controls },
    )

    val controls = persistentListOf(
        shapeControl,
        enabledControl,
        styleControl,
        widthControl,
        textDemoControls,
    )

    fun onSizeChanged(width: Dp) {
        if (state.maxWidth == 0.dp) {
            state.width = width
            state.textDemoState.width = width
        }
        state.maxWidth = width
        state.textDemoState.maxWidth = width
        if (state.width > state.maxWidth) {
            state.width = state.maxWidth
        }
    }
}
