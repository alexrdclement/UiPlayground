package com.alexrdclement.uiplayground.app.demo.experiments

import com.alexrdclement.uiplayground.app.catalog.CatalogItem
import kotlinx.serialization.Serializable

@Serializable
enum class Experiment : CatalogItem {
    TextField;

    override val title = this.name
}