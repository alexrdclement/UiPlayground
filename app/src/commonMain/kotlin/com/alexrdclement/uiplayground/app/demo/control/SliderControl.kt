package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import com.alexrdclement.uiplayground.components.core.Slider
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SliderControl(
    control: Control.Slider,
    modifier: Modifier = Modifier
) {
    val name by rememberUpdatedState(control.name())
    val value by rememberUpdatedState(control.value())
    val valueRange by rememberUpdatedState(control.valueRange())

    Column(
        verticalArrangement = Arrangement.spacedBy(PlaygroundTheme.spacing.small),
        modifier = modifier,
    ) {
        Text(text = name, style = PlaygroundTheme.typography.labelLarge)
        Slider(
            value = value,
            onValueChange = control.onValueChange,
            valueRange = valueRange,
            modifier = Modifier.semantics {
                contentDescription = name
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
                value = { value },
                onValueChange = { value = it },
            )
        }
        SliderControl(control = control)
    }
}
