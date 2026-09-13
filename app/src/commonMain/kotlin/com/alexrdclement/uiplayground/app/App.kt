package com.alexrdclement.uiplayground.app

import androidx.compose.runtime.Composable
import com.embarrasdf.palette.theme.components.core.Surface
import com.embarrasdf.palette.navigation.NavController
import com.embarrasdf.palette.theme.PaletteTheme
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
