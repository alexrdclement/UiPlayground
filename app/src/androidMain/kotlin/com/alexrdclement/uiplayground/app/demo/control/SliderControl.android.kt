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
private fun Preview() {
    UiPlaygroundPreview {
        var value by remember { mutableStateOf(0.5f) }
        val control = remember {
            Control.Slider(
                name = "Amount",
                value = value,
                onValueChange = { value = it },
            )
        }
        SliderControl(control = control)
    }
}
