package com.alexrdclement.uiplayground.app.catalog

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        CatalogScreen(
            items = MainCatalogItem.entries.toList(),
            onItemClick = {}
        )
    }
}

@Preview
@Composable
private fun WithNavPreview() {
    UiPlaygroundPreview {
        CatalogScreen(
            items = MainCatalogItem.entries.toList(),
            onItemClick = {},
            title = "Components",
            onNavigateBack = {},
        )
    }
}
