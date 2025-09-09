package com.alexrdclement.uiplayground.app.demo.components.demo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.components.Grid
import com.alexrdclement.uiplayground.components.GridCoordinateSystem
import com.alexrdclement.uiplayground.components.GridLineStyle
import com.alexrdclement.uiplayground.components.GridScale
import com.alexrdclement.uiplayground.components.GridVertex
import com.alexrdclement.uiplayground.components.Surface
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.reflect.KClass

@Composable
fun GridDemo(
    modifier: Modifier = Modifier,
    state: GridDemoState = rememberGridDemoState(),
    control: GridDemoControl = rememberGridDemoControl(state),
) {
    Demo(
        controls = control.controls,
        modifier = modifier.fillMaxSize(),
    ) {
        Grid(
            lineStyle = state.lineStyle.takeIf { state.showLines },
            vertex = state.vertex,
            coordinateSystem = state.coordinateSystem,
            offset = with(LocalDensity.current) {
                Offset(state.offsetX.toPx(), state.offsetY.toPx())
            },
            clipToBounds = state.clipToBounds,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun rememberGridDemoState(
    color: Color = PlaygroundTheme.colorScheme.primary,
    density: Density = LocalDensity.current,
): GridDemoState = remember {
    GridDemoState(
        color = color,
        density = density,
    )
}

@Stable
class GridDemoState(
    color: Color,
    density: Density,
) {
    val color by mutableStateOf(color)

    var gridSpacingX by mutableStateOf(100.dp)
    var gridSpacingY by mutableStateOf(100.dp)

    var gridScaleX: GridScale by mutableStateOf(GridScale.Linear(gridSpacingX))
    var gridScaleY: GridScale by mutableStateOf(GridScale.Linear(gridSpacingY))
    var gridScaleXBase: Float by mutableStateOf(2f)
    var gridScaleYBase: Float by mutableStateOf(2f)
    var gridScaleXExponent: Float by mutableStateOf(2f)
    var gridScaleYExponent: Float by mutableStateOf(2f)

    var radiusSpacing by mutableStateOf(50.dp)
    var radiusScaleBase: Float by mutableStateOf(2f)
    var radiusScaleExponent: Float by mutableStateOf(2f)
    var radiusScale: GridScale by mutableStateOf(GridScale.Linear(radiusSpacing))

    var rotationDegrees by mutableStateOf(0f)

    var theta by mutableStateOf((PI / 3f).toFloat())

    val coordinateSystems = mapOf(
        GridCoordinateSystem.Cartesian::class to "Cartesian",
        GridCoordinateSystem.Polar::class to "Polar",
    )
    val initialCoordinateSystem = GridCoordinateSystem.Cartesian(
        scaleX = gridScaleX,
        scaleY = gridScaleY,
        rotationDegrees = rotationDegrees,
    )
    var coordinateSystem: GridCoordinateSystem by mutableStateOf(initialCoordinateSystem)

    var strokeWidthPx by mutableStateOf(1f)
    val strokeWidth = with(density) { strokeWidthPx.toDp() }

    val initialLineStyle = GridLineStyle(
        color = color,
        stroke = Stroke(width = strokeWidthPx),
    )
    var lineStyle: GridLineStyle by mutableStateOf(initialLineStyle)

    var showLines by mutableStateOf(true)

    var vertexDrawStyle by mutableStateOf<DrawStyle>(Stroke(width = strokeWidthPx))
    var vertexWidth by mutableStateOf(10.dp)
    var vertexHeight by mutableStateOf(10.dp)
    val vertexSize = DpSize(vertexWidth, vertexHeight)
    var vertexRotationDegrees by mutableStateOf(0f)

    var vertex by mutableStateOf<GridVertex?>(null)

    var offsetX by mutableStateOf(0.dp)
    var offsetY by mutableStateOf(0.dp)

    var clipToBounds by mutableStateOf(true)
}

@Composable
fun rememberGridDemoControl(
    state: GridDemoState,
): GridDemoControl = remember(state) { GridDemoControl(state) }

@Stable
class GridDemoControl(
    val state: GridDemoState,
) {
    val coordinateSystemControl
        get() = Control.Dropdown(
            name = "Coordinate System",
            values = state.coordinateSystems.map { (kclass, name) ->
                Control.Dropdown.DropdownItem(
                    name = name,
                    value = kclass,
                )
            }.toPersistentList(),
            selectedIndex = state.coordinateSystems.keys.indexOf(state.coordinateSystem::class),
            onValueChange = { index ->
                state.coordinateSystem = when (state.coordinateSystems.keys.elementAt(index)) {
                    GridCoordinateSystem.Cartesian::class -> GridCoordinateSystem.Cartesian(
                        scaleX = state.gridScaleX,
                        scaleY = state.gridScaleY,
                        rotationDegrees = state.rotationDegrees,
                    )

                    GridCoordinateSystem.Polar::class -> GridCoordinateSystem.Polar(
                        radiusScale = state.radiusScale,
                        theta = state.theta,
                        rotationDegrees = state.rotationDegrees,
                    )

                    else -> state.initialCoordinateSystem
                }
            }
        )

    val gridSpacingXControl
        get() = Control.Slider(
            name = "Spacing X",
            value = state.gridSpacingX.value,
            onValueChange = {
                state.gridSpacingX = it.dp
                state.gridScaleX = when (val gridScaleX = state.gridScaleX) {
                    is GridScale.Linear -> gridScaleX.copy(state.gridSpacingX)
                    is GridScale.Logarithmic -> gridScaleX.copy(spacing = state.gridSpacingX)
                    is GridScale.LogarithmicDecay -> gridScaleX.copy(spacing = state.gridSpacingX)
                    is GridScale.Exponential -> gridScaleX.copy(spacing = state.gridSpacingX)
                    is GridScale.ExponentialDecay -> gridScaleX.copy(spacing = state.gridSpacingX)
                }
                state.gridScaleY = when (val gridScaleY = state.gridScaleY) {
                    is GridScale.Linear -> gridScaleY.copy(spacing = state.gridSpacingX)
                    is GridScale.Logarithmic -> gridScaleY.copy(spacing = state.gridSpacingX)
                    is GridScale.LogarithmicDecay -> gridScaleY.copy(spacing = state.gridSpacingX)
                    is GridScale.Exponential -> gridScaleY.copy(spacing = state.gridSpacingX)
                    is GridScale.ExponentialDecay -> gridScaleY.copy(spacing = state.gridSpacingX)
                }
                state.coordinateSystem = when (val coordinateSystem = state.coordinateSystem) {
                    is GridCoordinateSystem.Cartesian -> coordinateSystem.copy(
                        scaleX = state.gridScaleX,
                    )

                    is GridCoordinateSystem.Polar -> coordinateSystem
                }
            },
            valueRange = 0f..200f,
        )
    val gridSpacingYControl
        get() = Control.Slider(
            name = "Spacing Y",
            value = state.gridSpacingY.value,
            onValueChange = {
                state.gridSpacingY = it.dp
                state.gridScaleY = when (val gridScaleY = state.gridScaleY) {
                    is GridScale.Linear -> gridScaleY.copy(state.gridSpacingY)
                    is GridScale.Logarithmic -> gridScaleY.copy(spacing = state.gridSpacingY)
                    is GridScale.LogarithmicDecay -> gridScaleY.copy(spacing = state.gridSpacingY)
                    is GridScale.Exponential -> gridScaleY.copy(spacing = state.gridSpacingY)
                    is GridScale.ExponentialDecay -> gridScaleY.copy(spacing = state.gridSpacingY)
                }
                state.gridScaleY = when (val gridScaleY = state.gridScaleY) {
                    is GridScale.Linear -> gridScaleY.copy(spacing = state.gridSpacingY)
                    is GridScale.Logarithmic -> gridScaleY.copy(spacing = state.gridSpacingY)
                    is GridScale.LogarithmicDecay -> gridScaleY.copy(spacing = state.gridSpacingY)
                    is GridScale.Exponential -> gridScaleY.copy(spacing = state.gridSpacingY)
                    is GridScale.ExponentialDecay -> gridScaleY.copy(spacing = state.gridSpacingY)
                }
                state.coordinateSystem = when (val coordinateSystem = state.coordinateSystem) {
                    is GridCoordinateSystem.Cartesian -> coordinateSystem.copy(
                        scaleY = state.gridScaleY,
                    )

                    else -> coordinateSystem
                }
            },
            valueRange = 0f..200f,
        )

    val gridScales = listOf(
        GridScale.Linear::class to "Linear",
        GridScale.Logarithmic::class to "Logarithmic",
        GridScale.LogarithmicDecay::class to "Logarithmic Decay",
        GridScale.Exponential::class to "Exponential",
        GridScale.ExponentialDecay::class to "Exponential Decay",
    )
    val gridScaleXControl
        get() = Control.Dropdown(
            name = "Grid Scale X",
            values = gridScales.map { (kclass, name) ->
                Control.Dropdown.DropdownItem(
                    name = name,
                    value = kclass,
                )
            }.toPersistentList(),
            selectedIndex = gridScales.indexOfFirst { it.first == state.gridScaleX::class },
            onValueChange = { index ->
                state.gridScaleX = when (gridScales[index].first) {
                    GridScale.Linear::class -> GridScale.Linear(
                        spacing = state.gridSpacingX,
                    )

                    GridScale.Logarithmic::class -> GridScale.Logarithmic(
                        spacing = state.gridSpacingX,
                        base = state.gridScaleXBase,
                    )

                    GridScale.LogarithmicDecay::class -> GridScale.LogarithmicDecay(
                        spacing = state.gridSpacingX,
                        base = state.gridScaleXBase,
                    )

                    GridScale.Exponential::class -> GridScale.Exponential(
                        spacing = state.gridSpacingX,
                        exponent = state.gridScaleXExponent,
                    )

                    GridScale.ExponentialDecay::class -> GridScale.ExponentialDecay(
                        spacing = state.gridSpacingX,
                        exponent = state.gridScaleXExponent,
                    )

                    else -> GridScale.Linear(state.gridSpacingX)
                }
                state.coordinateSystem = when (val coordinateSystem = state.coordinateSystem) {
                    is GridCoordinateSystem.Cartesian -> coordinateSystem.copy(
                        scaleX = state.gridScaleX,
                    )

                    is GridCoordinateSystem.Polar -> coordinateSystem
                }
            }
        )
    val gridScaleYControl
        get() = Control.Dropdown(
            name = "Grid Scale Y",
            values = gridScales.map { (kclass, name) ->
                Control.Dropdown.DropdownItem(
                    name = name,
                    value = kclass,
                )
            }.toPersistentList(),
            selectedIndex = gridScales.indexOfFirst { it.first == state.gridScaleY::class },
            onValueChange = { index ->
                state.gridScaleY = when (gridScales[index].first) {
                    GridScale.Linear::class -> GridScale.Linear(
                        spacing = state.gridSpacingY,
                    )

                    GridScale.Logarithmic::class -> GridScale.Logarithmic(
                        spacing = state.gridSpacingY,
                        base = state.gridScaleYBase,
                    )

                    GridScale.LogarithmicDecay::class -> GridScale.LogarithmicDecay(
                        spacing = state.gridSpacingY,
                        base = state.gridScaleYBase,
                    )

                    GridScale.Exponential::class -> GridScale.Exponential(
                        spacing = state.gridSpacingY,
                        exponent = state.gridScaleYExponent,
                    )

                    GridScale.ExponentialDecay::class -> GridScale.ExponentialDecay(
                        spacing = state.gridSpacingY,
                        exponent = state.gridScaleYExponent,
                    )

                    else -> GridScale.Linear(state.gridSpacingY)
                }
                state.coordinateSystem = when (val coordinateSystem = state.coordinateSystem) {
                    is GridCoordinateSystem.Cartesian -> coordinateSystem.copy(
                        scaleY = state.gridScaleY,
                    )

                    is GridCoordinateSystem.Polar -> coordinateSystem
                }
            }
        )
    val scaleBaseRange = 1f..10f
    val gridScaleXBaseControl
        get() = Control.Slider(
            name = "Grid Scale X Log Base",
            value = logToLinearScale(state.gridScaleXBase, scaleBaseRange),
            onValueChange = {
                state.gridScaleXBase = linearToLogScale(it, scaleBaseRange)
                state.gridScaleX = when (val gridScaleX = state.gridScaleX) {
                    is GridScale.Logarithmic -> gridScaleX.copy(base = state.gridScaleXBase)
                    is GridScale.LogarithmicDecay -> gridScaleX.copy(base = state.gridScaleXBase)
                    else -> gridScaleX
                }
                state.coordinateSystem = when (val coordinateSystem = state.coordinateSystem) {
                    is GridCoordinateSystem.Cartesian -> coordinateSystem.copy(
                        scaleX = state.gridScaleX,
                    )

                    is GridCoordinateSystem.Polar -> coordinateSystem
                }
            },
            valueRange = scaleBaseRange,
        )
    val gridScaleYBaseControl
        get() = Control.Slider(
            name = "Grid Scale Y Log Base",
            value = logToLinearScale(state.gridScaleYBase, scaleBaseRange),
            onValueChange = {
                state.gridScaleYBase = linearToLogScale(it, scaleBaseRange)
                state.gridScaleY = when (val gridScaleY = state.gridScaleY) {
                    is GridScale.Logarithmic -> gridScaleY.copy(base = state.gridScaleYBase)
                    is GridScale.LogarithmicDecay -> gridScaleY.copy(base = state.gridScaleYBase)
                    else -> gridScaleY
                }
                state.coordinateSystem = when (val coordinateSystem = state.coordinateSystem) {
                    is GridCoordinateSystem.Cartesian -> coordinateSystem.copy(
                        scaleX = state.gridScaleX,
                        scaleY = state.gridScaleY,
                    )

                    is GridCoordinateSystem.Polar -> coordinateSystem
                }
            },
            valueRange = scaleBaseRange,
        )
    val radiusScaleBaseControl
        get() = Control.Slider(
            name = "Radius Scale Log Base",
            value = logToLinearScale(state.radiusScaleBase, scaleBaseRange),
            onValueChange = {
                state.radiusScaleBase = linearToLogScale(it, scaleBaseRange)
                state.radiusScale = when (val radiusScale = state.radiusScale) {
                    is GridScale.Logarithmic -> radiusScale.copy(base = state.radiusScaleBase)
                    is GridScale.LogarithmicDecay -> radiusScale.copy(base = state.radiusScaleBase)
                    else -> radiusScale
                }
                state.coordinateSystem = when (val coordinateSystem = state.coordinateSystem) {
                    is GridCoordinateSystem.Cartesian -> coordinateSystem
                    is GridCoordinateSystem.Polar -> coordinateSystem.copy(
                        radiusScale = state.radiusScale,
                    )
                }
            },
            valueRange = scaleBaseRange,
        )

    val scaleExponentRange = 1.001f..10f
    val gridScaleXExponentControl
        get() = Control.Slider(
            name = "Grid Scale X Exponent",
            value = logToLinearScale(state.gridScaleXExponent, scaleExponentRange),
            onValueChange = {
                state.gridScaleXExponent = linearToLogScale(it, scaleExponentRange)
                state.gridScaleX = when (val gridScaleX = state.gridScaleX) {
                    is GridScale.Exponential -> gridScaleX.copy(exponent = state.gridScaleXExponent)
                    is GridScale.ExponentialDecay -> gridScaleX.copy(exponent = state.gridScaleXExponent)
                    else -> gridScaleX
                }
                state.coordinateSystem = when (val coordinateSystem = state.coordinateSystem) {
                    is GridCoordinateSystem.Cartesian -> coordinateSystem.copy(
                        scaleX = state.gridScaleX,
                    )

                    is GridCoordinateSystem.Polar -> coordinateSystem
                }
            },
            valueRange = scaleExponentRange,
        )
    val gridScaleYExponentControl
        get() = Control.Slider(
            name = "Grid Scale Y Exponent",
            value = logToLinearScale(state.gridScaleYExponent, scaleExponentRange),
            onValueChange = {
                state.gridScaleYExponent = linearToLogScale(it, scaleExponentRange)
                state.gridScaleY = when (val gridScaleY = state.gridScaleY) {
                    is GridScale.Exponential -> gridScaleY.copy(exponent = state.gridScaleYExponent)
                    is GridScale.ExponentialDecay -> gridScaleY.copy(exponent = state.gridScaleYExponent)
                    else -> gridScaleY
                }
                state.coordinateSystem = when (val coordinateSystem = state.coordinateSystem) {
                    is GridCoordinateSystem.Cartesian -> coordinateSystem.copy(
                        scaleY = state.gridScaleY,
                    )

                    is GridCoordinateSystem.Polar -> coordinateSystem
                }
            },
            valueRange = scaleExponentRange,
        )
    val radiusScaleExponentControl
        get() = Control.Slider(
            name = "Radius Scale Exponent",
            value = logToLinearScale(state.radiusScaleExponent, scaleExponentRange),
            onValueChange = {
                state.radiusScaleExponent = linearToLogScale(it, scaleExponentRange)
                state.radiusScale = when (val radiusScale = state.radiusScale) {
                    is GridScale.Exponential -> radiusScale.copy(exponent = state.radiusScaleExponent)
                    is GridScale.ExponentialDecay -> radiusScale.copy(exponent = state.radiusScaleExponent)
                    else -> radiusScale
                }
                state.coordinateSystem = when (val coordinateSystem = state.coordinateSystem) {
                    is GridCoordinateSystem.Cartesian -> coordinateSystem
                    is GridCoordinateSystem.Polar -> coordinateSystem.copy(
                        radiusScale = state.radiusScale,
                    )
                }
            },
            valueRange = scaleExponentRange,
        )

    val thetaRange = (0.01f..(PI * 2f).toFloat())
    val thetaControl
        get() = Control.Slider(
            name = "Theta",
            value = logToLinearScale(state.theta, thetaRange),
            onValueChange = {
                state.theta = linearToLogScale(it, thetaRange)
                state.coordinateSystem = when (val coordinateSystem = state.coordinateSystem) {
                    is GridCoordinateSystem.Cartesian -> coordinateSystem
                    is GridCoordinateSystem.Polar -> coordinateSystem.copy(
                        theta = state.theta,
                    )
                }
            },
            valueRange = thetaRange,
        )

    val radiusSpacingControl
        get() = Control.Slider(
            name = "Radius Spacing",
            value = state.radiusSpacing.value,
            onValueChange = {
                state.radiusSpacing = it.dp
                state.radiusScale = when (val radiusScale = state.radiusScale) {
                    is GridScale.Linear -> radiusScale.copy(spacing = state.radiusSpacing)
                    is GridScale.Logarithmic -> radiusScale.copy(spacing = state.radiusSpacing)
                    is GridScale.LogarithmicDecay -> radiusScale.copy(spacing = state.radiusSpacing)
                    is GridScale.Exponential -> radiusScale.copy(spacing = state.radiusSpacing)
                    is GridScale.ExponentialDecay -> radiusScale.copy(spacing = state.radiusSpacing)
                }
                state.coordinateSystem = when (val coordinateSystem = state.coordinateSystem) {
                    is GridCoordinateSystem.Cartesian -> coordinateSystem
                    is GridCoordinateSystem.Polar -> coordinateSystem.copy(
                        radiusScale = state.radiusScale,
                    )
                }
            },
            valueRange = 0f..200f,
        )

    val radiusScaleControl
            get() = Control.Dropdown(
            name = "Radius Scale",
            values = gridScales.map { (kclass, name) ->
                Control.Dropdown.DropdownItem(
                    name = name,
                    value = kclass,
                )
            }.toPersistentList(),
            selectedIndex = gridScales.indexOfFirst { it.first == state.radiusScale::class },
            onValueChange = { index ->
                state.radiusScale = when (gridScales[index].first) {
                    GridScale.Linear::class -> GridScale.Linear(
                        spacing = state.radiusSpacing,
                    )

                    GridScale.Logarithmic::class -> GridScale.Logarithmic(
                        spacing = state.radiusSpacing,
                        base = state.radiusScaleBase,
                    )

                    GridScale.LogarithmicDecay::class -> GridScale.LogarithmicDecay(
                        spacing = state.radiusSpacing,
                        base = state.radiusScaleBase,
                    )

                    GridScale.Exponential::class -> GridScale.Exponential(
                        spacing = state.radiusSpacing,
                        exponent = state.radiusScaleExponent,
                    )

                    GridScale.ExponentialDecay::class -> GridScale.ExponentialDecay(
                        spacing = state.radiusSpacing,
                        exponent = state.radiusScaleExponent,
                    )

                    else -> GridScale.Linear(state.radiusSpacing)
                }
                state.coordinateSystem = when (val coordinateSystem = state.coordinateSystem) {
                    is GridCoordinateSystem.Cartesian -> coordinateSystem
                    is GridCoordinateSystem.Polar -> coordinateSystem.copy(
                        radiusScale = state.radiusScale,
                    )
                }
            }
        )

    val rotationDegreesControl
        get() = Control.Slider(
            name = "Grid Rotation",
            value = state.rotationDegrees,
            onValueChange = {
                state.rotationDegrees = it
                state.coordinateSystem = when (val coordinateSystem = state.coordinateSystem) {
                    is GridCoordinateSystem.Cartesian -> coordinateSystem.copy(
                        rotationDegrees = state.rotationDegrees,
                    )

                    is GridCoordinateSystem.Polar -> coordinateSystem.copy(
                        rotationDegrees = state.rotationDegrees,
                    )
                }
            },
            valueRange = 0f..360f,
        )

    val showLinesControl
        get() = Control.Toggle(
            name = "Show Grid Lines",
            value = state.showLines,
            onValueChange = { state.showLines = it },
        )

    val vertexItems = mapOf(
        null to "None",
        GridVertex.Oval::class to "Oval",
        GridVertex.Rect::class to "Rect",
        GridVertex.Plus::class to "Plus",
        GridVertex.X::class to "X",
    )
    val vertexControl
        get() = Control.Dropdown(
            name = "Vertex",
            values = vertexItems.map { (kclass, name) ->
                Control.Dropdown.DropdownItem(
                    name = name,
                    value = kclass,
                )
            }.toPersistentList(),
            selectedIndex = vertexItems.keys.indexOf<KClass<out Any>?>(state.vertex?.let { it::class })
                .coerceAtLeast(0), // -1 to null
            onValueChange = { index ->
                state.vertex = when (vertexItems.keys.elementAt(index)) {
                    GridVertex.Oval::class -> GridVertex.Oval(
                        color = state.color,
                        size = state.vertexSize,
                        drawStyle = state.vertexDrawStyle,
                        rotationDegrees = state.vertexRotationDegrees,
                    )

                    GridVertex.Rect::class -> GridVertex.Rect(
                        color = state.color,
                        size = state.vertexSize,
                        drawStyle = state.vertexDrawStyle,
                        rotationDegrees = state.vertexRotationDegrees,
                    )

                    GridVertex.Plus::class -> GridVertex.Plus(
                        color = state.color,
                        size = state.vertexSize,
                        strokeWidth = state.strokeWidth,
                        rotationDegrees = state.vertexRotationDegrees,
                    )

                    GridVertex.X::class -> GridVertex.X(
                        color = state.color,
                        size = state.vertexSize,
                        strokeWidth = state.strokeWidth,
                        rotationDegrees = state.vertexRotationDegrees,
                    )

                    else -> null
                }
            }
        )

    val vertexDrawStyles = mapOf(
        Stroke::class to "Stroke",
        Fill::class to "Fill",
    )
    val vertexDrawStyleControl
        get() = Control.Dropdown(
            name = "Vertex Draw Style",
            values = vertexDrawStyles.map { (kclass, name) ->
                Control.Dropdown.DropdownItem(
                    name = name,
                    value = kclass,
                )
            }.toPersistentList(),
            selectedIndex = vertexDrawStyles.keys.indexOf(state.vertexDrawStyle::class),
            onValueChange = { index ->
                state.vertexDrawStyle = when (vertexDrawStyles.keys.elementAt(index)) {
                    Stroke::class -> Stroke(width = state.strokeWidthPx)
                    Fill::class -> Fill
                    else -> Stroke(width = state.strokeWidthPx)
                }
                state.vertex = when (val vertex = state.vertex) {
                    is GridVertex.Oval -> vertex.copy(
                        drawStyle = state.vertexDrawStyle,
                    )

                    is GridVertex.Rect -> vertex.copy(
                        drawStyle = state.vertexDrawStyle,
                    )

                    is GridVertex.Plus,
                    is GridVertex.X,
                    -> vertex

                    null -> null
                }
            }
        )

    val vertexRotationControl
        get() = Control.Slider(
            name = "Vertex Rotation",
            value = state.vertexRotationDegrees,
            onValueChange = {
                state.vertexRotationDegrees = it
                state.vertex = when (val vertex = state.vertex) {
                    is GridVertex.Oval -> vertex.copy(
                        rotationDegrees = state.vertexRotationDegrees,
                    )

                    is GridVertex.Rect -> vertex.copy(
                        rotationDegrees = state.vertexRotationDegrees,
                    )

                    is GridVertex.Plus -> vertex.copy(
                        rotationDegrees = state.vertexRotationDegrees,
                    )

                    is GridVertex.X -> vertex.copy(
                        rotationDegrees = state.vertexRotationDegrees,
                    )

                    null -> null
                }
            },
            valueRange = 0f..360f,
        )

    val strokeWidthControl
        get() = Control.Slider(
            name = "Line Stroke Width",
            value = state.strokeWidthPx,
            onValueChange = {
                state.strokeWidthPx = it
                state.vertexDrawStyle = Stroke(width = state.strokeWidthPx)
                state.lineStyle = state.lineStyle.copy(
                    stroke = Stroke(width = state.strokeWidthPx),
                )
                state.vertex = when (val vertex = state.vertex) {
                    is GridVertex.Oval -> vertex.copy(
                        drawStyle = state.vertexDrawStyle,
                    )

                    is GridVertex.Rect -> vertex.copy(
                        drawStyle = state.vertexDrawStyle,
                    )

                    is GridVertex.Plus -> vertex.copy(
                        strokeWidth = state.strokeWidth,
                    )

                    is GridVertex.X -> vertex.copy(
                        strokeWidth = state.strokeWidth,
                    )

                    null -> null
                }
            },
            valueRange = 1f..100f,
        )

    val vertexSizeControl
        get() = Control.Slider(
            name = "Vertex Size",
            value = (state.vertexWidth.value + state.vertexHeight.value) / 2f,
            onValueChange = {
                state.vertexWidth = it.dp
                state.vertexHeight = it.dp
                state.vertex = when (val vertex = state.vertex) {
                    is GridVertex.Oval -> vertex.copy(
                        size = DpSize(state.vertexWidth, state.vertexHeight),
                    )

                    is GridVertex.Rect -> vertex.copy(
                        size = DpSize(state.vertexWidth, state.vertexHeight),
                    )

                    is GridVertex.Plus -> vertex.copy(
                        size = DpSize(state.vertexWidth, state.vertexHeight),
                    )

                    is GridVertex.X -> vertex.copy(
                        size = DpSize(state.vertexWidth, state.vertexHeight),
                    )

                    null -> null
                }
            },
            valueRange = 1f..100f,
        )

    val vertexWidthControl
        get() = Control.Slider(
            name = "Vertex Width",
            value = state.vertexWidth.value,
            onValueChange = {
                state.vertexWidth = it.dp
                state.vertex = when (val vertex = state.vertex) {
                    is GridVertex.Oval -> vertex.copy(
                        size = DpSize(state.vertexWidth, state.vertexHeight),
                    )

                    is GridVertex.Rect -> vertex.copy(
                        size = DpSize(state.vertexWidth, state.vertexHeight),
                    )

                    is GridVertex.Plus -> vertex.copy(
                        size = DpSize(state.vertexWidth, state.vertexHeight),
                    )

                    is GridVertex.X -> vertex.copy(
                        size = DpSize(state.vertexWidth, state.vertexHeight),
                    )

                    null -> null
                }
            },
            valueRange = 1f..100f,
        )
    val vertexHeightControl
        get() = Control.Slider(
            name = "Vertex Height",
            value = state.vertexHeight.value,
            onValueChange = {
                state.vertexHeight = it.dp
                state.vertex = when (val vertex = state.vertex) {
                    is GridVertex.Oval -> vertex.copy(
                        size = DpSize(state.vertexWidth, state.vertexHeight),
                    )

                    is GridVertex.Rect -> vertex.copy(
                        size = DpSize(state.vertexWidth, state.vertexHeight),
                    )

                    is GridVertex.Plus -> vertex.copy(
                        size = DpSize(state.vertexWidth, state.vertexHeight),
                    )

                    is GridVertex.X -> vertex.copy(
                        size = DpSize(state.vertexWidth, state.vertexHeight),
                    )

                    null -> null
                }
            },
            valueRange = 1f..100f,
        )

    val offsetXControl
        get() = Control.Slider(
            name = "Offset X",
            value = state.offsetX.value,
            onValueChange = { state.offsetX = it.dp },
            valueRange = -2000f..2000f,
        )

    val offsetYControl
        get() = Control.Slider(
            name = "Offset Y",
            value = state.offsetY.value,
            onValueChange = { state.offsetY = it.dp },
            valueRange = -2000f..2000f,
        )

    val clipControl
        get() = Control.Toggle(
            name = "Clip to bounds",
            value = state.clipToBounds,
            onValueChange = { state.clipToBounds = it },
        )

    val gridScaleXControls
        get() = when (state.gridScaleX) {
            is GridScale.Exponential,
            is GridScale.ExponentialDecay,
            -> persistentListOf(
                gridScaleXControl,
                gridSpacingXControl,
                gridScaleXExponentControl,
            )
            is GridScale.Linear -> persistentListOf(
                gridScaleXControl,
                gridSpacingXControl,
            )
            is GridScale.Logarithmic,
            is GridScale.LogarithmicDecay,
            -> persistentListOf(
                gridScaleXControl,
                gridSpacingXControl,
                gridScaleXBaseControl,
            )
        }

    val gridScaleYControls
        get() = when (state.gridScaleY) {
            is GridScale.Exponential,
            is GridScale.ExponentialDecay,
            -> persistentListOf(
                gridScaleYControl,
                gridSpacingYControl,
                gridScaleYExponentControl,
            )
            is GridScale.Linear -> persistentListOf(
                gridScaleYControl,
                gridSpacingYControl,
            )
            is GridScale.Logarithmic,
            is GridScale.LogarithmicDecay,
            -> persistentListOf(
                gridScaleYControl,
                gridSpacingYControl,
                gridScaleYBaseControl,
            )
        }

    val cartesianControls
        get() = persistentListOf(
            *gridScaleXControls.toTypedArray(),
            *gridScaleYControls.toTypedArray(),
        )

    val radiusScaleControls
        get() = when (state.radiusScale) {
            is GridScale.Exponential,
            is GridScale.ExponentialDecay,
            -> persistentListOf(
                radiusScaleControl,
                radiusSpacingControl,
                radiusScaleExponentControl,
                thetaControl,
            )

            is GridScale.Linear -> persistentListOf(
                radiusScaleControl,
                radiusSpacingControl,
                thetaControl,
            )

            is GridScale.Logarithmic,
            is GridScale.LogarithmicDecay,
            -> persistentListOf(
                radiusScaleControl,
                radiusSpacingControl,
                radiusScaleBaseControl,
                thetaControl,
            )
        }

    val coordinateSystemControls: PersistentList<Control>
        get() {
            val scaleControls = when (state.coordinateSystem) {
                is GridCoordinateSystem.Cartesian -> cartesianControls
                is GridCoordinateSystem.Polar -> radiusScaleControls
            }
            return persistentListOf(
                coordinateSystemControl,
                *scaleControls.toTypedArray()
            )
        }

    val vertexControls
        get() = persistentListOf(
            vertexControl,
            vertexDrawStyleControl,
            vertexSizeControl,
            vertexWidthControl,
            vertexHeightControl,
            vertexRotationControl,
        )

    val controls
        get() = persistentListOf(
            *coordinateSystemControls.toTypedArray(),
            showLinesControl,
            strokeWidthControl,
            *vertexControls.toTypedArray(),
            offsetXControl,
            offsetYControl,
            rotationDegreesControl,
            clipControl,
        )
}

@Preview
@Composable
fun GridDemoPreview() {
    PlaygroundTheme {
        Surface {
            GridDemo()
        }
    }
}
