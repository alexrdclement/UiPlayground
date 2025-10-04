package com.alexrdclement.uiplayground.theme

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.RectangleShape
import com.alexrdclement.uiplayground.theme.util.toRadians
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

enum class ShapeType {
    Rectangle,
    Circle,
    Triangle,
    Diamond,
}

sealed class Shape(
    val type: ShapeType,
) {
    object Rectangle : Shape(type = ShapeType.Rectangle)
    object Circle : Shape(type = ShapeType.Circle)
    object Triangle : Shape(type = ShapeType.Triangle)
    object Diamond : Shape(type = ShapeType.Diamond)
}

fun ShapeType.toShape(shapeScheme: ShapeScheme): Shape {
    return when (this) {
        ShapeType.Rectangle -> Shape.Rectangle
        ShapeType.Circle -> Shape.Circle
        ShapeType.Triangle -> Shape.Triangle
        ShapeType.Diamond -> Shape.Diamond
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
        Shape.Triangle -> TriangleShape
        Shape.Diamond -> DiamondShape
    }
}

val CircleShape = GenericShape { size, _ ->
    val radius = size.maxDimension / 2f
    val centerX = size.width / 2f
    val centerY = size.height / 2f
    moveTo(centerX + radius, centerY)
    for (i in 1..360) {
        val angle = i.toRadians()
        val x = centerX + radius * cos(angle)
        val y = centerY + radius * sin(angle)
        lineTo(x.toFloat(), y.toFloat())
    }
    close()
}

val TriangleShape = GenericShape { size, _ ->
    val triangleHeight = size.height * (sqrt(3f) / 2f)
    val halfTriangleHeight = triangleHeight / 2f
    val triangleWidth = size.width
    val widthDiff = triangleWidth - size.width
    moveTo(size.width / 2f, -halfTriangleHeight)
    lineTo(-widthDiff, triangleHeight)
    lineTo(size.width + widthDiff, triangleHeight)
    close()
}

val DiamondShape = GenericShape { size, _ ->
    moveTo(size.width / 2f, 0f)
    lineTo(0f, size.height / 2f)
    lineTo(size.width / 2f, size.height)
    lineTo(size.width, size.height / 2f)
    close()
}
