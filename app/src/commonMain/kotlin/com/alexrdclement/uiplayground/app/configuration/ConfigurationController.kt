package com.alexrdclement.uiplayground.app.configuration

import androidx.compose.runtime.Composable

interface ConfigurationController {
    val colorMode: ColorMode
    fun setColorMode(colorMode: ColorMode): Boolean
}

@Composable
expect fun rememberConfigurationController(): ConfigurationController
