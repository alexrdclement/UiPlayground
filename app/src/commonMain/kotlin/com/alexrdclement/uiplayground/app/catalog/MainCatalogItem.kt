package com.alexrdclement.uiplayground.app.catalog

import com.alexrdclement.palette.components.layout.catalog.CatalogItem

enum class MainCatalogItem : CatalogItem {
    Experiments;

    override val title = this.name
}
