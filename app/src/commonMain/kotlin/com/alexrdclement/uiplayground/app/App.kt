package com.alexrdclement.uiplayground.app

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.app.configuration.rememberConfigurationController
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNavHost
import com.alexrdclement.uiplayground.components.core.Surface
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.control.rememberThemeController

@Composable
fun App() {
    val configurationController = rememberConfigurationController()
    val themeController = rememberThemeController()

    PlaygroundTheme(
        typography = themeController.typography,
        indication = themeController.indication,
    ) {
        Surface {
            UiPlaygroundNavHost(
                configurationController = configurationController,
                themeController = themeController,
            )
        }
    }
}
