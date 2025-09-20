package com.alexrdclement.uiplayground.components.geometry

import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import com.alexrdclement.uiplayground.components.util.ColorSaver
import com.alexrdclement.uiplayground.components.util.DrawStyleSaver
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.components.util.restore
import com.alexrdclement.uiplayground.components.util.save

data class GridLineStyle(
    val color: Color,
    val stroke: Stroke,
)

private const val colorKey = "color"
private const val strokeKey = "stroke"

val GridLineStyleSaver: Saver<GridLineStyle, Any> = mapSaverSafe(
    save = { value ->
        mapOf(
            colorKey to save(value.color, ColorSaver, this),
            strokeKey to save(value.stroke, DrawStyleSaver, this),
        )
    },
    restore = { map ->
        GridLineStyle(
            color = restore(map[colorKey], ColorSaver)!!,
            stroke = restore(map[strokeKey], DrawStyleSaver)!!,
        )
    }
)
