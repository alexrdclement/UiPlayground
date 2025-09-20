package com.alexrdclement.uiplayground.components.util

import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke

private enum class DrawStyleType {
    Fill,
    Stroke
}

private const val drawStyleKey = "drawStyle"
private const val strokeWidthKey = "strokeWidth"
private const val strokeMiterKey = "strokeMiter"
private const val strokeCapKey = "strokeCap"
private const val strokeJoinKey = "strokeJoin"

// NOTE: does not save PathEffect for Stroke
val DrawStyleSaver: Saver<DrawStyle, Any> = mapSaverSafe(
    save = { value ->
        when (value) {
            Fill -> mapOf(
                drawStyleKey to DrawStyleType.Fill,
            )
            is Stroke -> mapOf(
                drawStyleKey to DrawStyleType.Stroke,
                strokeWidthKey to value.width,
                strokeMiterKey to value.miter,
                strokeCapKey to save(value.cap, StrokeCapSaver, this),
                strokeJoinKey to save(value.join, StrokeJoinSaver, this),
            )
        }
    },
    restore = { map ->
        when (map[drawStyleKey] as DrawStyleType) {
            DrawStyleType.Fill -> Fill
            DrawStyleType.Stroke -> Stroke(
                width = map[strokeWidthKey] as Float,
                miter = map[strokeMiterKey] as Float,
                cap = restore(map[strokeCapKey], StrokeCapSaver)!!,
                join = restore(map[strokeJoinKey], StrokeJoinSaver)!!,
            )
        }
    }
)
