package com.alexrdclement.uiplayground.app.demo.control

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.components.Checkbox
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun ToggleControl(
    control: Control.Toggle,
    modifier: Modifier = Modifier,
    includeTitle: Boolean = true,
) {
    Column(modifier = modifier) {
        if (includeTitle) {
            Text(control.name)
            Spacer(modifier = Modifier.height(PlaygroundTheme.spacing.small))
        }

        Checkbox(control.value, onCheckedChange = control.onValueChange)
    }
}
