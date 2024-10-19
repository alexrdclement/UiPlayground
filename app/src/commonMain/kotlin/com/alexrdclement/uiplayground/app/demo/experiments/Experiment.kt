package com.alexrdclement.uiplayground.app.demo.experiments

import com.alexrdclement.uiplayground.app.catalog.CatalogItem

enum class Experiment : CatalogItem {
    TextField;

    override val title = this.name
}
