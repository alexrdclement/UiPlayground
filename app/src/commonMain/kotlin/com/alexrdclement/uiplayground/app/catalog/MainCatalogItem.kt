package com.alexrdclement.uiplayground.app.catalog

import com.alexrdclement.uiplayground.components.layout.catalog.CatalogItem

enum class MainCatalogItem : CatalogItem {
    Components,
    Experiments,
    Shaders;

    override val title = this.name
}
