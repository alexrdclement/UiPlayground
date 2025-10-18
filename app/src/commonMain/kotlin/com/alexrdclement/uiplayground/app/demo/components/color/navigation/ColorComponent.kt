package com.alexrdclement.uiplayground.app.demo.components.color.navigation

import com.alexrdclement.uiplayground.components.layout.catalog.CatalogItem
import kotlinx.serialization.Serializable

@Serializable
enum class ColorComponent : CatalogItem {
    ColorPicker,
    ;

    override val title = this.name
}
