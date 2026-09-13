package com.alexrdclement.uiplayground.app.main.navigation

import com.embarrasdf.palette.navigation.NavGraphRoute
import com.embarrasdf.palette.navigation.NavKey
import com.embarrasdf.palette.navigation.toPathSegment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface MainRoute : NavKey

@Serializable
@SerialName("main")
data object MainGraph : MainRoute, NavGraphRoute {
    override val pathSegment = "main".toPathSegment()
}

@Serializable
@SerialName("main-catalog")
data object MainCatalogRoute : MainRoute {
    override val pathSegment = "catalog".toPathSegment()
}
