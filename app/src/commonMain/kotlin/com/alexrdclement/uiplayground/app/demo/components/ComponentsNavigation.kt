package com.alexrdclement.uiplayground.app.demo.components

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.configuration.ConfigureButton
import com.alexrdclement.uiplayground.app.demo.components.color.navigation.colorComponentsGraph
import com.alexrdclement.uiplayground.app.demo.components.color.navigation.navigateToColorComponents
import com.alexrdclement.uiplayground.app.demo.components.core.navigation.navigateToCoreComponents
import com.alexrdclement.uiplayground.app.demo.components.geometry.navigation.geometryComponentsGraph
import com.alexrdclement.uiplayground.app.demo.components.geometry.navigation.navigateToGeometryComponents
import com.alexrdclement.uiplayground.app.demo.components.media.navigation.mediaComponentsGraph
import com.alexrdclement.uiplayground.app.demo.components.media.navigation.navigateToMediaComponents
import com.alexrdclement.uiplayground.app.demo.components.money.navigation.moneyComponentsGraph
import com.alexrdclement.uiplayground.app.demo.components.money.navigation.navigateToMoneyComponents
import com.alexrdclement.uiplayground.app.demo.popBackStackIfResumed
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
object ComponentsGraphRoute

@Serializable
@SerialName("components")
object ComponentCatalogRoute

fun NavGraphBuilder.componentsGraph(
    navController: NavController,
    onConfigureClick: () -> Unit,
) {
    navigation<ComponentsGraphRoute>(
        startDestination = ComponentCatalogRoute,
    ) {
        componentCatalogScreen(
            onItemClick = { componentGroup ->
                when (componentGroup) {
                    ComponentCategory.Color -> navController.navigateToColorComponents()
                    ComponentCategory.Core -> navController.navigateToCoreComponents()
                    ComponentCategory.Geometry -> navController.navigateToGeometryComponents()
                    ComponentCategory.Media -> navController.navigateToMediaComponents()
                    ComponentCategory.Money -> navController.navigateToMoneyComponents()
                }
            },
            onNavigateBack = navController::popBackStackIfResumed,
            onConfigureClick = onConfigureClick,
        )
        colorComponentsGraph(
            navController = navController,
            onConfigureClick = onConfigureClick,
        )
        colorComponentsGraph(
            navController = navController,
            onConfigureClick = onConfigureClick,
        )
        geometryComponentsGraph(
            navController = navController,
            onConfigureClick = onConfigureClick,
        )
        mediaComponentsGraph(
            navController = navController,
            onConfigureClick = onConfigureClick,
        )
        moneyComponentsGraph(
            navController = navController,
            onConfigureClick = onConfigureClick,
        )
    }
}

fun NavController.navigateToComponents() {
    this.navigate(ComponentsGraphRoute) {
        launchSingleTop = true
    }
}

private fun NavGraphBuilder.componentCatalogScreen(
    onItemClick: (ComponentCategory) -> Unit,
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
) {
    composable<ComponentCatalogRoute> {
        CatalogScreen(
            items = ComponentCategory.entries.toList(),
            onItemClick = onItemClick,
            title = "Components",
            onNavigateBack = onNavigateBack,
            actions = {
                ConfigureButton(onClick = onConfigureClick)
            },
        )
    }
}
