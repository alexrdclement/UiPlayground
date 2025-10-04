package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.components.core.TextField
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TextFieldControl(
    control: Control.TextField,
    modifier: Modifier = Modifier,
) {
    val enabled by rememberUpdatedState(control.enabled())
    val keyboardOptions by rememberUpdatedState(control.keyboardOptions())
    val inputTransformation by rememberUpdatedState(control.inputTransformation())

    Row(
        horizontalArrangement = Arrangement.spacedBy(PlaygroundTheme.spacing.small),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .then(
                if (control.includeLabel) Modifier.padding(vertical = PlaygroundTheme.spacing.small)
                else Modifier.padding(bottom = PlaygroundTheme.spacing.small)
            ),
    ) {
        if (control.includeLabel) {
            Text(
                text = control.name,
                style = PlaygroundTheme.typography.labelLarge,
                modifier = Modifier.weight(1f, fill = false),
            )
            Spacer(modifier = Modifier.width(PlaygroundTheme.spacing.small))
        }
        TextField(
            state = control.textFieldState,
            textStyle = PlaygroundTheme.typography.labelLarge,
            enabled = enabled,
            inputTransformation = inputTransformation,
            keyboardOptions = keyboardOptions,
        )
    }
}

@Preview
@Composable
fun TextFieldControlPreview() {
    val textFieldState = rememberTextFieldState(initialText = "Text Field")
    PlaygroundTheme {
        TextFieldControl(
            control = Control.TextField(
                name = "Label",
                textFieldState = textFieldState,
                includeLabel = true,
            ),
        )
    }
}
