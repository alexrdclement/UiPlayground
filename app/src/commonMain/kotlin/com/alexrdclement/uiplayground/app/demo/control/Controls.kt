package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Controls(
    controls: ImmutableList<Control>,
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
                    is Control.ControlRow -> ControlsRow(controls = control.controls)
                }
            }
        }
    }
}

@Composable
fun ControlsRow(
    controls: ImmutableList<Control>,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        for (control in controls) {
            when (control) {
                is Control.Slider -> SliderControl(control = control)
                is Control.Dropdown<*> -> DropdownControl(control = control)
                is Control.Toggle -> ToggleControl(control = control)
                is Control.TextField -> TextFieldControl(control = control)
                is Control.ControlRow -> Controls(controls = control.controls)
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        Controls(
            controls = persistentListOf(
                Control.Slider(
                    name = "Amount",
                    value = 0.5f,
                    onValueChange = {},
                ),
                Control.Slider(
                    name = "Amount 2",
                    value = 0.5f,
                    onValueChange = {},
                )
            ),
        )
    }
}
