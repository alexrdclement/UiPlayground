package com.alexrdclement.uiplayground.app.demo.experiments.demo.keyboard

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable

actual val WindowInsets.Companion.imeAnimationSource: WindowInsets
    @Composable get() = WindowInsets(left = 0, top = 0, right = 0, bottom = 0)

actual val WindowInsets.Companion.imeAnimationTarget: WindowInsets
    @Composable get() = WindowInsets(left = 0, top = 0, right = 0, bottom = 0)
