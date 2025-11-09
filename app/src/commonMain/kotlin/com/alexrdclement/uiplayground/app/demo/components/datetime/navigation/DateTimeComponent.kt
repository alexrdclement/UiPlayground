package com.alexrdclement.uiplayground.app.demo.components.datetime.navigation

import com.alexrdclement.uiplayground.components.layout.catalog.CatalogItem
import kotlinx.serialization.Serializable

@Serializable
enum class DateTimeComponent : CatalogItem {
    DateTimeFormat,
    ;

    override val title = this.name
}
