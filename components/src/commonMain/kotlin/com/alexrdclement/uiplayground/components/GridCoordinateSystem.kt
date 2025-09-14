package com.alexrdclement.uiplayground.components

import androidx.compose.ui.unit.Dp

enum class GridCoordinateSystemType {
    Cartesian,
    Polar,
}

sealed class GridCoordinateSystem {
    data class Cartesian(
        val scaleX: GridScale,
        val scaleY: GridScale,
        val rotationDegrees: Float = 0f,
    ) : GridCoordinateSystem() {
        constructor(
            spacing: Dp,
            rotationDegrees: Float = 0f,
        ) : this(
            scaleX = GridScale.Linear(spacing = spacing),
            scaleY = GridScale.Linear(spacing = spacing),
            rotationDegrees = rotationDegrees,
        )
    }

    data class Polar(
        val radiusScale: GridScale,
        val thetaRadians: Float,
        val rotationDegrees: Float = 0f,
    ) : GridCoordinateSystem()
}
