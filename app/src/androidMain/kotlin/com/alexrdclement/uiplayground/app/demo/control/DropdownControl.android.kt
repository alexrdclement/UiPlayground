package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.tooling.preview.Preview
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import kotlinx.collections.immutable.toPersistentList

@Preview
@Composable
private fun DropdownControlPreview() {
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
                    }.toPersistentList(),
                    selectedIndex = selectedIndex,
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
                    values = listOf(
                        BlurredEdgeTreatment.Rectangle,
                        BlurredEdgeTreatment.Unbounded
                    ).map {
                        Control.Dropdown.DropdownItem(
                            name = it.toString(),
                            value = it
                        )
                    }.toPersistentList(),
                    selectedIndex = selectedIndex,
                    onValueChange = { selectedIndex = it }
                )
            )
        }
        DropdownControlRow(control = control)
    }
}
