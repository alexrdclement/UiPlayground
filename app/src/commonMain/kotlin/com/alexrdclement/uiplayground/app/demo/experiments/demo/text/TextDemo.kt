package com.alexrdclement.uiplayground.app.demo.experiments.demo.text

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
import androidx.compose.foundation.text.AutoSize
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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

private enum class TextStyle {
    Headline,
    Display,
    TitleSmall,
    TitleMedium,
    TitleLarge,
    BodyLarge,
    BodyMedium,
    BodySmall,
    LabelLarge,
    LabelMedium,
    LabelSmall,
}

@Composable
fun TextDemo() {
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

    var autoSize by remember { mutableStateOf(true) }
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .onSizeChanged { maxWidthPx = it.width },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                style = when (style) {
                    TextStyle.Headline -> PlaygroundTheme.typography.headline
                    TextStyle.Display -> PlaygroundTheme.typography.display
                    TextStyle.TitleSmall -> PlaygroundTheme.typography.titleSmall
                    TextStyle.TitleMedium -> PlaygroundTheme.typography.titleMedium
                    TextStyle.TitleLarge -> PlaygroundTheme.typography.titleLarge
                    TextStyle.BodyLarge -> PlaygroundTheme.typography.bodyLarge
                    TextStyle.BodyMedium -> PlaygroundTheme.typography.bodyMedium
                    TextStyle.BodySmall -> PlaygroundTheme.typography.bodySmall
                    TextStyle.LabelLarge -> PlaygroundTheme.typography.labelLarge
                    TextStyle.LabelMedium -> PlaygroundTheme.typography.labelMedium
                    TextStyle.LabelSmall -> PlaygroundTheme.typography.labelSmall
                },
                autoSize = if (autoSize) AutoSize.StepBased() else null,
                softWrap = softWrap,
                overflow = when (overflow) {
                    Overflow.Clip -> TextOverflow.Clip
                    Overflow.Ellipsis -> TextOverflow.Ellipsis
                    Overflow.Visible -> TextOverflow.Visible
                    Overflow.StartEllipsis -> TextOverflow.StartEllipsis
                    Overflow.MiddleEllipsis -> TextOverflow.MiddleEllipsis
                },
                modifier = Modifier
                    .width(width)
                    .padding(vertical = PlaygroundTheme.spacing.medium)
                    .then(
                        if (showBorder) Modifier.border(1.dp, PlaygroundTheme.colorScheme.primary)
                        else Modifier
                    )
            )
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        Controls(
            controls = persistentListOf(
                textFieldControl,
                styleControl,
                widthControl,
                autoSizeControl,
                softWrapControl,
                showBorderControl,
                overflowControl,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .verticalScroll(rememberScrollState())
                .padding(PlaygroundTheme.spacing.medium)
                .navigationBarsPadding(),
        )
    }
}
