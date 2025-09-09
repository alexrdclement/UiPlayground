package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import com.alexrdclement.uiplayground.components.Button
import com.alexrdclement.uiplayground.components.DropdownMenu
import com.alexrdclement.uiplayground.components.DropdownMenuItem
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun <T> DropdownControl(
    control: Control.Dropdown<T>,
    modifier: Modifier = Modifier,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    val values by rememberUpdatedState(control.values())
    val selectedIndex by rememberUpdatedState(control.selectedIndex())
    val selectedValue by remember(values, selectedIndex) {
        derivedStateOf { values[selectedIndex] }
    }

    Column(modifier = modifier) {
        if (control.includeLabel) {
            Text(control.name, style = PlaygroundTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(PlaygroundTheme.spacing.small))
        }

        DropdownMenuControlButton(
            selectedValue = selectedValue,
            onClick = { isMenuExpanded = true },
        )

        DropdownControlMenu(
            control = control,
            isMenuExpanded = isMenuExpanded,
            onMenuDismissRequest = { isMenuExpanded = false },
        )
    }
}

@Composable
fun <T> DropdownControlRow(
    control: Control.Dropdown<T>,
    modifier: Modifier = Modifier,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    val values by rememberUpdatedState(control.values())
    val selectedIndex by rememberUpdatedState(control.selectedIndex())
    val selectedValue by remember(values, selectedIndex) {
        derivedStateOf { values[selectedIndex] }
    }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (control.includeLabel) {
            Text(
                text = control.name,
                style = PlaygroundTheme.typography.labelLarge,
                softWrap = true,
                modifier = Modifier.weight(1f, fill = false)
            )
            Spacer(modifier = Modifier.width(PlaygroundTheme.spacing.medium))
        }

        DropdownMenuControlButton(
            selectedValue = selectedValue,
            onClick = { isMenuExpanded = true },
            modifier = Modifier.weight(1f, fill = false)
        )

        DropdownControlMenu(
            control = control,
            isMenuExpanded = isMenuExpanded,
            onMenuDismissRequest = { isMenuExpanded = false },
        )
    }
}

@Composable
private fun <T> DropdownMenuControlButton(
    selectedValue: Control.Dropdown.DropdownItem<T>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(text = selectedValue.name, style = PlaygroundTheme.typography.labelLarge)
    }
}

@Composable
private fun <T> DropdownControlMenu(
    control: Control.Dropdown<T>,
    isMenuExpanded: Boolean,
    onMenuDismissRequest: () -> Unit,
) {
    val values by rememberUpdatedState(control.values())
    DropdownMenu(
        expanded = isMenuExpanded,
        onDismissRequest = onMenuDismissRequest,
    ) {
        values.forEachIndexed { index, value ->
            DropdownMenuItem(
                text = { Text(text = value.name, style = PlaygroundTheme.typography.labelLarge) },
                onClick = {
                    onMenuDismissRequest()
                    control.onValueChange(index)
                }
            )
        }
    }
}

@Preview
@Composable
private fun DropdownControlPreview() {
    UiPlaygroundPreview {
        var selectedIndex by remember { mutableStateOf(0) }
        val control by remember {
            mutableStateOf(
                Control.Dropdown(
                    name = "Edge treatment",
                    values = {
                        listOf(
                            BlurredEdgeTreatment.Rectangle,
                            BlurredEdgeTreatment.Unbounded
                        ).map {
                            Control.Dropdown.DropdownItem(
                                name = it.toString(),
                                value = it
                            )
                        }.toPersistentList()
                    },
                    selectedIndex = { selectedIndex },
                    onValueChange = { selectedIndex = it }
                )
            )
        }
        DropdownControl(control = control)
    }
}

@Preview
@Composable
private fun DropdownControlRowPreview() {
    UiPlaygroundPreview {
        var selectedIndex by remember { mutableStateOf(0) }
        val control by remember {
            mutableStateOf(
                Control.Dropdown(
                    name = "Edge treatment",
                    values = {
                        listOf(
                            BlurredEdgeTreatment.Rectangle,
                            BlurredEdgeTreatment.Unbounded
                        ).map {
                            Control.Dropdown.DropdownItem(
                                name = it.toString(),
                                value = it
                            )
                        }.toPersistentList()
                    },
                    selectedIndex = { selectedIndex },
                    onValueChange = { selectedIndex = it }
                )
            )
        }
        DropdownControlRow(control = control)
    }
}
