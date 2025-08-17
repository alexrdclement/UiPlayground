package com.alexrdclement.uiplayground.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

val LocalPlaygroundColorScheme = staticCompositionLocalOf {
    ColorScheme(
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        secondary = Color.Unspecified,
        onSecondary = Color.Unspecified,
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        surface = Color.Unspecified,
        onSurface = Color.Unspecified,
        outline = Color.Unspecified,
    )
}

val LocalPlaygroundTypography = staticCompositionLocalOf {
    Typography(
        headline = TextStyle.Default,
        display = TextStyle.Default,
        titleLarge = TextStyle.Default,
        titleMedium = TextStyle.Default,
        titleSmall = TextStyle.Default,
        labelLarge = TextStyle.Default,
        labelMedium = TextStyle.Default,
        labelSmall = TextStyle.Default,
        bodyLarge = TextStyle.Default,
        bodyMedium = TextStyle.Default,
        bodySmall = TextStyle.Default,
    )
}

val LocalPlaygroundSpacing = staticCompositionLocalOf {
    Spacing(
        xs = Dp.Unspecified,
        small = Dp.Unspecified,
        medium = Dp.Unspecified,
        large = Dp.Unspecified,
    )
}

@Composable
fun PlaygroundTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: Typography = PlaygroundTypography,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> PlaygroundDarkColorScheme
        else -> PlaygroundLightColorScheme
    }

    CompositionLocalProvider(
        LocalPlaygroundColorScheme provides colorScheme,
        LocalPlaygroundTypography provides typography,
        LocalPlaygroundSpacing provides PlaygroundSpacing,
        content = content,
    )

}

object PlaygroundTheme {
    val colorScheme: ColorScheme
        @Composable
        get() = LocalPlaygroundColorScheme.current

    val typography: Typography
        @Composable
        get() = LocalPlaygroundTypography.current

    val spacing: Spacing
        @Composable
        get() = LocalPlaygroundSpacing.current
}
