package com.alexrdclement.uiplayground.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.scene.SinglePaneSceneStrategy
import androidx.navigation3.ui.NavDisplay
import com.alexrdclement.palette.navigation.NavController
import com.alexrdclement.palette.navigation.NavGraphRoute
import com.alexrdclement.palette.navigation.NavKey
import com.alexrdclement.palette.navigation.navGraph
import com.alexrdclement.palette.navigation.rememberNavController
import com.alexrdclement.palette.navigation.rememberNavState
import com.alexrdclement.palette.navigation.toPathSegment
import com.alexrdclement.uiplayground.app.configuration.ConfigurationController
import com.alexrdclement.uiplayground.app.configuration.navigation.configurationEntryProvider
import com.alexrdclement.uiplayground.app.configuration.navigation.configurationNavGraph
import com.alexrdclement.uiplayground.app.demo.experiments.navigation.experimentsEntryProvider
import com.alexrdclement.uiplayground.app.demo.experiments.navigation.experimentsNavGraph
import com.alexrdclement.uiplayground.app.main.navigation.MainGraph
import com.alexrdclement.uiplayground.app.main.navigation.mainEntryProvider
import com.alexrdclement.uiplayground.app.main.navigation.mainNavGraph
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("ui-playground")
data object UiPlaygroundGraph : NavGraphRoute {
    override val pathSegment = "".toPathSegment()
}

val UiPlaygroundNavGraph = navGraph(
    root = UiPlaygroundGraph,
    start = MainGraph,
) {
    mainNavGraph()
    configurationNavGraph()
    experimentsNavGraph()
}

@Composable
fun UiPlaygroundNav(
    configurationController: ConfigurationController,
    navController: NavController = rememberUiPlaygroundNavController(),
) {
    NavDisplay(
        backStack = navController.state.backStack,
        sceneStrategy = DialogSceneStrategy<NavKey>() then SinglePaneSceneStrategy(),
        entryProvider = entryProvider {
            uiPlaygroundEntryProvider(
                navController = navController,
                configurationController = configurationController,
            )
        },
        onBack = navController::goBack,
    )
}

fun EntryProviderScope<NavKey>.uiPlaygroundEntryProvider(
    navController: NavController,
    configurationController: ConfigurationController,
) {
    mainEntryProvider(navController)
    experimentsEntryProvider(navController)
    configurationEntryProvider(configurationController)
}

@Composable
fun rememberUiPlaygroundNavController(
    initialDeeplink: String? = null,
    buildSyntheticBackStack: Boolean = true,
    onBackStackEmpty: () -> Unit = {},
) = rememberNavController(
    state = rememberUiPlaygroundNavState(
        initialDeeplink = initialDeeplink,
        buildSyntheticBackStack = buildSyntheticBackStack,
        onBackStackEmpty = onBackStackEmpty,
    ),
)

@Composable
fun rememberUiPlaygroundNavState(
    initialDeeplink: String? = null,
    buildSyntheticBackStack: Boolean = true,
    onBackStackEmpty: () -> Unit = {},
) = rememberNavState(
    navGraph = UiPlaygroundNavGraph,
    initialDeeplink = initialDeeplink,
    buildSyntheticBackStack = buildSyntheticBackStack,
    onWouldBecomeEmpty = onBackStackEmpty,
)
