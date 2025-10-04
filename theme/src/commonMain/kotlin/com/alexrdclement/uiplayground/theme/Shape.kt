package com.alexrdclement.uiplayground.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.RectangleShape

enum class ShapeType {
    Rectangle,
    Circle,
}

sealed class Shape(
    val type: ShapeType,
) {
    object Rectangle : Shape(type = ShapeType.Rectangle)
    object Circle : Shape(type = ShapeType.Circle)
}

fun ShapeType.toShape(shapeScheme: ShapeScheme): Shape {
    return when (this) {
        ShapeType.Rectangle -> Shape.Rectangle
        ShapeType.Circle -> Shape.Circle
    }
}

@Composable
fun ShapeType.toShape(): Shape {
    return toShape(PlaygroundTheme.shapeScheme)
}

fun Shape.toComposeShape(): androidx.compose.ui.graphics.Shape {
    return when (this) {
        Shape.Rectangle -> RectangleShape
        Shape.Circle -> CircleShape
    }
}
