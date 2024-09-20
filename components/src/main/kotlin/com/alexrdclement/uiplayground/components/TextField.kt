package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.KeyboardActionHandler
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldDecorator
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun TextField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    inputTransformation: InputTransformation? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onKeyboardAction: KeyboardActionHandler? = null,
    lineLimits: TextFieldLineLimits = TextFieldLineLimits.Default,
    onTextLayout: (Density.(getResult: () -> TextLayoutResult?) -> Unit)? = null,
    interactionSource: MutableInteractionSource? = null,
    cursorBrush: Brush = TextFieldDefaults.CursorBrush,
    outputTransformation: OutputTransformation? = null,
    decorator: TextFieldDecorator? = null,
    scrollState: ScrollState = rememberScrollState(),
) {
    BasicTextField(
        state = state,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        inputTransformation = inputTransformation,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        onKeyboardAction = onKeyboardAction,
        lineLimits = lineLimits,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        cursorBrush = cursorBrush,
        outputTransformation = outputTransformation,
        decorator = decorator,
        scrollState = scrollState,
    )
}

object TextFieldDefaults {
    val CursorBrush: Brush
        @Composable
        get() = SolidColor(PlaygroundTheme.colorScheme.primary)
}

@Preview
@Composable
private fun Preview() {
    PlaygroundTheme {
        Surface {
            TextField(
                state = rememberTextFieldState("text"),
            )
        }
    }
}
