package com.alexrdclement.uiplayground.app.configuration.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.catalog.MainCatalogItem
import com.alexrdclement.uiplayground.app.configuration.ConfigurationScreen
import com.alexrdclement.uiplayground.app.demo.components.navigation.ComponentsGraphRoute
import com.alexrdclement.uiplayground.app.demo.popBackStackIfResumed
import com.alexrdclement.uiplayground.components.Button
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.serialization.Serializable

@Serializable
object ConfigurationGraphRoute

@Serializable
object ConfigurationRoute

fun NavGraphBuilder.configurationGraph(
    navController: NavController,
) {
    navigation<ConfigurationGraphRoute>(
        startDestination = ConfigurationRoute,
    ) {
        configurationScreen(
            onNavigateBack = navController::popBackStackIfResumed,
        )
    }
}

fun NavController.navigateToConfiguration() {
    this.navigate(ConfigurationRoute) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.configurationScreen(
    onNavigateBack: () -> Unit,
) {
    composable<ConfigurationRoute> {
        ConfigurationScreen(
            onNavigateBack = onNavigateBack,
        )
    }
}
