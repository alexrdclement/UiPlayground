package com.alexrdclement.uiplayground.app.demo

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController

/**
 * Pop the current back stack entry only if its current lifecycle state is resumed.
 * De-dupes pop back stack events if called in rapid succession.
 */
fun NavController.popBackStackIfResumed(): Boolean {
    return if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    } else {
        false
    }
}