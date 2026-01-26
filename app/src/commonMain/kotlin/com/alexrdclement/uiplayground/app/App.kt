package com.alexrdclement.uiplayground.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.alexrdclement.palette.components.core.Surface
import com.alexrdclement.palette.theme.PaletteTheme
import com.alexrdclement.uiplayground.app.catalog.navigation.MainCatalogRoute
import com.alexrdclement.uiplayground.app.configuration.ConfigurationController
import com.alexrdclement.uiplayground.app.configuration.rememberConfigurationController
import com.alexrdclement.uiplayground.app.navigation.NavState
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNavDisplay
import com.alexrdclement.uiplayground.app.navigation.rememberNavState

@Composable
fun App(
    navState: NavState = rememberNavState(startRoute = MainCatalogRoute),
    configurationController: ConfigurationController = rememberConfigurationController(),
) {
    PaletteTheme {
        Surface {
            UiPlaygroundNavDisplay(
                navState = navState,
                configurationController = configurationController,
            )
        }
    }
}
