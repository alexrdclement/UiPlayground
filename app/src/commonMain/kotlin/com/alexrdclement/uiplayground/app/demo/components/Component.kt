package com.alexrdclement.uiplayground.app.demo.components

import com.alexrdclement.uiplayground.app.catalog.CatalogItem
import kotlinx.serialization.Serializable

@Serializable
enum class Component : CatalogItem {
    Button,
    CurrencyAmountField,
    Grid,
    GridSphere,
    MediaControlSheet,
    Text,
    TextField,
    Web,
    ;

    override val title = this.name
}
