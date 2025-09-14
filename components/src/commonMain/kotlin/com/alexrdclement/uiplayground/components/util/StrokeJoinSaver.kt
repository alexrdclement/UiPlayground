package com.alexrdclement.uiplayground.components.util

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.ui.graphics.StrokeJoin

private enum class StrokeJoinType {
    Miter,
    Round,
    Bevel,
}

val StrokeJoinSaver = object : Saver<StrokeJoin, Any> {
    override fun SaverScope.save(value: StrokeJoin): Any = when (value) {
        StrokeJoin.Miter -> StrokeJoinType.Miter
        StrokeJoin.Round -> StrokeJoinType.Round
        StrokeJoin.Bevel -> StrokeJoinType.Bevel
        else -> throw IllegalArgumentException("Unknown StrokeJoin: $value")
    }

    override fun restore(value: Any): StrokeJoin = when (value as StrokeJoinType) {
        StrokeJoinType.Miter -> StrokeJoin.Miter
        StrokeJoinType.Round -> StrokeJoin.Round
        StrokeJoinType.Bevel -> StrokeJoin.Bevel
    }
}
