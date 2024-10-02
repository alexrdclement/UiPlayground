package com.alexrdclement.uiplayground.demo.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.alexrdclement.uiplayground.components.Slider
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.ui.preview.UiPlaygroundPreview

@Composable
fun SliderControl(
    control: Control.Slider,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(PlaygroundTheme.spacing.small),
        modifier = modifier,
    ) {
        Text(text = control.name)
        Slider(
            value = control.value,
            onValueChange = control.onValueChange,
            valueRange = control.valueRange,
            modifier = Modifier.semantics {
                contentDescription = control.name
            }
        )
    }
}

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
