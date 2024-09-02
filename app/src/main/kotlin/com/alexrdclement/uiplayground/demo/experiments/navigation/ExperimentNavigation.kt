package com.alexrdclement.uiplayground.demo.experiments.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alexrdclement.uiplayground.demo.experiments.Experiment
import com.alexrdclement.uiplayground.demo.experiments.ExperimentScreen

private const val experimentRouteRoot = "experiment"
private const val experimentNameArgKey = "experimentName"
private const val experimentRouteTemplate = "$experimentRouteRoot/{$experimentNameArgKey}"

private fun createExperimentRoute(experiment: Experiment) = "$experimentRouteRoot/${experiment.name}"

fun NavGraphBuilder.experimentScreen() {
    composable(
        route = experimentRouteTemplate,
        arguments = listOf(
            navArgument(experimentNameArgKey) { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val experimentName = backStackEntry.arguments?.getString(experimentNameArgKey)
        val experiment = experimentName?.let(Experiment::valueOf) ?: Experiment.BasicTextField
        ExperimentScreen(
            experiment = experiment,
        )
    }
}

fun NavController.navigateToExperiment(experiment: Experiment) {
    this.navigate(createExperimentRoute(experiment)) {
        launchSingleTop = true
    }
}
