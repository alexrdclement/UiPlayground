package com.alexrdclement.uiplayground.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberNavController(state: NavState): NavController {
    return remember(state) { NavController(state) }
}

class NavController(private val state: NavState) {
    fun navigate(route: UiPlaygroundNavKey) {
        state.navigate(route)
    }

    fun goBack() {
        state.goBack()
    }
}
