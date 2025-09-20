package com.alexrdclement.uiplayground.app.demo.components.media.navigation

import com.alexrdclement.uiplayground.app.catalog.CatalogItem
import kotlinx.serialization.Serializable

@Serializable
enum class MediaComponent : CatalogItem {
    MediaControlSheet,
    ;

    override val title = this.name
}
