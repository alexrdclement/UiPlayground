package com.alexrdclement.uiplayground.app.demo.components

import com.alexrdclement.uiplayground.app.catalog.CatalogItem

enum class Component : CatalogItem {
    MediaControlSheet;

    override val title = this.name
}
