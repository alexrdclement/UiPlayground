package com.alexrdclement.uiplayground.app.theme.interaction

import com.alexrdclement.uiplayground.app.catalog.CatalogItem

enum class Interaction : CatalogItem {
    Indication,
    ;

    override val title = this.name
}
