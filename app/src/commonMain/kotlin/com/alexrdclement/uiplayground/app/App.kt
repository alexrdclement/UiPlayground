package com.alexrdclement.uiplayground.app

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.app.configuration.rememberConfigurationController
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNavHost
import com.alexrdclement.uiplayground.components.core.Surface
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.control.rememberThemeControl

@Composable
fun App() {
    val configurationController = rememberConfigurationController()
    val themeControl = rememberThemeControl()

    PlaygroundTheme(
        typography = themeControl.typography,
        indication = themeControl.indication,
    ) {
        Surface {
            UiPlaygroundNavHost(
                configurationController = configurationController,
                themeControl = themeControl,
            )
        }
    }
}
