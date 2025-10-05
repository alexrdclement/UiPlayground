package com.alexrdclement.uiplayground.theme.util

import kotlin.math.PI

// TODO: share with components
fun Int.toRadians(): Double {
    return this * (PI / 180.0)
}
