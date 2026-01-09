package com.alexrdclement.uiplayground.app.demo.experiments.demo.keyboard

import androidx.compose.ui.Modifier

/**
 * Enables nested scrolling with the IME (keyboard) on platforms that support it.
 * On Android, allows dragging the keyboard open/closed with scroll gestures.
 * On other platforms, this is a no-op.
 */
expect fun Modifier.imeNestedScroll(): Modifier
