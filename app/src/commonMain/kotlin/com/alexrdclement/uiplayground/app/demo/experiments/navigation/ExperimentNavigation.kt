package com.alexrdclement.uiplayground.app.demo.experiments.navigation

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.app.configuration.navigation.ConfigurationRoute
import com.alexrdclement.uiplayground.app.demo.experiments.Experiment
import com.alexrdclement.uiplayground.app.demo.experiments.ExperimentScreen
import com.alexrdclement.uiplayground.app.navigation.NavController
import com.alexrdclement.uiplayground.app.navigation.PathSegment
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNavKey
import com.alexrdclement.uiplayground.app.navigation.toPathSegment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("experiment")
data class ExperimentRoute(
    val ordinal: Int,
) : UiPlaygroundNavKey {

    constructor(experiment: Experiment) : this(experiment.ordinal)

    val experiment: Experiment
        get() = Experiment.entries[ordinal]

    override val pathSegment: PathSegment
        get() = experiment.name.toPathSegment()
}

@Composable
fun ExperimentScreen(
    route: ExperimentRoute,
    navController: NavController,
) {
    ExperimentScreen(
        experiment = route.experiment,
        onNavigateBack = navController::goBack,
        onConfigureClick = {
            navController.navigate(ConfigurationRoute)
        },
    )
}
