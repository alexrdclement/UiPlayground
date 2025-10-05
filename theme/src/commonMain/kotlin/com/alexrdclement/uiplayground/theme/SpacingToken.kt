package com.alexrdclement.uiplayground.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

enum class SpacingToken {
    XS,
    Small,
    Medium,
    Large,
}

fun SpacingToken.toSpacing(spacing: Spacing): Dp {
    return when (this) {
        SpacingToken.XS -> spacing.xs
        SpacingToken.Small -> spacing.small
        SpacingToken.Medium -> spacing.medium
        SpacingToken.Large -> spacing.large
    }
}

@Composable
fun SpacingToken.toSpacing(): Dp {
    return toSpacing(PlaygroundTheme.spacing)
}
