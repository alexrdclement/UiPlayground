package com.alexrdclement.uiplayground.components.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.runtime.Composable

@Composable
fun WindowInsets.horizontalPaddingValues(): PaddingValues =
    this.asPaddingValues().calculateHorizontalPaddingValues()
