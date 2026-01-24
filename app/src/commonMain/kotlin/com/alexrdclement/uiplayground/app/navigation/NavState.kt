package com.alexrdclement.uiplayground.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList

@Composable
fun rememberNavState(
    startRoute: UiPlaygroundNavKey,
): NavState {
    val backStack = remember {
        mutableStateListOf(startRoute)
    }

    return remember {
        NavState(backStack)
    }
}

class NavState(
    val backStack: SnapshotStateList<UiPlaygroundNavKey>
) {
    fun navigate(route: UiPlaygroundNavKey) {
        backStack.add(route)
    }

    fun goBack() {
        backStack.removeLastOrNull()
    }
}
