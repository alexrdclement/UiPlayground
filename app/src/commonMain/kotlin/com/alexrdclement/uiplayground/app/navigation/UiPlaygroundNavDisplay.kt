package com.alexrdclement.uiplayground.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.alexrdclement.uiplayground.app.catalog.navigation.MainCatalogRoute
import com.alexrdclement.uiplayground.app.catalog.navigation.MainCatalogScreen
import com.alexrdclement.uiplayground.app.configuration.ConfigurationController
import com.alexrdclement.uiplayground.app.configuration.navigation.ConfigurationContent
import com.alexrdclement.uiplayground.app.configuration.navigation.ConfigurationRoute
import com.alexrdclement.uiplayground.app.demo.experiments.navigation.ExperimentCatalogRoute
import com.alexrdclement.uiplayground.app.demo.experiments.navigation.ExperimentRoute
import com.alexrdclement.uiplayground.app.demo.experiments.navigation.ExperimentScreen
import com.alexrdclement.uiplayground.app.demo.experiments.navigation.ExperimentsCatalogScreen

@Composable
fun UiPlaygroundNavDisplay(
    configurationController: ConfigurationController,
    navState: NavState = rememberNavState(
        startRoute = MainCatalogRoute
    ),
    navController: NavController = rememberNavController(navState)
) {
    val currentRoute = navState.backStack.lastOrNull() ?: return
    val previousRoute = navState.backStack.getOrNull(navState.backStack.size - 2) ?: currentRoute

    if (currentRoute.isDialog) {
        Box {
            Content(
                route = previousRoute,
                configurationController = configurationController,
                navState = navState,
                navController = navController,
            )

            Dialog(onDismissRequest = navController::goBack) {
                Content(
                    route = currentRoute,
                    configurationController = configurationController,
                    navState = navState,
                    navController = navController,
                )
            }
        }
    } else {
        Content(
            route = currentRoute,
            configurationController = configurationController,
            navState = navState,
            navController = navController,
        )
    }
}

@Composable
private fun Content(
    route: UiPlaygroundNavKey,
    configurationController: ConfigurationController,
    navState: NavState = rememberNavState(
        startRoute = MainCatalogRoute
    ),
    navController: NavController = rememberNavController(navState)
) {
    when (route) {
        is MainCatalogRoute -> MainCatalogScreen(
            navController = navController,
        )

        is ExperimentCatalogRoute -> ExperimentsCatalogScreen(
            navController = navController,
        )

        is ExperimentRoute -> ExperimentScreen(
            route = route,
            navController = navController,
        )

        is ConfigurationRoute -> ConfigurationContent(
            configurationController = configurationController,
        )
    }
}
