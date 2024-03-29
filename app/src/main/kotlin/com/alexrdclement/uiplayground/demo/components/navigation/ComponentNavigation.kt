package com.alexrdclement.uiplayground.demo.components.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alexrdclement.uiplayground.demo.components.Component
import com.alexrdclement.uiplayground.demo.components.ComponentScreen

private const val componentRouteRoot = "component"
private const val componentNameArgKey = "componentName"
private const val componentRouteTemplate = "$componentRouteRoot/{$componentNameArgKey}"

private fun createComponentRoute(component: Component) = "$componentRouteRoot/${component.name}"

fun NavGraphBuilder.componentScreen() {
    composable(
        route = componentRouteTemplate,
        arguments = listOf(
            navArgument(componentNameArgKey) { type = NavType.StringType }
        )
    ) { backStackEntry ->
        val componentName = backStackEntry.arguments?.getString(componentNameArgKey)
        val component = componentName?.let(Component::valueOf) ?: Component.MediaControlSheet
        ComponentScreen(
            component = component,
        )
    }
}

fun NavController.navigateToComponent(component: Component) {
    this.navigate(createComponentRoute(component)) {
        launchSingleTop = true
    }
}
