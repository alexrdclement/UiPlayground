package com.alexrdclement.uiplayground.components.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawTransform

fun DrawTransform.rotate(
    degrees: Float,
    pivot: Offset = Offset.Zero,
    block: () -> Unit,
) {
    rotate(degrees, pivot)
    block()
    rotate(-degrees, pivot)
}
