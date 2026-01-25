package com.alexrdclement.uiplayground.app.configuration.navigation

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.app.configuration.ConfigurationController
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNavKey

@Composable
fun ConfigurationNav(
    route: UiPlaygroundNavKey,
    configurationController: ConfigurationController,
) {
    when (route) {
        ConfigurationRoute -> ConfigurationContent(
            configurationController = configurationController,
        )
    }
}
