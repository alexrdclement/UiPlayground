package com.alexrdclement.uiplayground.app.demo.experiments.navigation

import com.alexrdclement.uiplayground.app.navigation.deeplink.DeeplinkTreeBuilder

fun DeeplinkTreeBuilder.experimentsDeeplinkTree() {
    route(ExperimentCatalogRoute) {
        wildcardRoute<ExperimentRoute> { pathSegment -> ExperimentRoute.from(pathSegment) }
    }
}
