package com.alexrdclement.uiplayground.app.demo.components.demo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.components.Button
import com.alexrdclement.uiplayground.components.ButtonStyle
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ButtonDemo(
    state: ButtonDemoState = rememberButtonDemoState(),
    control: ButtonDemoControl = rememberButtonDemoControl(state),
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    Demo(
        controls = control.controls,
        modifier = modifier
            .fillMaxSize()

    ) {
        LaunchedEffect(this@Demo.maxWidth, density) {
            with(density) { control.onSizeChanged(this@Demo.maxWidth) }
        }
        Button(
            onClick = {},
            style = state.style,
            enabled = state.enabled,
            modifier = Modifier
                .width(state.width)
                .align(Alignment.Center)
                .padding(PlaygroundTheme.spacing.medium)
        ) {
            Text(
                text = "Button",
                style = PlaygroundTheme.typography.labelLarge,
                softWrap = state.softWrap,
                autoSize = if (state.autoSizeText) TextAutoSize.StepBased() else null,
            )
        }
    }
}

@Composable
fun rememberButtonDemoState(): ButtonDemoState = remember { ButtonDemoState() }

@Stable
class ButtonDemoState {
    internal var enabled by mutableStateOf(true)
    internal var style by mutableStateOf(ButtonStyle.Outline)
    internal var maxWidth by mutableStateOf(0.dp)
    internal var width by mutableStateOf(200.dp)
    internal var autoSizeText by mutableStateOf(true)
    internal var softWrap by mutableStateOf(false)
}

@Composable
fun rememberButtonDemoControl(
    state: ButtonDemoState,
): ButtonDemoControl = remember(state) { ButtonDemoControl(state) }

@Stable
class ButtonDemoControl(
    val state: ButtonDemoState,
) {
    val styleControl = Control.Dropdown(
        name = "Style",
        values = {
            ButtonStyle.entries.map {
                Control.Dropdown.DropdownItem(
                    name = it.name,
                    value = it,
                )
            }.toPersistentList()
        },
        onValueChange = { state.style = ButtonStyle.entries[it] },
        selectedIndex = { ButtonStyle.entries.indexOf(state.style) },
    )

    val enabledControl = Control.Toggle(
        name = "Enabled",
        value = { state.enabled },
        onValueChange = { state.enabled = it },
    )

    val widthControl = Control.Slider(
        name = "Width",
        value = { state.width.value },
        onValueChange = {
            state.width = it.dp
        },
        valueRange = { 0f..state.maxWidth.value },
    )

    val autoSizeControl = Control.Toggle(
        name = "Auto-size text",
        value = { state.autoSizeText },
        onValueChange = {
            state.autoSizeText = it
        }
    )

    val softWrapControl = Control.Toggle(
        name = "Soft-wrap text",
        value = { state.softWrap },
        onValueChange = {
            state.softWrap = it
        }
    )

    val controls = persistentListOf(
        enabledControl,
        styleControl,
        widthControl,
        autoSizeControl,
        softWrapControl,
    )

    fun onSizeChanged(width: Dp) {
        if (state.maxWidth == 0.dp) {
            state.width = width
        }
        state.maxWidth = width
        if (state.width > state.maxWidth) {
            state.width = state.maxWidth
        }
    }
}
