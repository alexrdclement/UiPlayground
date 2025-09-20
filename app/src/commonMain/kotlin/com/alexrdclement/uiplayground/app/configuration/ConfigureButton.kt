package com.alexrdclement.uiplayground.app.configuration

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.components.core.Button
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun ConfigureButton(
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
    ) {
        Text("CONFIGURE", style = PlaygroundTheme.typography.labelSmall)
    }
}
