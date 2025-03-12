package com.alexrdclement.uiplayground.app.demo.experiments.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.alexrdclement.uiplayground.app.demo.experiments.Experiment
import com.alexrdclement.uiplayground.app.demo.experiments.ExperimentScreen
import kotlinx.serialization.Serializable

@Serializable
data class ExperimentRoute(
    // Serializable enums not supported in multiplatform navigation as of 2.8.0-alpha10
    val experimentOrdinal: Int,
)

fun NavGraphBuilder.experimentScreen(
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
) {
    composable<ExperimentRoute> { backStackEntry ->
        val experimentRoute: ExperimentRoute = backStackEntry.toRoute()
        val experiment = Experiment.entries[experimentRoute.experimentOrdinal]
        ExperimentScreen(
            experiment = experiment,
            onNavigateBack = onNavigateBack,
            onConfigureClick = onConfigureClick,
        )
    }
}

fun NavController.navigateToExperiment(experiment: Experiment) {
    this.navigate(ExperimentRoute(experiment.ordinal)) {
        launchSingleTop = true
    }
}
