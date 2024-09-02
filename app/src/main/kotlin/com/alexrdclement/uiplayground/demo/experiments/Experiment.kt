package com.alexrdclement.uiplayground.demo.experiments

import com.alexrdclement.uiplayground.catalog.CatalogItem

enum class Experiment : CatalogItem {
    BasicTextField;

    override val title = this.name
}
