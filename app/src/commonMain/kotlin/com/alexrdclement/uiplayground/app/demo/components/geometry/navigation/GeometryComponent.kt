package com.alexrdclement.uiplayground.app.demo.components.geometry.navigation

import com.alexrdclement.uiplayground.components.layout.catalog.CatalogItem
import kotlinx.serialization.Serializable

@Serializable
enum class GeometryComponent : CatalogItem {
    CurveStitch,
    Grid,
    Sphere,
    ;

    override val title = this.name
}
