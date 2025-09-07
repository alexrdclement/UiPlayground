package com.alexrdclement.uiplayground.app.demo.components.demo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.DemoWithControls
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.components.Button
import com.alexrdclement.uiplayground.components.ButtonStyle
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ButtonDemo(
    modifier: Modifier = Modifier,
) {
    var enabled by remember { mutableStateOf(true) }
    val enabledControl = Control.Toggle(
        name = "Enabled",
        value = enabled,
        onValueChange = { enabled = it },
    )

    var style by remember { mutableStateOf(ButtonStyle.Outline) }
    val styleControl = Control.Dropdown(
        name = "Style",
        values = ButtonStyle.entries.map {
            Control.Dropdown.DropdownItem(
                name = it.name,
                value = it,
            )
        }.toPersistentList(),
        onValueChange = { style = ButtonStyle.entries[it] },
        selectedIndex = ButtonStyle.entries.indexOf(style),
    )

    var maxWidthPx by remember { mutableIntStateOf(0) }
    val maxWidth = with(LocalDensity.current) { maxWidthPx.toDp() }
    var width by remember(maxWidth) { mutableStateOf(maxWidth) }
    val widthControl = Control.Slider(
        name = "Width",
        value = width.value,
        onValueChange = {
            width = it.dp
        },
        valueRange = 0f..maxWidth.value,
    )

    var autoSizeText by remember { mutableStateOf(true) }
    val autoSizeControl = Control.Toggle(
        name = "Auto-size text",
        value = autoSizeText,
        onValueChange = {
            autoSizeText = it
        }
    )

    var softWrap by remember { mutableStateOf(false) }
    val softWrapControl = Control.Toggle(
        name = "Soft-wrap text",
        value = softWrap,
        onValueChange = {
            softWrap = it
        }
    )

    DemoWithControls(
        controls = persistentListOf(
            enabledControl,
            styleControl,
            widthControl,
            autoSizeControl,
            softWrapControl,
        ),
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged { maxWidthPx = it.width },
    ) {
        Button(
            onClick = {},
            style = style,
            enabled = enabled,
            modifier = Modifier
                .width(width)
                .align(Alignment.Center)
                .padding(PlaygroundTheme.spacing.medium)
        ) {
            Text(
                text = "Button",
                style = PlaygroundTheme.typography.labelLarge,
                softWrap = softWrap,
                autoSize = if (autoSizeText) TextAutoSize.StepBased() else null,
            )
        }
    }
}
