package com.alexrdclement.uiplayground.app.configuration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberConfigurationController(): ConfigurationController {
    return remember { ConfigurationControllerImpl() }
}

private class ConfigurationControllerImpl : ConfigurationController {
    override val colorMode: ColorMode = ColorMode.DEFAULT
    override fun setColorMode(colorMode: ColorMode): Boolean {
        // TODO
        return false
    }
}
