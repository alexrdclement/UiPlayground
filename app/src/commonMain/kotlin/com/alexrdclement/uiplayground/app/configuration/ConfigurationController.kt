package com.alexrdclement.uiplayground.app.configuration

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.theme.Typography

interface ConfigurationState {
    val colorMode: ColorMode
    val typography: Typography
}

interface ConfigurationController : ConfigurationState {
    fun setColorMode(colorMode: ColorMode): Boolean
    fun setTypography(typography: Typography): Boolean
}

@Composable
expect fun rememberConfigurationController(): ConfigurationController
