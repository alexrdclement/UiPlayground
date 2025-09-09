package com.alexrdclement.uiplayground.app.demo.components.demo

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun TextDemo(
    modifier: Modifier = Modifier,
    state: TextDemoState = rememberTextDemoState(),
    control: TextDemoControl = rememberTextDemoControl(state),
) {
    val text by state.text.collectAsState(initial = state.text.toString())

    Demo(
        controls = control.controls,
        modifier = modifier.fillMaxSize()
    ) {
        LaunchedEffect(this@Demo.maxWidth) {
            control.onSizeChanged(this@Demo.maxWidth)
        }
        Text(
            text = text,
            style = state.style.toCompose().copy(
                lineHeightStyle = TextDemoState.lineHeightStyleDefault.copy(
                    alignment = state.lineHeightAlignment.toCompose(),
                    trim = state.lineHeightTrim.toCompose(),
                    mode = state.lineHeightMode.toCompose(),
                )
            ),
            autoSize = if (state.autoSize) TextAutoSize.StepBased() else null,
            softWrap = state.softWrap,
            overflow = when (state.overflow) {
                Overflow.Clip -> TextOverflow.Clip
                Overflow.Ellipsis -> TextOverflow.Ellipsis
                Overflow.Visible -> TextOverflow.Visible
                Overflow.StartEllipsis -> TextOverflow.StartEllipsis
                Overflow.MiddleEllipsis -> TextOverflow.MiddleEllipsis
            },
            modifier = Modifier
                .align(Alignment.Center)
                .width(state.width)
                .padding(vertical = PlaygroundTheme.spacing.medium)
                .then(
                    if (state.showBorder) Modifier.border(
                        1.dp,
                        PlaygroundTheme.colorScheme.primary
                    )
                    else Modifier
                )
        )
    }
}

enum class Overflow {
    Clip,
    Ellipsis,
    Visible,
    StartEllipsis,
    MiddleEllipsis,
}

enum class LineHeightAlignment {
    Top,
    Center,
    Proportional,
    Bottom,
}

enum class LineHeightTrim {
    FirstLineTop,
    LastLineBottom,
    Both,
    None,
}

enum class LineHeightMode {
    Fixed,
    Minimum,
}


@Composable
fun rememberTextDemoState(
    initialText: String = "Hello world",
) = remember {
    TextDemoState(initialText)
}

@Stable
class TextDemoState(
    initialText: String = "Hello world",
) {

    companion object {
        val lineHeightStyleDefault = LineHeightStyle.Default
        val lineHeightAlignmentDefault =
            lineHeightStyleDefault.alignment.toDomain() ?: LineHeightAlignment.Proportional
        val lineHeightTrimDefault = lineHeightStyleDefault.trim.toDomain() ?: LineHeightTrim.Both
        val lineHeightModeDefault = lineHeightStyleDefault.mode.toDomain() ?: LineHeightMode.Fixed
    }

    internal val textFieldState = TextFieldState(initialText = initialText)
    internal val text get() = snapshotFlow { textFieldState.text.toString() }

    internal var style by mutableStateOf(TextStyle.Headline)

    internal var lineHeightAlignment by mutableStateOf(lineHeightAlignmentDefault)
    internal var lineHeightTrim by mutableStateOf(lineHeightTrimDefault)

    internal var lineHeightMode by mutableStateOf(lineHeightModeDefault)

    internal var maxWidth by mutableStateOf(0.dp)

    internal var width by mutableStateOf(maxWidth)
    internal var autoSize by mutableStateOf(false)
    internal var softWrap by mutableStateOf(false)
    internal var showBorder by mutableStateOf(false)
    internal var overflow by mutableStateOf(Overflow.Clip)
}

@Composable
fun rememberTextDemoControl(
    textDemoState: TextDemoState,
) = remember(textDemoState) {
    TextDemoControl(textDemoState)
}

@Stable
class TextDemoControl(
    private var state: TextDemoState,
) {
    val textFieldControl = Control.TextField(
        name = "Text",
        textFieldState = state.textFieldState,
        includeLabel = false,
    )

    val styleControl = Control.Dropdown(
        name = "Style",
        values = {
            TextStyle.entries.map {
                Control.Dropdown.DropdownItem(
                    name = it.name,
                    value = it,
                )
            }.toPersistentList()
        },
        selectedIndex = { TextStyle.entries.indexOf(state.style) },
        onValueChange = { state.style = TextStyle.entries[it] },
    )

    val lineHeightAlignmentControl = Control.Dropdown(
        name = "Line height alignment",
        values = {
            LineHeightAlignment.entries.map {
                Control.Dropdown.DropdownItem(
                    name = it.name,
                    value = it,
                )
            }.toPersistentList()
        },
        onValueChange = { state.lineHeightAlignment = LineHeightAlignment.entries[it] },
        selectedIndex = { LineHeightAlignment.entries.indexOf(state.lineHeightAlignment) },
    )

    val lineHeightTrimControl = Control.Dropdown(
        name = "Line height trim",
        values = {
            LineHeightTrim.entries.map {
                Control.Dropdown.DropdownItem(
                    name = it.name,
                    value = it,
                )
            }.toPersistentList()
        },
        onValueChange = { state.lineHeightTrim = LineHeightTrim.entries[it] },
        selectedIndex = { LineHeightTrim.entries.indexOf(state.lineHeightTrim) },
    )

    val lineHeightModeControl = Control.Dropdown(
        name = "Line height mode",
        values = {
            LineHeightMode.entries.map {
                Control.Dropdown.DropdownItem(
                    name = it.name,
                    value = it,
                )
            }.toPersistentList()
        },
        onValueChange = { state.lineHeightMode = LineHeightMode.entries[it] },
        selectedIndex = { LineHeightMode.entries.indexOf(state.lineHeightMode) },
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
        value = { state.autoSize },
        onValueChange = {
            state.autoSize = it
        }
    )

    val softWrapControl = Control.Toggle(
        name = "Soft wrap",
        value = { state.softWrap },
        onValueChange = {
            state.softWrap = it
        }
    )

    val showBorderControl = Control.Toggle(
        name = "Show border",
        value = { state.showBorder },
        onValueChange = {
            state.showBorder = it
        }
    )

    val overflowControl = Control.Dropdown(
        name = "Overflow",
        values = {
            Overflow.entries.map {
                Control.Dropdown.DropdownItem(
                    name = it.name,
                    value = it
                )
            }.toPersistentList()
        },
        selectedIndex = { Overflow.entries.indexOf(state.overflow) },
        onValueChange = { state.overflow = Overflow.entries[it] },
    )

    val controls = persistentListOf(
        textFieldControl,
        styleControl,
        lineHeightAlignmentControl,
        lineHeightTrimControl,
        lineHeightModeControl,
        widthControl,
        autoSizeControl,
        softWrapControl,
        showBorderControl,
        overflowControl,
    )

    fun onSizeChanged(width: Dp) {
        if (state.maxWidth == 0.dp) {
            state.width = width
        }
        state.maxWidth = width
        if (state.width.value > state.maxWidth.value) {
            state.width = state.maxWidth
        }
    }
}

private fun LineHeightAlignment.toCompose() = when (this) {
    LineHeightAlignment.Top -> LineHeightStyle.Alignment.Top
    LineHeightAlignment.Center -> LineHeightStyle.Alignment.Center
    LineHeightAlignment.Proportional -> LineHeightStyle.Alignment.Proportional
    LineHeightAlignment.Bottom -> LineHeightStyle.Alignment.Bottom
}

private fun LineHeightStyle.Alignment.toDomain() = when (this) {
    LineHeightStyle.Alignment.Top -> LineHeightAlignment.Top
    LineHeightStyle.Alignment.Center -> LineHeightAlignment.Center
    LineHeightStyle.Alignment.Proportional -> LineHeightAlignment.Proportional
    LineHeightStyle.Alignment.Bottom -> LineHeightAlignment.Bottom
    else -> null
}

private fun LineHeightTrim.toCompose() = when (this) {
    LineHeightTrim.FirstLineTop -> LineHeightStyle.Trim.FirstLineTop
    LineHeightTrim.LastLineBottom -> LineHeightStyle.Trim.LastLineBottom
    LineHeightTrim.Both -> LineHeightStyle.Trim.Both
    LineHeightTrim.None -> LineHeightStyle.Trim.None
}

private fun LineHeightStyle.Trim.toDomain() = when (this) {
    LineHeightStyle.Trim.FirstLineTop -> LineHeightTrim.FirstLineTop
    LineHeightStyle.Trim.LastLineBottom -> LineHeightTrim.LastLineBottom
    LineHeightStyle.Trim.Both -> LineHeightTrim.Both
    LineHeightStyle.Trim.None -> LineHeightTrim.None
    else -> null
}

private fun LineHeightMode.toCompose() = when (this) {
    LineHeightMode.Fixed -> LineHeightStyle.Mode.Fixed
    LineHeightMode.Minimum -> LineHeightStyle.Mode.Minimum
}

private fun LineHeightStyle.Mode.toDomain() = when (this) {
    LineHeightStyle.Mode.Fixed -> LineHeightMode.Fixed
    LineHeightStyle.Mode.Minimum -> LineHeightMode.Minimum
    else -> null
}
