package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import com.alexrdclement.uiplayground.components.Checkbox
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ToggleControl(
    control: Control.Toggle,
    modifier: Modifier = Modifier,
    includeTitle: Boolean = true,
) {
    val checked by rememberUpdatedState(control.value())

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (includeTitle) {
            Text(control.name, style = PlaygroundTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(PlaygroundTheme.spacing.small))
        }

        Checkbox(checked, onCheckedChange = control.onValueChange)
    }
}

@Composable
fun ToggleControlRow(
    control: Control.Toggle,
    modifier: Modifier = Modifier,
    includeTitle: Boolean = true,
) {
    val isChecked by rememberUpdatedState(control.value())

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (includeTitle) {
            Text(control.name, style = PlaygroundTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(PlaygroundTheme.spacing.small))
        }

        Checkbox(isChecked, onCheckedChange = control.onValueChange)
    }
}

@Preview
@Composable
private fun ToggleControlPreview() {
    UiPlaygroundPreview {
        var on by remember { mutableStateOf(false) }
        val control = Control.Toggle(
            name = "Color",
            value = { on },
            onValueChange = { on = it }
        )
        ToggleControl(control = control)
    }
}

@Preview
@Composable
private fun ToggleControlRowPreview() {
    UiPlaygroundPreview {
        var on by remember { mutableStateOf(false) }
        val control = Control.Toggle(
            name = "Color",
            value = { on },
            onValueChange = { on = it }
        )
        ToggleControlRow(control = control)
    }
}
