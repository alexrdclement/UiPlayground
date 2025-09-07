package com.alexrdclement.uiplayground.components.util

import kotlin.math.PI

fun Int.toRadians(): Double {
    return this * (PI / 180.0)
}

fun Float.toRadians(): Double {
    return this * (PI / 180.0)
}
