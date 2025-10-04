package com.alexrdclement.uiplayground.app.theme.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.demo.popBackStackIfResumed
import com.alexrdclement.uiplayground.app.theme.ThemeItem
import com.alexrdclement.uiplayground.app.theme.color.navigation.colorScreen
import com.alexrdclement.uiplayground.app.theme.color.navigation.navigateToColor
import com.alexrdclement.uiplayground.app.theme.interaction.navigation.indicationScreen
import com.alexrdclement.uiplayground.app.theme.interaction.navigation.interactionGraph
import com.alexrdclement.uiplayground.app.theme.interaction.navigation.navigateToInteraction
import com.alexrdclement.uiplayground.app.theme.shape.navigation.navigateToShape
import com.alexrdclement.uiplayground.app.theme.shape.navigation.shapeScreen
import com.alexrdclement.uiplayground.app.theme.typography.navigation.navigateToTypography
import com.alexrdclement.uiplayground.app.theme.typography.navigation.typographyScreen
import com.alexrdclement.uiplayground.theme.control.ThemeController
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
object ThemeGraphRoute

@Serializable
@SerialName("theme")
object ThemeRoute

fun NavGraphBuilder.themeGraph(
    navController: NavController,
    themeController: ThemeController,
) {
    navigation<ThemeGraphRoute>(
        startDestination = ThemeRoute,
    ) {
        themeScreen(
            onItemClick = {
                when (it) {
                    ThemeItem.Color -> navController.navigateToColor()
                    ThemeItem.Interaction -> navController.navigateToInteraction()
                    ThemeItem.Shape -> navController.navigateToShape()
                    ThemeItem.Typography -> navController.navigateToTypography()
                }
            },
            onNavigateBack = navController::popBackStackIfResumed,
        )
        typographyScreen(
            themeController = themeController,
            onNavigateBack = navController::popBackStackIfResumed,
        )
        colorScreen(
            themeController = themeController,
            onNavigateBack = navController::popBackStackIfResumed,
        )
        shapeScreen(
            themeController = themeController,
            onNavigateBack = navController::popBackStackIfResumed,
        )
        interactionGraph(
            navController = navController,
            themeController = themeController,
            onNavigateBack = navController::popBackStackIfResumed,
        )
    }
}

fun NavController.navigateToTheme() {
    this.navigate(ThemeRoute) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.themeScreen(
    onItemClick: (ThemeItem) -> Unit,
    onNavigateBack: () -> Unit,
) {
    composable<ThemeRoute> {
        CatalogScreen(
            title = "Theme",
            items = ThemeItem.entries.toList(),
            onItemClick = onItemClick,
            onNavigateBack = onNavigateBack,
        )
    }
}
