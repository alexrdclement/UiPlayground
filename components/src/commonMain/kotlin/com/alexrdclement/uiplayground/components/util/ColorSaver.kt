package com.alexrdclement.uiplayground.components.util

import androidx.compose.ui.graphics.Color

private const val redKey = "red"
private const val greenKey = "green"
private const val blueKey = "blue"
private const val alphaKey = "alpha"

val ColorSaver = mapSaverSafe(
    save = { color ->
        mapOf(
            redKey to color.red,
            greenKey to color.green,
            blueKey to color.blue,
            alphaKey to color.alpha,
        )
    },
    restore = { map ->
        Color(
            red = map[redKey] as Float,
            green = map[greenKey] as Float,
            blue = map[blueKey] as Float,
            alpha = map[alphaKey] as Float,
        )
    },
)
