package com.alexrdclement.uiplayground.app.theme.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.alexrdclement.uiplayground.app.demo.popBackStackIfResumed
import com.alexrdclement.uiplayground.app.theme.ThemeScreen
import com.alexrdclement.uiplayground.theme.control.ThemeControl
import kotlinx.serialization.Serializable

@Serializable
object ThemeGraphRoute

@Serializable
object ThemeRoute

fun NavGraphBuilder.themeGraph(
    navController: NavController,
    themeControl: ThemeControl,
) {
    navigation<ThemeGraphRoute>(
        startDestination = ThemeRoute,
    ) {
        themeScreen(
            themeControl = themeControl,
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
    themeControl: ThemeControl,
    onNavigateBack: () -> Unit,
) {
    composable<ThemeRoute> {
        ThemeScreen(
            themeControl = themeControl,
            onNavigateBack = onNavigateBack,
        )
    }
}
