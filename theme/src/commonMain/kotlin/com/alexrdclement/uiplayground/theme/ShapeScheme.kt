package com.alexrdclement.uiplayground.theme

import androidx.compose.ui.unit.dp

data class ShapeScheme(
    val primary: Shape,
    val secondary: Shape,
    val tertiary: Shape,
)

val PlaygroundShapeScheme = ShapeScheme(
    primary = Shape.Rectangle(
        cornerRadius = 64.dp,
    ),
    secondary = Shape.Rectangle(),
    tertiary = Shape.Circle,
)

fun ShapeScheme.copy(
    token: ShapeToken,
    shape: Shape,
) = this.copy(
    primary = if (token == ShapeToken.Primary) shape else this.primary,
    secondary = if (token == ShapeToken.Secondary) shape else this.secondary,
    tertiary = if (token == ShapeToken.Tertiary) shape else this.tertiary,
)
