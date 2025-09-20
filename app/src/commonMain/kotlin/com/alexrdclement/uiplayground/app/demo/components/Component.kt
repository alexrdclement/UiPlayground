package com.alexrdclement.uiplayground.app.demo.components

import com.alexrdclement.uiplayground.app.catalog.CatalogItem
import kotlinx.serialization.Serializable

@Serializable
enum class Component : CatalogItem {
    Core,
    Geometry,
    Media,
    Money,
    ;

    override val title = this.name
}
