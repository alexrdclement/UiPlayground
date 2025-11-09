package com.alexrdclement.uiplayground.app.demo.components.auth.navigation

import com.alexrdclement.uiplayground.components.layout.catalog.CatalogItem
import kotlinx.serialization.Serializable

@Serializable
enum class AuthComponent : CatalogItem {
    AuthButton,
    ;

    override val title = this.name
}
