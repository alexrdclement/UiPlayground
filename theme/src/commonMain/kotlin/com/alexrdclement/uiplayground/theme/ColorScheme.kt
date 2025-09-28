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
