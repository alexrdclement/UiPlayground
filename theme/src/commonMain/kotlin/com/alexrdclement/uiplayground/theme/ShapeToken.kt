package com.alexrdclement.uiplayground.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape as ComposeShape

enum class ShapeToken {
    Primary,
    Secondary,
    Tertiary,
    Surface,
}

fun ShapeToken.toShape(shapeScheme: ShapeScheme): Shape {
    return when (this) {
        ShapeToken.Primary -> shapeScheme.primary
        ShapeToken.Secondary -> shapeScheme.secondary
        ShapeToken.Tertiary -> shapeScheme.tertiary
        ShapeToken.Surface -> shapeScheme.surface
    }
}

@Composable
fun ShapeToken.toShape(): Shape {
    return toShape(PlaygroundTheme.shapeScheme)
}

fun ShapeToken.toComposeShape(shapeScheme: ShapeScheme): ComposeShape {
    return this.toShape(shapeScheme).toComposeShape()
}

@Composable
fun ShapeToken.toComposeShape(): ComposeShape {
    return toComposeShape(PlaygroundTheme.shapeScheme)
}
