package com.alexrdclement.uiplayground.app.theme.spacing.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alexrdclement.uiplayground.app.theme.spacing.SpacingScreen
import com.alexrdclement.uiplayground.theme.control.ThemeController
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("spacing")
object SpacingRoute

fun NavController.navigateToSpacing() {
    this.navigate(SpacingRoute) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.spacingScreen(
    themeController: ThemeController,
    onNavigateBack: () -> Unit,
) {
    composable<SpacingRoute> {
        SpacingScreen(
            themeController = themeController,
            onNavigateBack = onNavigateBack,
        )
    }
}
