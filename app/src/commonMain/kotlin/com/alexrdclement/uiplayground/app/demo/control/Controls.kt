package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Controls(
    controls: ImmutableList<Control>,
    modifier: Modifier = Modifier,
    name: String? = null,
    indent: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(vertical = PlaygroundTheme.spacing.small),
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(PlaygroundTheme.spacing.medium)
) {
    Column(
        verticalArrangement = verticalArrangement,
        modifier = modifier
            .padding(contentPadding)
    ) {
        name?.let {
            Text(
                text = name,
                style = PlaygroundTheme.typography.labelSmall,
                modifier = Modifier
                    .border(1.dp, PlaygroundTheme.colorScheme.outline)
                    .padding(PlaygroundTheme.spacing.xs)
            )
        }
        for (control in controls) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (indent)
                            Modifier.padding(start = PlaygroundTheme.spacing.medium)
                        else Modifier
                    )
            ) {
                when (control) {
                    is Control.Slider -> SliderControl(control = control)
                    is Control.Dropdown<*> -> DropdownControlRow(control = control)
                    is Control.Toggle -> ToggleControlRow(control = control)
                    is Control.TextField -> TextFieldControl(control = control)
                    is Control.ControlColumn -> {
                        val controls by rememberUpdatedState(control.controls())
                        Controls(
                            controls = controls,
                            name = control.name,
                            indent = control.indent,
                            contentPadding = PaddingValues(vertical = PlaygroundTheme.spacing.small),
                        )
                    }
                    is Control.ControlRow -> {
                        val controls by rememberUpdatedState(control.controls())
                        ControlsRow(controls = controls)
                    }
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
                is Control.ControlColumn -> {
                    val controls by rememberUpdatedState(control.controls())
                    Controls(
                        controls = controls,
                        name = control.name,
                        indent = control.indent,
                        contentPadding = PaddingValues(horizontal = PlaygroundTheme.spacing.small),
                    )
                }
                is Control.ControlRow -> {
                    val controls by rememberUpdatedState(control.controls())
                    Controls(controls = controls)
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
                    value = { 0.5f },
                    onValueChange = {},
                ),
                Control.Slider(
                    name = "Amount 2",
                    value = { 0.5f },
                    onValueChange = {},
                )
            ),
            name = "Sliders",
        )
    }
}
