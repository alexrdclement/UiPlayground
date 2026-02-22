package com.alexrdclement.uiplayground.app.demo.experiments.navigation

import com.alexrdclement.palette.navigation.EnumNavKey
import com.alexrdclement.palette.navigation.NavGraphRoute
import com.alexrdclement.palette.navigation.NavKey
import com.alexrdclement.palette.navigation.PathSegment
import com.alexrdclement.palette.navigation.toEnumEntry
import com.alexrdclement.palette.navigation.toPathSegment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed interface ExperimentsRoute : NavKey

@Serializable
@SerialName("experiments")
data object ExperimentsGraph : ExperimentsRoute, NavGraphRoute {
    override val pathSegment = "experiments".toPathSegment()
}

@Serializable
@SerialName("experiment-catalog")
data object ExperimentCatalogRoute : ExperimentsRoute {
    override val pathSegment = "catalog".toPathSegment()
}

@Serializable
@SerialName("experiment")
data class ExperimentRoute(
    override val ordinal: Int,
) : ExperimentsRoute, EnumNavKey<Experiment> {
    override val entries = Experiment.entries
    val experiment: Experiment = Experiment.entries[ordinal]

    constructor(experiment: Experiment) : this(ordinal = experiment.ordinal)
    constructor(pathSegment: PathSegment) : this(experiment = pathSegment.toEnumEntry(Experiment.entries))
}
