package com.alexrdclement.uiplayground.app.demo.components.demo

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.DemoWithControls
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.control.Controls
import com.alexrdclement.uiplayground.components.HorizontalDivider
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

private enum class Overflow {
    Clip,
    Ellipsis,
    Visible,
    StartEllipsis,
    MiddleEllipsis,
}

private enum class LineHeightAlignment {
    Top,
    Center,
    Proportional,
    Bottom,
}

private enum class LineHeightTrim {
    FirstLineTop,
    LastLineBottom,
    Both,
    None,
}

private enum class LineHeightMode {
    Fixed,
    Minimum,
}

@Composable
fun TextDemo(
    modifier: Modifier = Modifier,
) {
    val textFieldState = rememberTextFieldState(initialText = "Hello world")
    val text by snapshotFlow { textFieldState.text.toString() }
        .collectAsState(initial = textFieldState.text.toString())
    val textFieldControl = Control.TextField(
        name = "Text",
        textFieldState = textFieldState,
        includeLabel = false,
    )

    var style by remember { mutableStateOf(TextStyle.Headline) }
    val styleControl = Control.Dropdown(
        name = "Style",
        values = TextStyle.entries.map {
            Control.Dropdown.DropdownItem(
                name = it.name,
                value = it,
            )
        }.toPersistentList(),
        selectedIndex = TextStyle.entries.indexOf(style),
        onValueChange = { style = TextStyle.entries[it] },
    )

    val lineHeightStyleDefault = LineHeightStyle.Default

    val lineHeightAlignmentDefault = lineHeightStyleDefault.alignment.toDomain() ?: LineHeightAlignment.Proportional
    var lineHeightAlignment by remember { mutableStateOf(lineHeightAlignmentDefault) }
    val lineHeightAlignmentControl = Control.Dropdown(
        name = "Line height alignment",
        values = LineHeightAlignment.entries.map {
            Control.Dropdown.DropdownItem(
                name = it.name,
                value = it,
            )
        }.toPersistentList(),
        onValueChange = { lineHeightAlignment = LineHeightAlignment.entries[it] },
        selectedIndex = LineHeightAlignment.entries.indexOf(lineHeightAlignment),
    )

    val lineHeightTrimDefault = lineHeightStyleDefault.trim.toDomain() ?: LineHeightTrim.Both
    var lineHeightTrim by remember { mutableStateOf(lineHeightTrimDefault) }
    val lineHeightTrimControl = Control.Dropdown(
        name = "Line height trim",
        values = LineHeightTrim.entries.map {
            Control.Dropdown.DropdownItem(
                name = it.name,
                value = it,
            )
        }.toPersistentList(),
        onValueChange = { lineHeightTrim = LineHeightTrim.entries[it] },
        selectedIndex = LineHeightTrim.entries.indexOf(lineHeightTrim),
    )

    val lineHeightModeDefault = lineHeightStyleDefault.mode.toDomain() ?: LineHeightMode.Fixed
    var lineHeightMode by remember { mutableStateOf(lineHeightModeDefault) }
    val lineHeightModeControl = Control.Dropdown(
        name = "Line height mode",
        values = LineHeightMode.entries.map {
            Control.Dropdown.DropdownItem(
                name = it.name,
                value = it,
            )
        }.toPersistentList(),
        onValueChange = { lineHeightMode = LineHeightMode.entries[it] },
        selectedIndex = LineHeightMode.entries.indexOf(lineHeightMode),
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

    var autoSize by remember { mutableStateOf(false) }
    val autoSizeControl = Control.Toggle(
        name = "Auto-size text",
        value = autoSize,
        onValueChange = {
            autoSize = it
        }
    )

    var softWrap by remember { mutableStateOf(false) }
    val softWrapControl = Control.Toggle(
        name = "Soft wrap",
        value = softWrap,
        onValueChange = {
            softWrap = it
        }
    )

    var showBorder by remember { mutableStateOf(false) }
    val showBorderControl = Control.Toggle(
        name = "Show border",
        value = showBorder,
        onValueChange = {
            showBorder = it
        }
    )

    var overflow by remember { mutableStateOf(Overflow.Clip) }
    val overflowControl = Control.Dropdown(
        name = "Overflow",
        values = Overflow.entries.map {
            Control.Dropdown.DropdownItem(
                name = it.name,
                value = it
            )
        }.toPersistentList(),
        selectedIndex = Overflow.entries.indexOf(overflow),
        onValueChange = { overflow = Overflow.entries[it] },
    )

    DemoWithControls(
        controls = persistentListOf(
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
        ),
        modifier = modifier.fillMaxSize()
    ) {
        maxWidthPx = with(LocalDensity.current) { this@DemoWithControls.maxWidth.toPx().toInt() }

        Text(
            text = text,
            style = style.toCompose().copy(
                lineHeightStyle = lineHeightStyleDefault.copy(
                    alignment = lineHeightAlignment.toCompose(),
                    trim = lineHeightTrim.toCompose(),
                    mode = lineHeightMode.toCompose(),
                )
            ),
            autoSize = if (autoSize) TextAutoSize.StepBased() else null,
            softWrap = softWrap,
            overflow = when (overflow) {
                Overflow.Clip -> TextOverflow.Clip
                Overflow.Ellipsis -> TextOverflow.Ellipsis
                Overflow.Visible -> TextOverflow.Visible
                Overflow.StartEllipsis -> TextOverflow.StartEllipsis
                Overflow.MiddleEllipsis -> TextOverflow.MiddleEllipsis
            },
            modifier = Modifier
                .align(Alignment.Center)
                .width(width)
                .padding(vertical = PlaygroundTheme.spacing.medium)
                .then(
                    if (showBorder) Modifier.border(1.dp, PlaygroundTheme.colorScheme.primary)
                    else Modifier
                )
        )
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
