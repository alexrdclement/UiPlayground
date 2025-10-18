package com.alexrdclement.uiplayground.app.demo.components.media.navigation

import com.alexrdclement.uiplayground.components.layout.catalog.CatalogItem
import kotlinx.serialization.Serializable

@Serializable
enum class MediaComponent : CatalogItem {
    MediaControlSheet,
    ;

    override val title = this.name
}
