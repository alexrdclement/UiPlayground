package com.alexrdclement.uiplayground

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import androidx.navigation.ExperimentalBrowserHistoryApi
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.bindToBrowserNavigation
import androidx.savedstate.read
import androidx.savedstate.savedState
import com.alexrdclement.uiplayground.app.App
import kotlinx.browser.window
import kotlinx.serialization.ExperimentalSerializationApi
import org.w3c.dom.Location
import org.w3c.dom.PopStateEvent

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalBrowserHistoryApi::class,
)
fun main() {
    ComposeViewport {
        App(
            onNavHostReady = { navController ->
                navController.tryToNavigateToUrlFragment(window.location)

                window.addEventListener("popstate") { event ->
                    event as PopStateEvent
                    if (event.state == null) {
                        navController.tryToNavigateToUrlFragment(window.location)
                        return@addEventListener
                    }
                    navController.popBackStack()
                }

                navController.bindToBrowserNavigation { entry ->
                    getBackStackEntryRoute(
                        currentBackStack = navController.currentBackStack.value,
                        entry = entry,
                    )
                }
            },
        )
    }
}

@OptIn(ExperimentalSerializationApi::class)
private fun getBackStackEntryRoute(
    entry: NavBackStackEntry,
    currentBackStack: List<NavBackStackEntry>,
): String {
    val entryRoute = entry.getRouteWithEncodedArgs().orEmpty()

    val destinationRoutes = currentBackStack.filter { it.destination !is NavGraph }
    if (destinationRoutes.isEmpty()) { return "#$entryRoute" }

    val currentLast = destinationRoutes.last()
    val currentRoute = getBackStackEntryRoute(
        entry = currentLast,
        currentBackStack = destinationRoutes.take(destinationRoutes.size - 1),
    )

    if (entryRoute == currentLast.getRouteWithEncodedArgs()) {
        return currentRoute
    }

    return if (currentRoute.isEmpty()) {
        "#$entryRoute"
    } else {
        "$currentRoute/$entryRoute"
    }
}

// Below is copied + modified from androidx.navigation.BrowserHistory in navigation-runtime-wasm-js-2.9.0

private val argPlaceholder = Regex("""\{.*?\}""")
private fun NavBackStackEntry.getRouteWithEncodedArgs(): String? {
    val entry = this
    val route = entry.destination.route ?: return null
    if (!route.contains(argPlaceholder)) return route
    val args = entry.arguments ?: savedState()
    val nameToTypedValue = entry.destination.arguments.mapValues { (name, arg) ->
        arg.type.serializeAsValue(arg.type[args, name])
    }

    val routeWithFilledArgs = route.replace(argPlaceholder) { match ->
        val key = match.value.trim('{', '}')
        val value = nameToTypedValue[key]
        //untyped args stored as strings
        //see: androidx.navigation.NavDeepLink.parseArgument
            ?: args.read { getStringOrNull(key) ?: "" }
        encodeURIComponent(value)
    }

    return routeWithFilledArgs
}

private fun NavController.tryToNavigateToUrlFragment(location: Location) {
    val route = decodeURIComponent(location.hash.substringAfter('#', ""))
    if (route.isNotEmpty()) {
        try {
            navigate(route)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }
}

private fun encodeURIComponent(value: String): String {
    return value
}

private fun decodeURIComponent(value: String): String {
    return value
}
