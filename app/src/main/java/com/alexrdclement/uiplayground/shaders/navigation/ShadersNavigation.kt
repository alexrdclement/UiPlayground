package com.alexrdclement.uiplayground.shaders.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alexrdclement.uiplayground.shaders.ShaderScreen

const val shadersRoute = "shaders"

fun NavController.navigateToShaders() {
    this.navigate(route = shadersRoute)
}

fun NavGraphBuilder.shadersScreen() {
    composable(shadersRoute) {
        ShaderScreen()
    }
}
