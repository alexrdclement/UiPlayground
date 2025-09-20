package com.alexrdclement.uiplayground.app

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.app.configuration.rememberConfigurationController
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNavHost
import com.alexrdclement.uiplayground.components.core.Surface
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun App() {
    val configurationController = rememberConfigurationController()

    PlaygroundTheme(
        typography = configurationController.typography,
        indication = configurationController.indication,
    ) {
        Surface {
            UiPlaygroundNavHost(
                configurationController = configurationController,
            )
        }
    }
}
