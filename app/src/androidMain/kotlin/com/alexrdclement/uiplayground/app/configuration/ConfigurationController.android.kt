package com.alexrdclement.uiplayground.app.configuration

import android.content.pm.ActivityInfo
import android.os.Build
import android.view.Window
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberConfigurationController(): ConfigurationController {
    val activity = LocalActivity.current
    val window = activity?.window
    return remember(window) {
        ConfigurationControllerImpl(
            window = window,
        )
    }
}

private class ConfigurationControllerImpl(
    private val window: Window?,
) : ConfigurationController {
    override val colorMode: ColorMode
        get() {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                return ColorMode.DEFAULT
            }
            return when (window?.colorMode) {
                ActivityInfo.COLOR_MODE_DEFAULT -> ColorMode.DEFAULT
                ActivityInfo.COLOR_MODE_HDR -> ColorMode.HDR
                ActivityInfo.COLOR_MODE_WIDE_COLOR_GAMUT -> ColorMode.WIDE_COLOR_GAMUT
                else -> ColorMode.DEFAULT
            }
        }

    override fun setColorMode(colorMode: ColorMode): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return false
        }
        if (window == null) return false

        window.colorMode = when (colorMode) {
            ColorMode.DEFAULT -> ActivityInfo.COLOR_MODE_DEFAULT
            ColorMode.WIDE_COLOR_GAMUT -> ActivityInfo.COLOR_MODE_WIDE_COLOR_GAMUT
            ColorMode.HDR -> ActivityInfo.COLOR_MODE_HDR
        }
        return true
    }
}
