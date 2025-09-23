package com.alexrdclement.uiplayground.app.theme.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.alexrdclement.uiplayground.app.theme.ThemeScreen
import com.alexrdclement.uiplayground.theme.control.ThemeControl
import kotlinx.serialization.Serializable

@Serializable
object ThemeGraphRoute

@Serializable
object ThemeRoute

fun NavGraphBuilder.themeGraph(
    themeControl: ThemeControl,
) {
    navigation<ThemeGraphRoute>(
        startDestination = ThemeRoute,
    ) {
        themeScreen(
            themeControl = themeControl,
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
) {
    composable<ThemeRoute> {
        ThemeScreen(
            themeControl = themeControl,
        )
    }
}
