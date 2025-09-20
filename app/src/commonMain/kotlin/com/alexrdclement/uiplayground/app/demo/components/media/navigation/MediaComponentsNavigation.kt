package com.alexrdclement.uiplayground.app.demo.components.media.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.configuration.ConfigureButton
import com.alexrdclement.uiplayground.app.demo.popBackStackIfResumed
import kotlinx.serialization.Serializable

@Serializable
object MediaComponentsGraphRoute

@Serializable
object MediaComponentCatalogRoute

fun NavGraphBuilder.mediaComponentsGraph(
    navController: NavController,
    onConfigureClick: () -> Unit,
) {
    navigation<MediaComponentsGraphRoute>(
        startDestination = MediaComponentCatalogRoute,
    ) {
        mediaComponentCatalogScreen(
            onItemClick = navController::navigateToComponent,
            onNavigateBack = navController::popBackStackIfResumed,
            onConfigureClick = onConfigureClick,
        )
        mediaComponentScreen(
            onNavigateBack = navController::popBackStackIfResumed,
            onConfigureClick = onConfigureClick,
        )
    }
}

fun NavController.navigateToMediaComponents() {
    this.navigate(MediaComponentsGraphRoute) {
        launchSingleTop = true
    }
}

private fun NavGraphBuilder.mediaComponentCatalogScreen(
    onItemClick: (MediaComponent) -> Unit,
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
) {
    composable<MediaComponentCatalogRoute> {
        CatalogScreen(
            items = MediaComponent.entries.toList(),
            onItemClick = onItemClick,
            title = "Media",
            onNavigateBack = onNavigateBack,
            actions = {
                ConfigureButton(onClick = onConfigureClick)
            },
        )
    }
}
