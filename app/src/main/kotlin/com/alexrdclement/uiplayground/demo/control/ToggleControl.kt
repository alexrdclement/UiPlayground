package com.alexrdclement.uiplayground.demo.control

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alexrdclement.uiplayground.components.Checkbox
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.ui.preview.UiPlaygroundPreview

@Composable
fun ToggleControl(
    control: Control.Toggle,
    modifier: Modifier = Modifier,
    includeTitle: Boolean = true,
) {
    Column(modifier = modifier) {
        if (includeTitle) {
            Text(control.name)
            Spacer(modifier = Modifier.height(PlaygroundTheme.spacing.small))
        }

        Checkbox(control.value, onCheckedChange = control.onValueChange)
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        var on by remember { mutableStateOf(false) }
        val control by remember {
            mutableStateOf(
                Control.Toggle(
                    name = "Color",
                    value = on,
                    onValueChange = { on = it }
                )
            )
        }
        ToggleControl(control = control)
    }
}
