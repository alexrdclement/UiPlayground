package com.alexrdclement.uiplayground.components.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Spacer(height: Dp = 0.dp, width: Dp = 0.dp) {
    Spacer(modifier = Modifier.height(height).width(width))
}

@Composable
fun Spacer(padding: PaddingValues) {
    Spacer(modifier = Modifier.padding(padding))
}
