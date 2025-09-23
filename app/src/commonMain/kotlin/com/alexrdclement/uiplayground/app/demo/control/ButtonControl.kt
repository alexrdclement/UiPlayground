package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.components.core.Button
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun ButtonControl(
    control: Control.Button,
    modifier: Modifier = Modifier,
) {
    val enabled by rememberUpdatedState(control.enabled())
    Button(
        modifier = modifier,
        onClick = control.onClick,
        enabled = enabled,
    ) {
        Text(control.name, style = PlaygroundTheme.typography.labelLarge)
    }
}
