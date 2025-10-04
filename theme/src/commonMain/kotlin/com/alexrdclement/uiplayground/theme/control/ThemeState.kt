package com.alexrdclement.uiplayground.theme.control

import androidx.compose.foundation.Indication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.alexrdclement.uiplayground.theme.ColorScheme
import com.alexrdclement.uiplayground.theme.PlaygroundDarkColorScheme
import com.alexrdclement.uiplayground.theme.PlaygroundIndication
import com.alexrdclement.uiplayground.theme.PlaygroundLightColorScheme
import com.alexrdclement.uiplayground.theme.PlaygroundShapeScheme
import com.alexrdclement.uiplayground.theme.PlaygroundTypography
import com.alexrdclement.uiplayground.theme.ShapeScheme
import com.alexrdclement.uiplayground.theme.Typography

interface ThemeState {
    val typography: Typography
    val indication: Indication
    val lightColorScheme: ColorScheme
    val darkColorScheme: ColorScheme
    val isDarkMode: Boolean
    val shapeScheme: ShapeScheme

    val colorScheme: ColorScheme
        get() = if (isDarkMode) darkColorScheme else lightColorScheme
}

internal class ThemeStateImpl(
    isDarkModeInitial: Boolean,
    lightColorSchemeInitial: ColorScheme = PlaygroundLightColorScheme,
    darkColorSchemeInitial: ColorScheme = PlaygroundDarkColorScheme,
    typographyInitial: Typography = PlaygroundTypography,
    shapeSchemeInitial: ShapeScheme = PlaygroundShapeScheme,
    indicationInitial: Indication = PlaygroundIndication,
) : ThemeState {
    override var typography by mutableStateOf(typographyInitial)
    override var shapeScheme by mutableStateOf(shapeSchemeInitial)
    override var indication by mutableStateOf(indicationInitial)
    override var lightColorScheme by mutableStateOf(lightColorSchemeInitial)
    override var darkColorScheme by mutableStateOf(darkColorSchemeInitial)
    override var isDarkMode by mutableStateOf(isDarkModeInitial)
}

@Composable
internal fun rememberThemeState(
    isDarkMode: Boolean = isSystemInDarkTheme(),
): ThemeStateImpl {
    return remember(isDarkMode) {
        ThemeStateImpl(
            isDarkModeInitial = isDarkMode,
        )
    }
}
