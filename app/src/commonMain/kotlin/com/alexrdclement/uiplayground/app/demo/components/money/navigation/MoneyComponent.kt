package com.alexrdclement.uiplayground.app.demo.components.money.navigation

import com.alexrdclement.uiplayground.components.layout.catalog.CatalogItem
import kotlinx.serialization.Serializable

@Serializable
enum class MoneyComponent : CatalogItem {
    CurrencyAmountField,
    ;

    override val title = this.name
}
