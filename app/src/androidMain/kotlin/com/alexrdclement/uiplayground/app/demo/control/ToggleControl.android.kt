package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview

@Preview
@Composable
private fun ToggleControlPreview() {
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

@Preview
@Composable
private fun ToggleControlRowPreview() {
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
        ToggleControlRow(control = control)
    }
}
