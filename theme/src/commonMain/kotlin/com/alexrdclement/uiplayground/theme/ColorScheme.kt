package com.alexrdclement.uiplayground.theme

import androidx.compose.ui.graphics.Color

data class ColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color,
    val outline: Color,
    val disabledContainerAlpha: Float = 0.12f,
    val disabledContentAlpha: Float = 0.35f,
)

val PlaygroundDarkColorScheme = ColorScheme(
    primary = Color.White,
    onPrimary = Color.Black,
    secondary = Color.White,
    onSecondary = Color.Black,
    background = Color.Gray,
    onBackground = Color.White,
    surface = Color.Black,
    onSurface = Color.White,
    outline = Color.White.copy(alpha = 0.5f),
)

val PlaygroundLightColorScheme = ColorScheme(
    primary = Color.Black,
    onPrimary = Color.White,
    secondary = Color.Black,
    onSecondary = Color.White,
    background = Color.Gray,
    onBackground = Color.White,
    surface = Color.White,
    onSurface = Color.Black,
    outline = Color.Black.copy(alpha = 0.5f),
)

fun ColorScheme.copy(
    token: ColorToken,
    color: Color,
) = this.copy(
    primary = if (token == ColorToken.Primary) color else this.primary,
    onPrimary = if (token == ColorToken.OnPrimary) color else this.onPrimary,
    secondary = if (token == ColorToken.Secondary) color else this.secondary,
    onSecondary = if (token == ColorToken.OnSecondary) color else this.onSecondary,
    background = if (token == ColorToken.Background) color else this.background,
    onBackground = if (token == ColorToken.OnBackground) color else this.onBackground,
    surface = if (token == ColorToken.Surface) color else this.surface,
    onSurface = if (token == ColorToken.OnSurface) color else this.onSurface,
    outline = if (token == ColorToken.Outline) color else this.outline,
)
