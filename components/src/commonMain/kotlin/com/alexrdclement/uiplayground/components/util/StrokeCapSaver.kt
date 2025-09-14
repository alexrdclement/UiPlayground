package com.alexrdclement.uiplayground.components.util

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.ui.graphics.StrokeCap

private enum class StrokeCapType {
    Butt,
    Round,
    Square
}

val StrokeCapSaver = object : Saver<StrokeCap, Any> {
    override fun SaverScope.save(value: StrokeCap): Any = when (value) {
        StrokeCap.Butt -> StrokeCapType.Butt
        StrokeCap.Round -> StrokeCapType.Round
        StrokeCap.Square -> StrokeCapType.Square
        else -> throw IllegalArgumentException("Unknown StrokeCap: $value")
    }

    override fun restore(value: Any): StrokeCap = when (value as StrokeCapType) {
        StrokeCapType.Butt -> StrokeCap.Butt
        StrokeCapType.Round -> StrokeCap.Round
        StrokeCapType.Square -> StrokeCap.Square
    }
}
