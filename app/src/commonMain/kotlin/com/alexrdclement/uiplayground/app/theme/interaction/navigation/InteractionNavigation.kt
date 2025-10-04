package com.alexrdclement.uiplayground.app.theme.interaction.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.theme.interaction.Interaction
import com.alexrdclement.uiplayground.theme.control.ThemeController
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
object InteractionGraphRoute

@Serializable
@SerialName("interaction")
object InteractionCatalogRoute

fun NavGraphBuilder.interactionGraph(
    navController: NavController,
    themeController: ThemeController,
    onNavigateBack: () -> Unit,
) {
    navigation<InteractionGraphRoute>(
        startDestination = InteractionCatalogRoute,
    ) {
        interactionScreen(
            onItemClick = { interaction ->
                when (interaction) {
                    Interaction.Indication -> navController.navigateToIndication()
                }
            },
            onNavigateBack = onNavigateBack,
        )
        indicationScreen(
            themeController = themeController,
            onNavigateBack = onNavigateBack,
        )
    }
}

fun NavController.navigateToInteraction() {
    this.navigate(InteractionGraphRoute) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.interactionScreen(
    onItemClick: (Interaction) -> Unit,
    onNavigateBack: () -> Unit,
) {
    composable<InteractionCatalogRoute> {
        CatalogScreen(
            items = Interaction.entries.toList(),
            onItemClick = onItemClick,
            title = "Interaction",
            onNavigateBack = onNavigateBack,
        )
    }
}
