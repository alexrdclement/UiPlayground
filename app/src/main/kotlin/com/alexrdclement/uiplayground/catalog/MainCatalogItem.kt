package com.alexrdclement.uiplayground.catalog

enum class MainCatalogItem : CatalogItem {
    Components,
    Experiments,
    Shaders;

    override val title = this.name
}
