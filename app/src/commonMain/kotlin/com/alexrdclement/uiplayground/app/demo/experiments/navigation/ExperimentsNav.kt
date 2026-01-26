package com.alexrdclement.uiplayground.app.demo.experiments.navigation

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.app.navigation.NavController
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNavKey

@Composable
fun ExperimentsNav(
    route: UiPlaygroundNavKey = ExperimentCatalogRoute,
    navController: NavController,
) {
    when (route) {
        is ExperimentCatalogRoute -> ExperimentsCatalogScreen(
            navController = navController,
        )
        is ExperimentRoute -> ExperimentScreen(
            route = route,
            navController = navController,
        )
    }
}
