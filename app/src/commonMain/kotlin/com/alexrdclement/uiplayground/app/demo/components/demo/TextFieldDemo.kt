package com.alexrdclement.uiplayground.app.demo.components.demo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.allCaps
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.util.onlyDigits
import com.alexrdclement.uiplayground.components.TextField
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList

@Composable
fun TextFieldDemo(
    modifier: Modifier = Modifier,
    state: TextFieldDemoState = rememberTextFieldDemoState(),
    control: TextFieldDemoControl = rememberTextFieldDemoControl(state = state),
) {
    Demo(
        controls = control.controls,
        modifier = modifier
            .fillMaxSize(),
    ) {
        LaunchedEffect(this@Demo.maxWidth) {
            control.onSizeChanged(this@Demo.maxWidth)
        }

        TextField(
            state = state.textFieldState,
            enabled = state.enabled,
            lineLimits = when (state.lineLimits) {
                LineLimits.SingleLine -> TextFieldLineLimits.SingleLine
                LineLimits.Multiline -> TextFieldLineLimits.MultiLine(
                    minHeightInLines = state.minHeightInLines,
                    maxHeightInLines = state.maxHeightInLines,
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = state.keyboardType,
                autoCorrectEnabled = state.autoCorrectEnabled,
                capitalization = state.keyboardCapitalization,
                showKeyboardOnFocus = state.showKeyboardOnFocus,
            ),
            inputTransformation = when (state.inputTransformation) {
                InputTransformations.None -> null
                InputTransformations.AllCaps -> InputTransformation.allCaps(Locale.current)
                InputTransformations.OnlyDigits -> InputTransformation.onlyDigits()
            },
            textStyle = state.style.toCompose(),
            modifier = Modifier
                .align(Alignment.Center)
                .width(state.width)
                .padding(vertical = PlaygroundTheme.spacing.medium)
        )
    }
}

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

enum class LineLimits {
    SingleLine,
    Multiline,
}

enum class InputTransformations {
    None,
    AllCaps,
    OnlyDigits,
}

@Composable
fun rememberTextFieldDemoState(
    initialText: String = "Hello world",
): TextFieldDemoState {
    return remember {
        TextFieldDemoState(
            initialText = initialText,
        )
    }
}

@Stable
class TextFieldDemoState(
    initialText: String,
) {
    val textFieldState = TextFieldState(initialText = initialText)
    val text = snapshotFlow { textFieldState.text.toString() }

    var style by mutableStateOf(TextStyle.Headline)

    var maxWidth by mutableStateOf(0.dp)
    var width by mutableStateOf(200.dp)

    var enabled by mutableStateOf(true)

    var keyboardType by mutableStateOf(KeyboardType.Unspecified)
    var keyboardCapitalization by mutableStateOf(KeyboardCapitalization.Unspecified)
    var autoCorrectEnabled by mutableStateOf(true)
    var showKeyboardOnFocus by mutableStateOf(true)

    var lineLimits by mutableStateOf(LineLimits.SingleLine)
    var minHeightInLines by mutableIntStateOf(1)
    val minHeightInLinesTextFieldState = TextFieldState(initialText = minHeightInLines.toString())
    var maxHeightInLines by mutableIntStateOf(Int.MAX_VALUE)
    var maxHeightInLinesTextFieldState = TextFieldState(initialText = Int.MAX_VALUE.toString())

    var inputTransformation by mutableStateOf(InputTransformations.None)
}

@Composable
fun rememberTextFieldDemoControl(
    state: TextFieldDemoState = rememberTextFieldDemoState(),
) = remember(state) { TextFieldDemoControl(state) }

@Stable
class TextFieldDemoControl(
    val state: TextFieldDemoState,
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

    val widthControl = Control.Slider(
        name = "Width",
        value = { state.width.value },
        onValueChange = {
            state.width = it.dp
        },
        valueRange = { 0f..state.maxWidth.value },
    )

    val enabledControl = Control.Toggle(
        name = "Enabled",
        value = { state.enabled },
        onValueChange = {
            state.enabled = it
        }
    )

    val keyboardTypeControl = Control.Dropdown(
        name = "Keyboard type",
        values = {
            keyboardTypes.map {
                Control.Dropdown.DropdownItem(
                    name = it.toString(),
                    value = it,
                )
            }.toImmutableList()
        },
        selectedIndex = { keyboardTypes.indexOf(state.keyboardType) },
        onValueChange = {
            state.keyboardType = keyboardTypes[it]
        },
    )

    val keyboardCapitalizationControl = Control.Dropdown(
        name = "Keyboard capitalization",
        values = {
            keyboardCapitalizations.map {
                Control.Dropdown.DropdownItem(
                    name = it.toString(),
                    value = it,
                )
            }.toImmutableList()
        },
        selectedIndex = { keyboardCapitalizations.indexOf(state.keyboardCapitalization) },
        onValueChange = {
            state.keyboardCapitalization = keyboardCapitalizations[it]
        }
    )

    val autoCorrectEnabledControl = Control.Toggle(
        name = "Auto-correct",
        value = { state.autoCorrectEnabled },
        onValueChange = { state.autoCorrectEnabled = it }
    )

    val showKeyboardOnFocusControl = Control.Toggle(
        name = "Show keyboard on focus",
        value = { state.showKeyboardOnFocus },
        onValueChange = { state.showKeyboardOnFocus = it }
    )

    val lineLimitsControl = Control.Dropdown(
        name = "Line limits",
        values = {
            LineLimits.entries.map {
                Control.Dropdown.DropdownItem(
                    name = it.name,
                    value = it,
                )
            }.toImmutableList()
        },
        selectedIndex = { LineLimits.entries.indexOf(state.lineLimits) },
        onValueChange = {
            state.lineLimits = LineLimits.entries[it]
        }
    )
    val minHeightInLinesControl = Control.Slider(
        name = "Min height in lines",
        value = { state.minHeightInLines.toFloat() },
        onValueChange = { state.minHeightInLines = it.toInt() },
        valueRange = {
            val max = if (state.maxHeightInLines == Int.MAX_VALUE) 100 else state.maxHeightInLines
            1f..max.toFloat()
        },
    )

    val maxHeightInLinesControl = Control.Slider(
        name = "Max height in lines",
        value = {
            if (state.maxHeightInLines == Int.MAX_VALUE) 100f else state.maxHeightInLines.toFloat()
        },
        onValueChange = { state.maxHeightInLines = if (it >= 100f) Int.MAX_VALUE else it.toInt() },
        valueRange = { state.minHeightInLines.toFloat()..100f },
    )

    val inputTransformationControl = Control.Dropdown(
        name = "Input Transformations",
        values = {
            InputTransformations.entries.map {
                Control.Dropdown.DropdownItem(
                    name = it.name,
                    value = it,
                )
            }.toImmutableList()
        },
        selectedIndex = { InputTransformations.entries.indexOf(state.inputTransformation) },
        onValueChange = {
            state.inputTransformation = InputTransformations.entries[it]
        }
    )

    val keyboardControls = persistentListOf(
        keyboardTypeControl,
        keyboardCapitalizationControl,
        autoCorrectEnabledControl,
        showKeyboardOnFocusControl,
    )

    val lineControls
        get() = if (state.lineLimits == LineLimits.Multiline) {
            persistentListOf(
                lineLimitsControl,
                minHeightInLinesControl,
                maxHeightInLinesControl,
            )
        } else persistentListOf(
            lineLimitsControl,
        )

    val controls
        get() = persistentListOf(
            textFieldControl,
            styleControl,
            widthControl,
            enabledControl,
            *keyboardControls.toTypedArray(),
            *lineControls.toTypedArray(),
            inputTransformationControl,
        )

    fun onSizeChanged(width: Dp) {
        if (state.maxWidth == 0.dp) {
            state.width = width
        }
        state.maxWidth = width
        if (state.width > width) {
            state.width = width
        }
    }
}

