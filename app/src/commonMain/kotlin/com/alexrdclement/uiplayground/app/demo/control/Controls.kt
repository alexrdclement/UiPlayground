package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun Controls(
    controls: List<Control>,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        for (control in controls) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = PlaygroundTheme.spacing.small)
            ) {
                when (control) {
                    is Control.Slider -> SliderControl(control = control)
                    is Control.Dropdown<*> -> DropdownControlRow(control = control)
                    is Control.Toggle -> ToggleControlRow(control = control)
                    is Control.TextField -> TextFieldControl(control = control)
                }
            }
        }
    }
}