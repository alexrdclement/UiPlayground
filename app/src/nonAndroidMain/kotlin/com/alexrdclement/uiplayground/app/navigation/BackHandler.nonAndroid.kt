package com.alexrdclement.uiplayground.app.navigation

import androidx.compose.runtime.Composable

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    // No-op for non-Android platforms
    // iOS and Desktop handle back navigation differently
}
