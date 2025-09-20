package com.alexrdclement.uiplayground.app.catalog.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.catalog.MainCatalogItem
import com.alexrdclement.uiplayground.app.configuration.ConfigureButton
import kotlinx.serialization.Serializable

@Serializable
object CatalogRoute

fun NavGraphBuilder.mainCatalogScreen(
    onItemClick: (MainCatalogItem) -> Unit,
    onConfigureClick: () -> Unit,
) {
    composable<CatalogRoute> {
        CatalogScreen(
            items = MainCatalogItem.entries.toList(),
            onItemClick = onItemClick,
            actions = {
                ConfigureButton(onClick = onConfigureClick)
            },
        )
    }
}
