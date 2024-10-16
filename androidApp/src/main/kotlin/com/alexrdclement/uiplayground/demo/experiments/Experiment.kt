package com.alexrdclement.uiplayground.demo.experiments

import com.alexrdclement.uiplayground.catalog.CatalogItem

enum class Experiment : CatalogItem {
    TextField;

    override val title = this.name
}
