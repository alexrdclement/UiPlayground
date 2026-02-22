package com.alexrdclement.uiplayground.app

import androidx.compose.runtime.Composable
import com.alexrdclement.palette.components.core.Surface
import com.alexrdclement.palette.navigation.NavController
import com.alexrdclement.palette.theme.PaletteTheme
import com.alexrdclement.uiplayground.app.configuration.ConfigurationController
import com.alexrdclement.uiplayground.app.configuration.rememberConfigurationController
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNav
import com.alexrdclement.uiplayground.app.navigation.rememberUiPlaygroundNavController

@Composable
fun App(
    navController: NavController = rememberUiPlaygroundNavController(),
    configurationController: ConfigurationController = rememberConfigurationController(),
) {
    PaletteTheme {
        Surface {
            UiPlaygroundNav(
                navController = navController,
                configurationController = configurationController,
            )
        }
    }
}
