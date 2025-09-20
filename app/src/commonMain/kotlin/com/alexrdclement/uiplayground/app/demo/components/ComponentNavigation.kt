package com.alexrdclement.uiplayground.app.demo.components

import androidx.navigation.NavController
import com.alexrdclement.uiplayground.app.demo.components.core.navigation.navigateToCoreComponents
import com.alexrdclement.uiplayground.app.demo.components.geometry.navigation.navigateToGeometryComponents
import com.alexrdclement.uiplayground.app.demo.components.media.navigation.navigateToMediaComponents
import com.alexrdclement.uiplayground.app.demo.components.money.navigation.navigateToMoneyComponents

fun NavController.navigateToComponent(component: Component) {
    when (component) {
        Component.Core -> navigateToCoreComponents()
        Component.Geometry -> navigateToGeometryComponents()
        Component.Media -> navigateToMediaComponents()
        Component.Money -> navigateToMoneyComponents()
    }
}
