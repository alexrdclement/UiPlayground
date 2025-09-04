package com.alexrdclement.uiplayground.app.demo.components

import com.alexrdclement.uiplayground.app.catalog.CatalogItem
import kotlinx.serialization.Serializable

@Serializable
enum class Component : CatalogItem {
    Button,
    CurrencyAmountField,
    CurveStitch,
    Grid,
    MediaControlSheet,
    Sphere,
    Text,
    TextField,
    ;

    override val title = this.name
}
