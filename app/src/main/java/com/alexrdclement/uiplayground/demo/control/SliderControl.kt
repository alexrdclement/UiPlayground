package com.alexrdclement.uiplayground.demo.control

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alexrdclement.uiplayground.ui.preview.UiPlaygroundPreview

@Composable
fun SliderControl(
    control: Control.Slider,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(text = control.name)
        Slider(
            value = control.value,
            onValueChange = control.onValueChange,
            valueRange = control.valueRange
        )
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        var value by remember { mutableStateOf(0f) }
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
