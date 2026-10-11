package com.alexrdclement.uiplayground.app.configuration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.embarrasdf.palette.components.core.Button
import com.embarrasdf.palette.components.core.Text
import com.embarrasdf.palette.theme.PaletteTheme

@Composable
fun ConfigureButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Button(
        style = PaletteTheme.component.core.button.tertiary,
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(text = "Configure", style = PaletteTheme.component.core.text.labelSmall)
    }
}
