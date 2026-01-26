package com.alexrdclement.uiplayground.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.alexrdclement.uiplayground.app.catalog.navigation.MainCatalogRoute
import com.alexrdclement.uiplayground.app.catalog.navigation.MainNav
import com.alexrdclement.uiplayground.app.configuration.ConfigurationController
import com.alexrdclement.uiplayground.app.configuration.navigation.ConfigurationNav
import com.alexrdclement.uiplayground.app.demo.experiments.navigation.ExperimentsNav

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
    MainNav(
        route = route,
        navController = navController,
    )
    ExperimentsNav(
        route = route,
        navController = navController,
    )
    ConfigurationNav(
        route = route,
        configurationController = configurationController,
    )
}
