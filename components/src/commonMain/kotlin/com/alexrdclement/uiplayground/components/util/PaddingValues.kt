package com.alexrdclement.uiplayground.components.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

fun PaddingValues.calculateVerticalPadding() =
    this.calculateTopPadding() + this.calculateBottomPadding()

fun PaddingValues.calculateVerticalPaddingValues() = PaddingValues(
    start = 0.dp,
    top = this.calculateTopPadding(),
    end = 0.dp,
    bottom = this.calculateBottomPadding(),
)

@Composable
fun PaddingValues.calculateStartPadding() = this.calculateStartPadding(LocalLayoutDirection.current)

@Composable
fun PaddingValues.calculateEndPadding() = this.calculateEndPadding(LocalLayoutDirection.current)

@Composable
fun PaddingValues.calculateHorizontalPaddingValues() = PaddingValues(
    start = this.calculateStartPadding(),
    top = 0.dp,
    end = this.calculateEndPadding(),
    bottom = 0.dp,
)

@Composable
fun PaddingValues.calculateHorizontalPadding() = calculateHorizontalPadding(
    layoutDirection = LocalLayoutDirection.current,
)

fun PaddingValues.calculateHorizontalPadding(layoutDirection: LayoutDirection) =
    this.calculateStartPadding(layoutDirection) + this.calculateEndPadding(layoutDirection)

@Composable
operator fun PaddingValues.plus(other: PaddingValues): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current
    return this.plus(other, layoutDirection)
}

fun PaddingValues.plus(
    other: PaddingValues,
    layoutDirection: LayoutDirection,
): PaddingValues {
    return plus(
        start = other.calculateStartPadding(layoutDirection = layoutDirection),
        top = other.calculateTopPadding(),
        end = other.calculateEndPadding(layoutDirection = layoutDirection),
        bottom = other.calculateBottomPadding(),
        layoutDirection = layoutDirection,
    )
}

@Composable
fun PaddingValues.plus(
    start: Dp = 0.dp,
    top: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp,
): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current
    return this.plus(
        start = start,
        top = top,
        end = end,
        bottom = bottom,
        layoutDirection = layoutDirection,
    )
}

fun PaddingValues.plus(
    start: Dp = 0.dp,
    top: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp,
    layoutDirection: LayoutDirection,
): PaddingValues {
    return copy(
        start = this.calculateStartPadding(layoutDirection = layoutDirection) + start,
        top = this.calculateTopPadding() + top,
        end = this.calculateEndPadding(layoutDirection = layoutDirection) + end,
        bottom = this.calculateBottomPadding() + bottom,
        layoutDirection = layoutDirection,
    )
}

@Composable
fun PaddingValues.plus(
    horizontal: PaddingValues = PaddingValues(0.dp),
    vertical: PaddingValues = PaddingValues(0.dp),
): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current
    return this.plus(
        vertical = vertical,
        horizontal = horizontal,
        layoutDirection = layoutDirection,
    )
}

fun PaddingValues.plus(
    horizontal: PaddingValues = PaddingValues(0.dp),
    vertical: PaddingValues = PaddingValues(0.dp),
    layoutDirection: LayoutDirection,
): PaddingValues {
    return this.plus(
        start = horizontal.calculateStartPadding(layoutDirection = layoutDirection),
        top = vertical.calculateTopPadding(),
        end = horizontal.calculateEndPadding(layoutDirection = layoutDirection),
        bottom = vertical.calculateBottomPadding(),
        layoutDirection = layoutDirection,
    )
}

@Composable
fun PaddingValues.plus(
    all: Dp,
): PaddingValues {
    return this.plus(
        vertical = PaddingValues(all = all),
        horizontal = PaddingValues(all = all),
        layoutDirection = LocalLayoutDirection.current,
    )
}

fun PaddingValues.plus(
    all: Dp,
    layoutDirection: LayoutDirection,
): PaddingValues {
    return this.plus(
        vertical = PaddingValues(all = all),
        horizontal = PaddingValues(all = all),
        layoutDirection = layoutDirection,
    )
}

@Composable
operator fun PaddingValues.minus(other: PaddingValues): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current
    return this.minus(other, layoutDirection)
}

fun PaddingValues.minus(
    other: PaddingValues,
    layoutDirection: LayoutDirection,
): PaddingValues {
    return minus(
        start = other.calculateStartPadding(layoutDirection = layoutDirection),
        top = other.calculateTopPadding(),
        end = other.calculateEndPadding(layoutDirection = layoutDirection),
        bottom = other.calculateBottomPadding(),
        layoutDirection = layoutDirection,
    )
}

@Composable
fun PaddingValues.minus(
    start: Dp = 0.dp,
    top: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp,
): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current
    return minus(
        start = start,
        top = top,
        end = end,
        bottom = bottom,
        layoutDirection = layoutDirection,
    )
}

fun PaddingValues.minus(
    start: Dp = 0.dp,
    top: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp,
    layoutDirection: LayoutDirection,
): PaddingValues {
    return copy(
        start = (this.calculateStartPadding(layoutDirection = layoutDirection) - start)
            .coerceAtLeast(0.dp),
        top = (this.calculateTopPadding() - top).coerceAtLeast(0.dp),
        end = (this.calculateEndPadding(layoutDirection = layoutDirection) - end).coerceAtLeast(0.dp),
        bottom = (this.calculateBottomPadding() - bottom).coerceAtLeast(0.dp),
        layoutDirection = layoutDirection,
    )
}

@Composable
fun PaddingValues.minus(
    horizontal: PaddingValues = PaddingValues(0.dp),
    vertical: PaddingValues = PaddingValues(0.dp),
): PaddingValues {
    val layoutDirection = LocalLayoutDirection.current
    return this.minus(
        vertical = vertical,
        horizontal = horizontal,
        layoutDirection = layoutDirection,
    )
}

fun PaddingValues.minus(
    horizontal: PaddingValues = PaddingValues(0.dp),
    vertical: PaddingValues = PaddingValues(0.dp),
    layoutDirection: LayoutDirection,
): PaddingValues {
    return this.minus(
        start = horizontal.calculateStartPadding(layoutDirection = layoutDirection),
        top = vertical.calculateTopPadding(),
        end = horizontal.calculateEndPadding(layoutDirection = layoutDirection),
        bottom = vertical.calculateBottomPadding(),
        layoutDirection = layoutDirection,
    )
}

@Composable
fun PaddingValues.copy(
    start: Dp? = null,
    top: Dp? = null,
    end: Dp? = null,
    bottom: Dp? = null,
): PaddingValues {
    return this.copy(
        start = start,
        top = top,
        end = end,
        bottom = bottom,
        layoutDirection = LocalLayoutDirection.current,
    )
}

fun PaddingValues.copy(
    start: Dp? = null,
    top: Dp? = null,
    end: Dp? = null,
    bottom: Dp? = null,
    layoutDirection: LayoutDirection,
): PaddingValues {
    return PaddingValues(
        start = start ?: this.calculateStartPadding(layoutDirection = layoutDirection),
        top = top ?: this.calculateTopPadding(),
        end = end ?: this.calculateEndPadding(layoutDirection = layoutDirection),
        bottom = bottom ?: this.calculateBottomPadding(),
    )
}
