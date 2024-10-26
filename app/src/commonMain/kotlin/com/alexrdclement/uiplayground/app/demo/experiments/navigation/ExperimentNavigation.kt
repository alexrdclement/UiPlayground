package com.alexrdclement.uiplayground.app.demo.experiments.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.alexrdclement.uiplayground.app.demo.experiments.Experiment
import com.alexrdclement.uiplayground.app.demo.experiments.ExperimentScreen
import kotlinx.serialization.Serializable

@Serializable
data class ExperimentRoute(val experiment: Experiment)

fun NavGraphBuilder.experimentScreen() {
    composable<ExperimentRoute> { backStackEntry ->
        val experimentRoute: ExperimentRoute = backStackEntry.toRoute()
        ExperimentScreen(
            experiment = experimentRoute.experiment,
        )
    }
}

fun NavController.navigateToExperiment(experiment: Experiment) {
    this.navigate(ExperimentRoute(experiment)) {
        launchSingleTop = true
    }
}
