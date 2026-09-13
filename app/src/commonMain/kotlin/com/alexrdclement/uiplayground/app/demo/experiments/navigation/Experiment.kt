package com.alexrdclement.uiplayground.app.demo.experiments.navigation

import com.embarrasdf.palette.components.layout.catalog.CatalogItem
import kotlinx.serialization.Serializable

@Serializable
enum class Experiment : CatalogItem {
    AnimateScrollItemVisible,
    Gradients,
    Keyboard,
    UiEvent,
    ;

    override val title = this.name
}
