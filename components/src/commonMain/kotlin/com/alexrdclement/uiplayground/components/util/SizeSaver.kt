package com.alexrdclement.uiplayground.components.util

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

private const val widthKey = "width"
private const val heightKey = "height"

val IntSizeSaver = mapSaverSafe(
    save = { value -> mapOf(widthKey to value.width, heightKey to value.height) },
    restore = { map ->
        val width = map[widthKey] as Int
        val height = map[heightKey] as Int
        IntSize(width, height)
    },
)

val DpSizeSaver = mapSaverSafe(
    save = { value ->
        mapOf(
            widthKey to value.width.value,
            heightKey to value.height.value,
        )
   },
    restore = { map ->
        DpSize(
            width = (map[widthKey] as Float).dp,
            height = (map[heightKey] as Float).dp,
        )
    },
)
