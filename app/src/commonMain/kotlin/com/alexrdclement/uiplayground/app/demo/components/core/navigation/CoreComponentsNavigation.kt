package com.alexrdclement.uiplayground.app.demo.components.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.configuration.ConfigureButton
import com.alexrdclement.uiplayground.app.demo.popBackStackIfResumed
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
object CoreComponentsGraphRoute

@Serializable
@SerialName("core")
object CoreComponentCatalogRoute

fun NavGraphBuilder.coreComponentsGraph(
    navController: NavController,
    onConfigureClick: () -> Unit,
) {
    navigation<CoreComponentsGraphRoute>(
        startDestination = CoreComponentCatalogRoute,
    ) {
        coreComponentCatalogScreen(
            onItemClick = navController::navigateToComponent,
            onNavigateBack = navController::popBackStackIfResumed,
            onConfigureClick = onConfigureClick,
        )
        coreComponentScreen(
            onNavigateBack = navController::popBackStackIfResumed,
            onConfigureClick = onConfigureClick,
        )
    }
}

fun NavController.navigateToCoreComponents() {
    this.navigate(CoreComponentsGraphRoute) {
        launchSingleTop = true
    }
}

private fun NavGraphBuilder.coreComponentCatalogScreen(
    onItemClick: (CoreComponent) -> Unit,
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
) {
    composable<CoreComponentCatalogRoute> {
        CatalogScreen(
            items = CoreComponent.entries.toList(),
            onItemClick = onItemClick,
            title = "Core",
            onNavigateBack = onNavigateBack,
            actions = {
                ConfigureButton(onClick = onConfigureClick)
            },
        )
    }
}
