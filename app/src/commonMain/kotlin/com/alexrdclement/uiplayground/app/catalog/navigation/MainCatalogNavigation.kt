package com.alexrdclement.uiplayground.app.catalog.navigation

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.catalog.MainCatalogItem
import com.alexrdclement.uiplayground.app.configuration.ConfigureButton
import com.alexrdclement.uiplayground.app.configuration.navigation.ConfigurationRoute
import com.alexrdclement.uiplayground.app.demo.experiments.navigation.ExperimentCatalogRoute
import com.alexrdclement.uiplayground.app.navigation.NavController
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNavKey
import com.alexrdclement.uiplayground.app.navigation.toPathSegment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("catalog")
data object MainCatalogRoute : UiPlaygroundNavKey {
    override val pathSegment = "catalog".toPathSegment()
}

@Composable
fun MainCatalogScreen(
    navController: NavController,
) {
    CatalogScreen(
        items = MainCatalogItem.entries.toList(),
        onItemClick = { item ->
            when (item) {
                MainCatalogItem.Experiments -> {
                    navController.navigate(ExperimentCatalogRoute)
                }
            }
        },
        actions = {
            ConfigureButton(
                onClick = { navController.navigate(ConfigurationRoute) },
            )
        },
    )
}
