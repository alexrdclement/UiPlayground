package com.alexrdclement.uiplayground.app.demo.components.datetime.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.alexrdclement.uiplayground.app.demo.components.datetime.DateTimeComponentScreen
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("component")
data class ComponentRoute(
    // Serializable enums not supported in multiplatform navigation as of 2.8.0-alpha10
    val componentOrdinal: Int,
)

fun NavGraphBuilder.dateTimeComponentScreen(
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
) {
    composable<ComponentRoute> { backStackEntry ->
        val componentRoute: ComponentRoute = backStackEntry.toRoute()
        val component = DateTimeComponent.entries[componentRoute.componentOrdinal]
        DateTimeComponentScreen(
            component = component,
            onNavigateBack = onNavigateBack,
            onConfigureClick = onConfigureClick,
        )
    }
}

fun NavController.navigateToComponent(component: DateTimeComponent) {
    this.navigate(ComponentRoute(component.ordinal)) {
        launchSingleTop = true
    }
}
