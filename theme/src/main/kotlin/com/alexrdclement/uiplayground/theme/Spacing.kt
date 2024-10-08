package com.alexrdclement.uiplayground.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val xs: Dp,
    val small: Dp,
    val medium: Dp,
    val large: Dp,
)

val PlaygroundSpacing = Spacing(
    xs = 4.dp,
    small = 8.dp,
    medium = 16.dp,
    large = 24.dp,
)
