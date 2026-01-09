package com.alexrdclement.uiplayground.app.demo.experiments.demo.keyboard

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.imeAnimationSource
import androidx.compose.foundation.layout.imeAnimationTarget
import androidx.compose.runtime.Composable

@OptIn(ExperimentalLayoutApi::class)
actual val WindowInsets.Companion.imeAnimationSource: WindowInsets
    @Composable get() = imeAnimationSource

@OptIn(ExperimentalLayoutApi::class)
actual val WindowInsets.Companion.imeAnimationTarget: WindowInsets
    @Composable get() = imeAnimationTarget
