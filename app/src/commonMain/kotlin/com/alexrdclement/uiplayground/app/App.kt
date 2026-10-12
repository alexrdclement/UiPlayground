package com.alexrdclement.uiplayground.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.configuration.ConfigurationController
import com.alexrdclement.uiplayground.app.configuration.rememberConfigurationController
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNav
import com.alexrdclement.uiplayground.app.navigation.rememberUiPlaygroundNavController
import com.embarrasdf.palette.navigation.NavController
import com.embarrasdf.palette.theme.PaletteTheme
import com.embarrasdf.palette.theme.components.core.Surface

@Composable
fun App(
    modifier: Modifier = Modifier,
    navController: NavController = rememberUiPlaygroundNavController(),
    configurationController: ConfigurationController = rememberConfigurationController(),
) {
    PaletteTheme {
        Surface(modifier = modifier) {
            UiPlaygroundNav(
                navController = navController,
                configurationController = configurationController,
            )
        }
    }
}
