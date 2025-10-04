package com.alexrdclement.uiplayground.theme

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
    data class Rectangle(
        override val cornerRadius: Dp = 0.dp,
    ) : Shape(type = ShapeType.Rectangle)

    object Circle : Shape(type = ShapeType.Circle)
    object Triangle : Shape(type = ShapeType.Triangle)
    object Diamond : Shape(type = ShapeType.Diamond)

    open val cornerRadius
        get() = if (this is Rectangle) this.cornerRadius else 0.dp
}

fun ShapeType.toShape(
    cornerRadius: Dp = 0.dp,
): Shape {
    return when (this) {
        ShapeType.Rectangle -> Shape.Rectangle(cornerRadius = cornerRadius)
        ShapeType.Circle -> Shape.Circle
        ShapeType.Triangle -> Shape.Triangle
        ShapeType.Diamond -> Shape.Diamond
    }
}

fun Shape.toComposeShape(): androidx.compose.ui.graphics.Shape {
    return when (this) {
        is Shape.Rectangle -> RoundedCornerShape(size = cornerRadius)
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
