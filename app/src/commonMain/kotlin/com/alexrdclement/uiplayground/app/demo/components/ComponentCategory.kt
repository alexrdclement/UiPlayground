package com.alexrdclement.uiplayground.app.demo.components

import com.alexrdclement.uiplayground.components.layout.catalog.CatalogItem
import kotlinx.serialization.Serializable

@Serializable
enum class ComponentCategory : CatalogItem {
    Color,
    Core,
    Geometry,
    Media,
    Money,
    ;

    override val title = this.name
}
