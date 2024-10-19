package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.alexrdclement.uiplayground.components.Slider
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

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
