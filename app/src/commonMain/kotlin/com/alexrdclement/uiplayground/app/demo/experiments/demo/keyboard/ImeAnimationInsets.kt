package com.alexrdclement.uiplayground.app.demo.experiments.demo.keyboard

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable

/**
 * WindowInsets representing the IME animation source on platforms that support it.
 * On Android, this tracks the starting position of the keyboard animation.
 * On other platforms, returns an empty insets.
 */
expect val WindowInsets.Companion.imeAnimationSource: WindowInsets
    @Composable get

/**
 * WindowInsets representing the IME animation target on platforms that support it.
 * On Android, this tracks the target position of the keyboard animation.
 * On other platforms, returns an empty insets.
 */
expect val WindowInsets.Companion.imeAnimationTarget: WindowInsets
    @Composable get
