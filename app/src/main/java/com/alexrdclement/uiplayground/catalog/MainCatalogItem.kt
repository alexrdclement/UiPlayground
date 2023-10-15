package com.alexrdclement.uiplayground.catalog

enum class MainCatalogItem : CatalogItem {
    Components,
    Shaders;

    override val title = this.name
}
