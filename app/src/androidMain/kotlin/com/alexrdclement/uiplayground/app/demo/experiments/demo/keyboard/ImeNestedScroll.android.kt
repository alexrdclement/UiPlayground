package com.alexrdclement.uiplayground.app.demo.experiments.demo.keyboard

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.ui.Modifier

@OptIn(ExperimentalLayoutApi::class)
actual fun Modifier.imeNestedScroll(): Modifier = this.imeNestedScroll()
