package com.alexrdclement.uiplayground.app.demo.experiments

import com.alexrdclement.uiplayground.components.layout.catalog.CatalogItem
import kotlinx.serialization.Serializable

@Serializable
enum class Experiment : CatalogItem {
    AnimateScrollItemVisible,
    Fade,
    Gradients,
    UiEvent,
    ;

    override val title = this.name
}
