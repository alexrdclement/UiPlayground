package com.alexrdclement.uiplayground.components

import com.alexrdclement.uiplayground.catalog.CatalogItem

enum class Component : CatalogItem {
    MediaControlSheet;

    override val title = this.name
}
