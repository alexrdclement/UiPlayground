package com.alexrdclement.uiplayground.theme

import androidx.compose.foundation.Indication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.alexrdclement.uiplayground.theme.styles.ButtonStyle
import com.alexrdclement.uiplayground.theme.styles.ButtonStyleScheme
import com.alexrdclement.uiplayground.theme.styles.ButtonStyleToken

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
        disabledContainerAlpha = 1f,
        disabledContentAlpha = 1f,
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

val LocalPlaygroundShapes = staticCompositionLocalOf {
    ShapeScheme(
        primary = Shape.Rectangle(),
        secondary = Shape.Rectangle(),
        tertiary = Shape.Rectangle(),
        surface = Shape.Rectangle(),
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

val LocalPlaygroundIndication = staticCompositionLocalOf<Indication> {
    NoOpIndication
}

val LocalPlaygroundStyles = staticCompositionLocalOf {
    val defaultButtonStyle = ButtonStyle(
        token = ButtonStyleToken.Primary,
        shape = ShapeToken.Primary,
        containerColor = ColorToken.Surface,
        contentColor = ColorToken.Primary,
        border = null,
    )
    Styles(
        buttonStyles = ButtonStyleScheme(
            primary = defaultButtonStyle,
            secondary = defaultButtonStyle,
            tertiary = defaultButtonStyle,
        ),
    )
}

@Composable
fun PlaygroundTheme(
    isDarkMode: Boolean = isSystemInDarkTheme(),
    lightColorScheme: ColorScheme = PlaygroundLightColorScheme,
    darkColorScheme: ColorScheme = PlaygroundDarkColorScheme,
    typography: Typography = PlaygroundTypography,
    shapeScheme: ShapeScheme = PlaygroundShapeScheme,
    indication: Indication = PlaygroundIndication,
    spacing: Spacing = PlaygroundSpacing,
    styles: Styles = PlaygroundStyles,
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDarkMode) darkColorScheme else lightColorScheme
    CompositionLocalProvider(
        LocalPlaygroundColorScheme provides colorScheme,
        LocalPlaygroundTypography provides typography,
        LocalPlaygroundShapes provides shapeScheme,
        LocalPlaygroundIndication provides indication,
        LocalPlaygroundSpacing provides spacing,
        LocalPlaygroundStyles provides styles,
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

    val shapeScheme: ShapeScheme
        @Composable
        get() = LocalPlaygroundShapes.current

    val spacing: Spacing
        @Composable
        get() = LocalPlaygroundSpacing.current

    val indication: Indication
        @Composable
        get() = LocalPlaygroundIndication.current

    val styles: Styles
        @Composable
        get() = LocalPlaygroundStyles.current
}
