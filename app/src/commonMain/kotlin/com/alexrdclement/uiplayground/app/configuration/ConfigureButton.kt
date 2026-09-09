package com.alexrdclement.uiplayground.app.configuration

import androidx.compose.runtime.Composable
import com.alexrdclement.palette.components.core.Button
import com.alexrdclement.palette.components.core.Text
import com.alexrdclement.palette.theme.PaletteTheme

@Composable
fun ConfigureButton(
    onClick: () -> Unit = {},
) {
    Button(
        style = PaletteTheme.component.core.button.tertiary,
        onClick = onClick,
    ) {
        Text("Configure", style = PaletteTheme.component.core.text.labelSmall)
    }
}
