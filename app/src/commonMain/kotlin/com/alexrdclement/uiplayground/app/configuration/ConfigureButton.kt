package com.alexrdclement.uiplayground.app.configuration

import androidx.compose.runtime.Composable
import com.alexrdclement.palette.components.core.Button
import com.alexrdclement.palette.components.core.Text
import com.alexrdclement.palette.theme.PaletteTheme
import com.alexrdclement.palette.theme.styles.ButtonStyleToken

@Composable
fun ConfigureButton(
    onClick: () -> Unit = {},
) {
    Button(
        style = ButtonStyleToken.Tertiary,
        onClick = onClick,
    ) {
        Text("Configure", style = PaletteTheme.styles.text.labelSmall)
    }
}
