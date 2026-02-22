package com.alexrdclement.uiplayground.app.configuration.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.scene.DialogSceneStrategy
import com.alexrdclement.palette.navigation.NavGraphBuilder
import com.alexrdclement.palette.navigation.NavKey
import com.alexrdclement.uiplayground.app.configuration.ConfigurationController
import com.alexrdclement.uiplayground.app.configuration.ConfigurationDialogContent

fun NavGraphBuilder.configurationNavGraph() {
    route(ConfigurationRoute)
}

fun EntryProviderScope<NavKey>.configurationEntryProvider(
    configurationController: ConfigurationController,
) {
    entry<ConfigurationRoute>(
        metadata = DialogSceneStrategy.dialog(),
    ) {
        ConfigurationDialogContent(configurationController = configurationController)
    }
}
