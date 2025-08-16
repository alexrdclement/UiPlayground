package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.components.TextField
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun TextFieldControl(
    control: Control.TextField,
    modifier: Modifier = Modifier,
) {
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
            Text(text = control.name)
            Spacer(modifier = Modifier.width(PlaygroundTheme.spacing.small))
        }
        TextField(
            state = control.textFieldState,
            enabled = control.enabled,
            inputTransformation = control.inputTransformation,
            keyboardOptions = control.keyboardOptions,
        )
    }
}
