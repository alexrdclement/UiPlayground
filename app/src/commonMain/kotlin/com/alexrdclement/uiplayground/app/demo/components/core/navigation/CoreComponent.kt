package com.alexrdclement.uiplayground.app.demo.components.core.navigation

import com.alexrdclement.uiplayground.components.layout.catalog.CatalogItem
import kotlinx.serialization.Serializable

@Serializable
enum class CoreComponent : CatalogItem {
    Button,
    Text,
    TextField,
    ;

    override val title = this.name
}
