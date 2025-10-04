package com.alexrdclement.uiplayground.app.theme

import com.alexrdclement.uiplayground.app.catalog.CatalogItem

enum class ThemeItem : CatalogItem {
    Color,
    Interaction,
    Shape,
    Typography,
    ;

    override val title = this.name
}
