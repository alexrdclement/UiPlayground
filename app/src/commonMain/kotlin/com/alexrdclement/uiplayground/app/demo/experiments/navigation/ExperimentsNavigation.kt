package com.alexrdclement.uiplayground.app.demo.experiments.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alexrdclement.uiplayground.app.demo.experiments.Experiment

private const val experimentsGraphRoute = "experimentsGraph"
private const val experimentCatalogRoute = "experimentCatalog"

fun NavGraphBuilder.experimentsGraph(
    navController: NavController,
) {
    navigation(
        route = experimentsGraphRoute,
        startDestination = experimentCatalogRoute,
    ) {
        experimentCatalogScreen(
            onItemClick = navController::navigateToExperiment,
        )
        experimentScreen()
    }
}

fun NavController.navigateToExperiments() {
    this.navigate(experimentsGraphRoute) {
        launchSingleTop = true
    }
}

private fun NavGraphBuilder.experimentCatalogScreen(
    onItemClick: (Experiment) -> Unit,
) {
    composable(experimentCatalogRoute) {
        com.alexrdclement.uiplayground.app.catalog.CatalogScreen(
            items = Experiment.entries.toList(),
            onItemClick = onItemClick,
        )
    }
}
