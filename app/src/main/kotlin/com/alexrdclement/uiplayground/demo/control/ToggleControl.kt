package com.alexrdclement.uiplayground.demo.control

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.ui.preview.UiPlaygroundPreview

@Composable
fun ToggleControl(
    control: Control.Toggle,
    modifier: Modifier = Modifier,
    includeTitle: Boolean = true,
) {
    Column(modifier = modifier) {
        if (includeTitle) {
            Text(control.name)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Switch(control.value, onCheckedChange = control.onValueChange)
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        var enabled by remember { mutableStateOf(false) }
        val control by remember {
            mutableStateOf(
                Control.Toggle(
                    name = "Color",
                    value = false,
                    onValueChange = { enabled = it }
                )
            )
        }
        ToggleControl(control = control)
    }
}
