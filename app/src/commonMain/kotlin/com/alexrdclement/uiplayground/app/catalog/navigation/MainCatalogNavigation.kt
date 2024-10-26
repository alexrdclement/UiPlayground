package com.alexrdclement.uiplayground.app.catalog.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alexrdclement.uiplayground.app.catalog.CatalogScreen
import com.alexrdclement.uiplayground.app.catalog.MainCatalogItem
import kotlinx.serialization.Serializable

@Serializable
object CatalogRoute

fun NavGraphBuilder.mainCatalogScreen(
    onItemClick: (MainCatalogItem) -> Unit,
) {
    composable<CatalogRoute> {
        CatalogScreen(
            items = MainCatalogItem.entries.toList(),
            onItemClick = onItemClick,
        )
    }
}
