package com.alexrdclement.uiplayground.app.catalog

enum class MainCatalogItem : CatalogItem {
    Components,
    Experiments,
    Shaders;

    override val title = this.name
}
