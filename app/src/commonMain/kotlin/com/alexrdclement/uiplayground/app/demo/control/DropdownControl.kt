package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.components.Button
import com.alexrdclement.uiplayground.components.DropdownMenu
import com.alexrdclement.uiplayground.components.DropdownMenuItem
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.components.VerticalDivider
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun <T> DropdownControl(
    control: Control.Dropdown<T>,
    modifier: Modifier = Modifier,
    includeTitle: Boolean = true,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    val selectedValue by remember(control.values, control.selectedIndex) {
        derivedStateOf { control.values[control.selectedIndex] }
    }

    Column(modifier = modifier) {
        if (includeTitle) {
            Text(control.name)
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
    includeTitle: Boolean = true,
) {
    var isMenuExpanded by remember { mutableStateOf(false) }
    val selectedValue by remember(control.values, control.selectedIndex) {
        derivedStateOf { control.values[control.selectedIndex] }
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (includeTitle) {
            Text(control.name)
            Spacer(modifier = Modifier.width(PlaygroundTheme.spacing.medium))
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
private fun <T> DropdownMenuControlButton(
    selectedValue: Control.Dropdown.DropdownItem<T>,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
    ) {
        Text(text = selectedValue.name)
    }
}

@Composable
private fun <T> DropdownControlMenu(
    control: Control.Dropdown<T>,
    isMenuExpanded: Boolean,
    onMenuDismissRequest: () -> Unit,
) {
    DropdownMenu(
        expanded = isMenuExpanded,
        onDismissRequest = onMenuDismissRequest,
    ) {
        control.values.forEachIndexed { index, value ->
            DropdownMenuItem(
                text = { Text(text = value.name) },
                onClick = {
                    onMenuDismissRequest()
                    control.onValueChange(index)
                }
            )
        }
    }
}