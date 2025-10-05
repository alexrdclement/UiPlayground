package com.alexrdclement.uiplayground.theme

import androidx.compose.runtime.Composable

enum class ColorToken {
    Primary,
    OnPrimary,
    Secondary,
    OnSecondary,
    Background,
    OnBackground,
    Surface,
    OnSurface,
    Outline,
}

@Composable
fun ColorToken.toColor(): androidx.compose.ui.graphics.Color {
    return when (this) {
        ColorToken.Primary -> PlaygroundTheme.colorScheme.primary
        ColorToken.OnPrimary -> PlaygroundTheme.colorScheme.onPrimary
        ColorToken.Secondary -> PlaygroundTheme.colorScheme.secondary
        ColorToken.OnSecondary -> PlaygroundTheme.colorScheme.onSecondary
        ColorToken.Background -> PlaygroundTheme.colorScheme.background
        ColorToken.OnBackground -> PlaygroundTheme.colorScheme.onBackground
        ColorToken.Surface -> PlaygroundTheme.colorScheme.surface
        ColorToken.OnSurface -> PlaygroundTheme.colorScheme.onSurface
        ColorToken.Outline -> PlaygroundTheme.colorScheme.outline
    }
}
