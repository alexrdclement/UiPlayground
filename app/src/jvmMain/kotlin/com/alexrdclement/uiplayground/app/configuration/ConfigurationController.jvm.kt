package com.alexrdclement.uiplayground.app.configuration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.alexrdclement.uiplayground.theme.PlaygroundTypography
import com.alexrdclement.uiplayground.theme.Typography

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

    private var _typography by mutableStateOf(PlaygroundTypography)
    override val typography: Typography
        get() = _typography

    override fun setTypography(typography: Typography): Boolean {
        _typography = typography
        return true
    }
}
