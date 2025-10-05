package com.alexrdclement.uiplayground.app.configuration

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.components.core.Button
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.ShapeToken

@Composable
fun ConfigureButton(
    onClick: () -> Unit = {},
) {
    Button(
        shape = ShapeToken.Secondary,
        onClick = onClick,
    ) {
        Text("CONFIGURE", style = PlaygroundTheme.typography.labelSmall)
    }
}
