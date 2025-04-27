package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
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
                }
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