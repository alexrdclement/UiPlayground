package com.alexrdclement.uiplayground.components

import androidx.compose.ui.unit.Dp
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.components.util.restore
import com.alexrdclement.uiplayground.components.util.save

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
        val thetaRadians: Float, // angle step in radians
        val rotationDegrees: Float = 0f,
    ) : GridCoordinateSystem()
}

private const val coordinateSystemTypeKey = "coordinateSystemType"
private const val scaleXKey = "scaleX"
private const val scaleYKey = "scaleY"
private const val radiusScaleKey = "radiusScale"
private const val thetaKey = "theta"
private const val rotationDegreesKey = "rotationDegrees"

private enum class CoordinateSystemType {
    Cartesian,
    Polar,
}

val GridCoordinateSystemSaver = mapSaverSafe(
    save = { value ->
        when (value) {
            is GridCoordinateSystem.Cartesian -> mapOf(
                coordinateSystemTypeKey to CoordinateSystemType.Cartesian,
                scaleXKey to save(value.scaleX, GridScaleSaver, this),
                scaleYKey to save(value.scaleY, GridScaleSaver, this),
                rotationDegreesKey to value.rotationDegrees,
            )
            is GridCoordinateSystem.Polar -> mapOf(
                coordinateSystemTypeKey to CoordinateSystemType.Polar,
                radiusScaleKey to save(value.radiusScale, GridScaleSaver, this),
                thetaKey to value.thetaRadians,
                rotationDegreesKey to value.rotationDegrees,
            )
        }
    },
    restore = { map ->
        when (map[coordinateSystemTypeKey]) {
            CoordinateSystemType.Cartesian -> GridCoordinateSystem.Cartesian(
                scaleX = restore(map[scaleXKey], GridScaleSaver)!!,
                scaleY = restore(map[scaleYKey], GridScaleSaver)!!,
                rotationDegrees = map[rotationDegreesKey] as Float,
            )
            CoordinateSystemType.Polar -> GridCoordinateSystem.Polar(
                radiusScale = restore(map[radiusScaleKey], GridScaleSaver)!!,
                thetaRadians = map[thetaKey] as Float,
                rotationDegrees = map[rotationDegreesKey] as Float,
            )
            else -> throw IllegalArgumentException("Unknown type: ${map[coordinateSystemTypeKey]}")
        }
    }
)
