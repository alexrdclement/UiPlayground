package com.alexrdclement.uiplayground.app.configuration.navigation

import com.alexrdclement.palette.navigation.NavKey
import com.alexrdclement.palette.navigation.toPathSegment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("configuration")
data object ConfigurationRoute : NavKey {
    override val pathSegment = "configuration".toPathSegment()
}
