package com.alexrdclement.uiplayground.app.demo.shaders.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alexrdclement.uiplayground.app.demo.shaders.ShaderScreen

private const val shadersRoute = "shaders"

fun NavController.navigateToShaders() {
    this.navigate(route = shadersRoute) {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.shadersScreen() {
    composable(shadersRoute) {
        ShaderScreen()
    }
}
