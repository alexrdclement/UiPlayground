package com.alexrdclement.uiplayground.app.demo.components.color.navigation

import com.alexrdclement.uiplayground.app.catalog.CatalogItem
import kotlinx.serialization.Serializable

@Serializable
enum class ColorComponent : CatalogItem {
    ColorPicker,
    ;

    override val title = this.name
}
