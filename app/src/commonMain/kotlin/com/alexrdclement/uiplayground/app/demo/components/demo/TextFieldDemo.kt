package com.alexrdclement.uiplayground.app.demo.components.demo

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.allCaps
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.control.Controls
import com.alexrdclement.uiplayground.app.demo.util.onlyDigits
import com.alexrdclement.uiplayground.components.HorizontalDivider
import com.alexrdclement.uiplayground.components.TextField
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList

private val keyboardTypes = persistentListOf(
    KeyboardType.Text,
    KeyboardType.Number,
    KeyboardType.Ascii,
    KeyboardType.Decimal,
    KeyboardType.Email,
    KeyboardType.NumberPassword,
    KeyboardType.Password,
    KeyboardType.Phone,
    KeyboardType.Unspecified,
    KeyboardType.Uri,
)

private val keyboardCapitalizations = persistentListOf(
    KeyboardCapitalization.Characters,
    KeyboardCapitalization.None,
    KeyboardCapitalization.Sentences,
    KeyboardCapitalization.Unspecified,
    KeyboardCapitalization.Words,
)

private enum class LineLimits {
    SingleLine,
    Multiline,
}

private enum class InputTransformations {
    None,
    AllCaps,
    OnlyDigits,
}

@Composable
fun TextFieldDemo() {
    val textFieldState = rememberTextFieldState(initialText = "Hello world")
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

    var enabled by remember { mutableStateOf(true) }
    val enabledControl = Control.Toggle(
        name = "Enabled",
        value = enabled,
        onValueChange = {
            enabled = it
        }
    )

    var keyboardType by remember { mutableStateOf(KeyboardType.Unspecified) }
    val keyboardTypeControl = Control.Dropdown(
        name = "Keyboard type",
        values = keyboardTypes.map {
            Control.Dropdown.DropdownItem(
                name = it.toString(),
                value = it,
            )
        }.toImmutableList(),
        selectedIndex = keyboardTypes.indexOf(keyboardType),
        onValueChange = {
            keyboardType = keyboardTypes[it]
        },
    )

    var keyboardCapitalization by remember { mutableStateOf(KeyboardCapitalization.Unspecified) }
    val keyboardCapitalizationControl = Control.Dropdown(
        name = "Keyboard capitalization",
        values = keyboardCapitalizations.map {
            Control.Dropdown.DropdownItem(
                name = it.toString(),
                value = it,
            )
        }.toImmutableList(),
        selectedIndex = keyboardCapitalizations.indexOf(keyboardCapitalization),
        onValueChange = {
            keyboardCapitalization = keyboardCapitalizations[it]
        }
    )

    var autoCorrectEnabled by remember { mutableStateOf(true) }
    val autoCorrectEnabledControl = Control.Toggle(
        name = "Auto-correct",
        value = autoCorrectEnabled,
        onValueChange = { autoCorrectEnabled = it }
    )

    var showKeyboardOnFocus by remember { mutableStateOf(true) }
    val showKeyboardOnFocusControl = Control.Toggle(
        name = "Show keyboard on focus",
        value = showKeyboardOnFocus,
        onValueChange = { showKeyboardOnFocus = it }
    )

    var lineLimits by remember { mutableStateOf(LineLimits.SingleLine) }
    val lineLimitsControl = Control.Dropdown(
        name = "Line limits",
        values = LineLimits.entries.map {
            Control.Dropdown.DropdownItem(
                name = it.name,
                value = it,
            )
        }.toImmutableList(),
        selectedIndex = LineLimits.entries.indexOf(lineLimits),
        onValueChange = {
            lineLimits = LineLimits.entries[it]
        }
    )

    var minHeightInLines by remember { mutableIntStateOf(1) }
    val minHeightInLinesTextFieldState = rememberTextFieldState(
        initialText = minHeightInLines.toString(),
    )
    val minHeightInLinesControl = Control.TextField(
        name = "Min height in lines",
        textFieldState = minHeightInLinesTextFieldState,
        enabled = lineLimits == LineLimits.Multiline,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
        ),
        inputTransformation = InputTransformation.onlyDigits(),
    )

    var maxHeightInLines by remember { mutableIntStateOf(Int.MAX_VALUE) }
    val maxHeightInLinesTextFieldState = rememberTextFieldState(
        initialText = maxHeightInLines.toString(),
    )
    val maxHeightInLinesControl = Control.TextField(
        name = "Max height in lines",
        textFieldState = maxHeightInLinesTextFieldState,
        enabled = lineLimits == LineLimits.Multiline,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
        ),
        inputTransformation = InputTransformation.onlyDigits(),
    )

    var inputTransformation by remember { mutableStateOf(InputTransformations.None) }
    val inputTransformationControl = Control.Dropdown(
        name = "Input Transformations",
        values = InputTransformations.entries.map {
            Control.Dropdown.DropdownItem(
                name = it.name,
                value = it,
            )
        }.toImmutableList(),
        selectedIndex = InputTransformations.entries.indexOf(inputTransformation),
        onValueChange = {
            inputTransformation = InputTransformations.entries[it]
        }
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
            TextField(
                state = textFieldState,
                enabled = enabled,
                lineLimits = when (lineLimits) {
                    LineLimits.SingleLine -> TextFieldLineLimits.SingleLine
                    LineLimits.Multiline -> TextFieldLineLimits.MultiLine(
                        minHeightInLines = minHeightInLines,
                        maxHeightInLines = maxHeightInLines,
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = keyboardType,
                    autoCorrectEnabled = autoCorrectEnabled,
                    capitalization = keyboardCapitalization,
                    showKeyboardOnFocus = showKeyboardOnFocus,
                ),
                inputTransformation = when (inputTransformation) {
                    InputTransformations.None -> null
                    InputTransformations.AllCaps -> InputTransformation.allCaps(Locale.current)
                    InputTransformations.OnlyDigits -> InputTransformation.onlyDigits()
                },
                textStyle = style.toCompose(),
                modifier = Modifier
                    .width(width)
                    .padding(vertical = PlaygroundTheme.spacing.medium)
            )
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        Controls(
            controls = persistentListOf(
                textFieldControl,
                styleControl,
                widthControl,
                enabledControl,
                keyboardTypeControl,
                keyboardCapitalizationControl,
                autoCorrectEnabledControl,
                showKeyboardOnFocusControl,
                lineLimitsControl,
                minHeightInLinesControl,
                maxHeightInLinesControl,
                inputTransformationControl,
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

