package com.alexrdclement.uiplayground.app.configuration.navigation

import com.embarrasdf.palette.navigation.NavKey
import com.embarrasdf.palette.navigation.toPathSegment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("configuration")
data object ConfigurationRoute : NavKey {
    override val pathSegment = "configuration".toPathSegment()
}
