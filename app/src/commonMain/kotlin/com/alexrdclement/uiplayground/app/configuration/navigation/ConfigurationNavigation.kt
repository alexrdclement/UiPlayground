package com.alexrdclement.uiplayground.app.configuration.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import com.alexrdclement.uiplayground.app.configuration.ConfigurationDialogContent
import kotlinx.serialization.Serializable

@Serializable
object ConfigurationGraphRoute

@Serializable
object ConfigurationRoute

fun NavGraphBuilder.configurationGraph() {
    navigation<ConfigurationGraphRoute>(
        startDestination = ConfigurationRoute,
    ) {
        configurationDialog()
    }
}

fun NavController.navigateToConfiguration() {
    this.navigate(ConfigurationRoute) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.configurationDialog() {
    dialog<ConfigurationRoute> {
        ConfigurationDialogContent()
    }
}
