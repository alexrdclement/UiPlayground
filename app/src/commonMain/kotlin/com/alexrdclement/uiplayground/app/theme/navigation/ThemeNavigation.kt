package com.alexrdclement.uiplayground.app.theme.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.alexrdclement.uiplayground.app.demo.popBackStackIfResumed
import com.alexrdclement.uiplayground.app.theme.ThemeScreen
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
    themeController: ThemeController,
    onNavigateBack: () -> Unit,
) {
    composable<ThemeRoute> {
        ThemeScreen(
            themeController = themeController,
            onNavigateBack = onNavigateBack,
        )
    }
}
