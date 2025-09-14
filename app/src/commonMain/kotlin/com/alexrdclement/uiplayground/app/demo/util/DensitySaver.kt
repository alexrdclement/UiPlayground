package com.alexrdclement.uiplayground.app.demo.util

import androidx.compose.ui.unit.Density
import com.alexrdclement.uiplayground.components.util.mapSaverSafe

private const val densityKey = "density"
private const val fontScaleKey = "fontScale"

val DensitySaver = mapSaverSafe(
    save = { value ->
        mapOf(
            densityKey to value.density,
            fontScaleKey to value.fontScale,
        )
    },
    restore = { map ->
        Density(
            density = map[densityKey] as Float,
            fontScale = map[fontScaleKey] as Float,
        )
    }
)
