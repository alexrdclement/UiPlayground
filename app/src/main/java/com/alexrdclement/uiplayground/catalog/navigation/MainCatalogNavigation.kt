package com.alexrdclement.uiplayground.catalog.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alexrdclement.uiplayground.catalog.CatalogScreen
import com.alexrdclement.uiplayground.catalog.MainCatalogItem

const val mainCatalogRoute = "catalog"

fun NavGraphBuilder.mainCatalogScreen(
    onItemClick: (MainCatalogItem) -> Unit,
) {
    composable(mainCatalogRoute) {
        CatalogScreen(
            items = MainCatalogItem.entries.toList(),
            onItemClick = onItemClick,
        )
    }
}
