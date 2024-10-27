package com.alexrdclement.uiplayground.app.demo.experiments.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.demo.experiments.Experiment
import kotlinx.serialization.Serializable

@Serializable
object ExperimentsGraphRoute

@Serializable
object ExperimentCatalogRoute

fun NavGraphBuilder.experimentsGraph(
    navController: NavController,
) {
    navigation<ExperimentsGraphRoute>(
        startDestination = ExperimentCatalogRoute,
    ) {
        experimentCatalogScreen(
            onItemClick = navController::navigateToExperiment,
            onNavigateBack = navController::popBackStack,
        )
        experimentScreen(
            onNavigateBack = navController::popBackStack,
        )
    }
}

fun NavController.navigateToExperiments() {
    this.navigate(ExperimentsGraphRoute) {
        launchSingleTop = true
    }
}

private fun NavGraphBuilder.experimentCatalogScreen(
    onItemClick: (Experiment) -> Unit,
    onNavigateBack: () -> Unit,
) {
    composable<ExperimentCatalogRoute> {
        CatalogScreen(
            items = Experiment.entries.toList(),
            onItemClick = onItemClick,
            title = "Experiments",
            onNavigateBack = onNavigateBack,
        )
    }
}
