package com.alexrdclement.uiplayground.demo.control

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.util.UiPlaygroundPreview

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
            Spacer(modifier = Modifier.height(8.dp))
        }

        OutlinedButton(
            onClick = { isMenuExpanded = true }
        ) {
            Text(text = selectedValue.name)
        }

        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
        ) {
            control.values.forEachIndexed { index, value ->
                DropdownMenuItem(
                    text = { Text(text = value.name) },
                    onClick = {
                        isMenuExpanded = false
                        control.onValueChange(index)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        var selectedIndex by remember { mutableStateOf(0) }
        val control by remember {
            mutableStateOf(
                Control.Dropdown(
                    name = "Edge treatment",
                    values = listOf(
                        BlurredEdgeTreatment.Rectangle,
                        BlurredEdgeTreatment.Unbounded
                    ).map {
                          Control.Dropdown.DropdownItem(
                              name = it.toString(),
                              value = it
                          )
                    },
                    selectedIndex = selectedIndex,
                    onValueChange = { selectedIndex = it }
                )
            )
        }
        DropdownControl(control = control)
    }
}
