package com.alexrdclement.uiplayground.app.demo.components.demo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.util.DensitySaver
import com.alexrdclement.uiplayground.components.Grid
import com.alexrdclement.uiplayground.components.GridCoordinateSystem
import com.alexrdclement.uiplayground.components.GridCoordinateSystemSaver
import com.alexrdclement.uiplayground.components.GridLineStyle
import com.alexrdclement.uiplayground.components.GridLineStyleSaver
import com.alexrdclement.uiplayground.components.GridScale
import com.alexrdclement.uiplayground.components.GridScaleSaver
import com.alexrdclement.uiplayground.components.GridVertex
import com.alexrdclement.uiplayground.components.GridVertexSaver
import com.alexrdclement.uiplayground.components.Surface
import com.alexrdclement.uiplayground.components.util.ColorSaver
import com.alexrdclement.uiplayground.components.util.DrawStyleSaver
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.components.util.restore
import com.alexrdclement.uiplayground.components.util.save
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
): GridDemoState = rememberSaveable(
    saver = GridDemoStateSaver,
) {
    GridDemoState(
        color = color,
        density = density,
    )
}

@Stable
class GridDemoState(
    color: Color,
    density: Density,
    xGridScaleStateInitial: CartesianGridScaleState = CartesianGridScaleState(),
    yGridScaleStateInitial: CartesianGridScaleState = CartesianGridScaleState(),
    polarGridScaleStateInitial: PolarGridScaleState = PolarGridScaleState(
        gridSpacingInitial = 50.dp,
        thetaRadiansInitial = (PI / 3f).toFloat(),
    ),
    rotationDegreesInitial: Float = 0f,
    coordinateSystemInitial: GridCoordinateSystem = GridCoordinateSystem.Cartesian(
        scaleX = xGridScaleStateInitial.gridScale,
        scaleY = yGridScaleStateInitial.gridScale,
        rotationDegrees = rotationDegreesInitial,
    ),
    lineStyleInitial: GridLineStyle = GridLineStyle(
        color = color,
        stroke = Stroke(width = 1f),
    ),
    showLinesInitial: Boolean = true,
    vertexStateInitial: GridVertexState = GridVertexState(
        vertexInitial = null,
        drawStyleInitial = Stroke(width = 1f),
        widthInitial = 10.dp,
        heightInitial = 10.dp,
        rotationDegreesInitial = 0f,
    ),
    strokeWidthPxInitial: Float = 1f,
    offsetXInitial: Dp = 0.dp,
    offsetYInitial: Dp = 0.dp,
    clipToBoundsInitial: Boolean = true,
) {
    val color by mutableStateOf(color)
    val density by mutableStateOf(density)

    var gridSpacingX by mutableStateOf(xGridScaleStateInitial.gridSpacing)
        internal set
    var gridScaleX: GridScale by mutableStateOf(xGridScaleStateInitial.gridScale)
        internal set
    var gridScaleXBase: Float by mutableStateOf(xGridScaleStateInitial.gridScaleBase)
        internal set
    var gridScaleXExponent: Float by mutableStateOf(xGridScaleStateInitial.gridScaleExponent)
        internal set

    var gridSpacingY by mutableStateOf(yGridScaleStateInitial.gridSpacing)
        internal set
    var gridScaleY: GridScale by mutableStateOf(yGridScaleStateInitial.gridScale)
        internal set
    var gridScaleYBase: Float by mutableStateOf(yGridScaleStateInitial.gridScaleBase)
        internal set
    var gridScaleYExponent: Float by mutableStateOf(yGridScaleStateInitial.gridScaleExponent)
        internal set

    var radiusSpacing by mutableStateOf(polarGridScaleStateInitial.gridSpacing)
        internal set
    var radiusScale: GridScale by mutableStateOf(polarGridScaleStateInitial.gridScale)
        internal set
    var radiusScaleBase: Float by mutableStateOf(polarGridScaleStateInitial.gridScaleBase)
        internal set
    var radiusScaleExponent: Float by mutableStateOf(polarGridScaleStateInitial.gridScaleExponent)
        internal set
    var thetaRadians by mutableStateOf(polarGridScaleStateInitial.thetaRadians)
        internal set

    var rotationDegrees by mutableStateOf(rotationDegreesInitial)
        internal set
    var coordinateSystem: GridCoordinateSystem by mutableStateOf(coordinateSystemInitial)
        internal set
    var strokeWidthPx by mutableStateOf(strokeWidthPxInitial)
        internal set
    var lineStyle: GridLineStyle by mutableStateOf(lineStyleInitial)
        internal set
    var showLines by mutableStateOf(showLinesInitial)
        internal set

    var vertex by mutableStateOf(vertexStateInitial.vertex)
        internal set
    var vertexDrawStyle by mutableStateOf(vertexStateInitial.drawStyle)
        internal set
    var vertexWidth by mutableStateOf(vertexStateInitial.width)
        internal set
    var vertexHeight by mutableStateOf(vertexStateInitial.height)
        internal set
    var vertexRotationDegrees by mutableStateOf(vertexStateInitial.rotationDegrees)
        internal set

    var offsetX by mutableStateOf(offsetXInitial)
        internal set
    var offsetY by mutableStateOf(offsetYInitial)
        internal set
    var clipToBounds by mutableStateOf(clipToBoundsInitial)
        internal set

    val strokeWidth = with(density) { strokeWidthPx.toDp() }
    val vertexSize = DpSize(vertexWidth, vertexHeight)
}

private const val colorKey = "color"
private const val densityKey = "density"
private const val xGridScaleStateKey = "xGridScaleState"
private const val yGridScaleStateKey = "yGridScaleState"
private const val radiusGridScaleStateKey = "radiusGridScaleState"
private const val rotationDegreesKey = "rotationDegrees"
private const val coordinateSystemKey = "coordinateSystem"
private const val lineStyleKey = "lineStyle"
private const val showLinesKey = "showLines"
private const val vertexStateKey = "vertexState"
private const val strokeWidthPxKey = "strokeWidthPx"
private const val offsetXKey = "offsetX"
private const val offsetYKey = "offsetY"
private const val clipToBoundsKey = "clipToBounds"

val GridDemoStateSaver = mapSaverSafe(
    save = { value ->
        mapOf(
            colorKey to save(value.color, ColorSaver, this),
            densityKey to save(value.density, DensitySaver, this),
            xGridScaleStateKey to save(
                value = CartesianGridScaleState(
                    gridSpacingInitial = value.gridSpacingX,
                    gridScaleInitial = value.gridScaleX,
                    gridScaleBaseInitial = value.gridScaleXBase,
                    gridScaleExponentInitial = value.gridScaleXExponent,
                ),
                saver = CartesianGridScaleStateSaver,
                scope = this,
            ),
            yGridScaleStateKey to save(
                value = CartesianGridScaleState(
                    gridSpacingInitial = value.gridSpacingY,
                    gridScaleInitial = value.gridScaleY,
                    gridScaleBaseInitial = value.gridScaleYBase,
                    gridScaleExponentInitial = value.gridScaleYExponent,
                ),
                saver = CartesianGridScaleStateSaver,
                scope = this,
            ),
            radiusGridScaleStateKey to save(
                value = PolarGridScaleState(
                    gridSpacingInitial = value.radiusSpacing,
                    gridScaleInitial = value.radiusScale,
                    gridScaleBaseInitial = value.radiusScaleBase,
                    gridScaleExponentInitial = value.radiusScaleExponent,
                    thetaRadiansInitial = value.thetaRadians,
                ),
                saver = PolarGridScaleStateSaver,
                scope = this,
            ),
            rotationDegreesKey to value.rotationDegrees,
            coordinateSystemKey to save(
                value.coordinateSystem,
                GridCoordinateSystemSaver,
                this
            ),
            lineStyleKey to save(value.lineStyle, GridLineStyleSaver, this),
            showLinesKey to value.showLines,
            vertexStateKey to save(
                value = GridVertexState(
                    vertexInitial = value.vertex,
                    drawStyleInitial = value.vertexDrawStyle,
                    widthInitial = value.vertexWidth,
                    heightInitial = value.vertexHeight,
                    rotationDegreesInitial = value.vertexRotationDegrees,
                ),
                saver = GridVertexStateSaver,
                scope = this,
            ),
            strokeWidthPxKey to value.strokeWidthPx,
            offsetXKey to value.offsetX.value,
            offsetYKey to value.offsetY.value,
            clipToBoundsKey to value.clipToBounds,
        )
    },
    restore = { value ->
        GridDemoState(
            color = restore(value[colorKey], ColorSaver)!!,
            density = restore(value[densityKey], DensitySaver)!!,
            xGridScaleStateInitial = restore(
                value = value[xGridScaleStateKey],
                saver = CartesianGridScaleStateSaver,
            )!!,
            yGridScaleStateInitial = restore(
                value = value[yGridScaleStateKey],
                saver = CartesianGridScaleStateSaver,
            )!!,
            polarGridScaleStateInitial = restore(
                value = value[radiusGridScaleStateKey],
                saver = PolarGridScaleStateSaver,
            )!!,
            coordinateSystemInitial = restore(
                value[coordinateSystemKey],
                GridCoordinateSystemSaver
            )!!,
            lineStyleInitial = restore(value[lineStyleKey], GridLineStyleSaver)!!,
            showLinesInitial = value[showLinesKey] as Boolean,
            vertexStateInitial = restore(value[vertexStateKey], GridVertexStateSaver)!!,
            strokeWidthPxInitial = value[strokeWidthPxKey] as Float,
            offsetXInitial = (value[offsetXKey] as Float).dp,
            offsetYInitial = (value[offsetYKey] as Float).dp,
            clipToBoundsInitial = value[clipToBoundsKey] as Boolean,
        )
    },
)

@Stable
class CartesianGridScaleState(
    gridSpacingInitial: Dp = 100.dp,
    gridScaleInitial: GridScale = GridScale.Linear(gridSpacingInitial),
    gridScaleBaseInitial: Float = 2f,
    gridScaleExponentInitial: Float = 2f,
) {
    var gridSpacing by mutableStateOf(gridSpacingInitial)
        internal set
    var gridScale: GridScale by mutableStateOf(gridScaleInitial)
        internal set
    var gridScaleBase: Float by mutableStateOf(gridScaleBaseInitial)
        internal set
    var gridScaleExponent: Float by mutableStateOf(gridScaleExponentInitial)
        internal set
}

private const val cartesianGridSpacingKey = "cartesianGridSpacing"
private const val cartesianGridScaleKey = "cartesianGridScale"
private const val cartesianGridScaleBaseKey = "cartesianGridScaleBase"
private const val cartesianGridScaleExponentKey = "cartesianGridScaleExponent"

val CartesianGridScaleStateSaver = mapSaverSafe(
    save = { value ->
        mapOf(
            cartesianGridSpacingKey to value.gridSpacing.value,
            cartesianGridScaleKey to save(value.gridScale, GridScaleSaver, this),
            cartesianGridScaleBaseKey to value.gridScaleBase,
            cartesianGridScaleExponentKey to value.gridScaleExponent,
        )
    },
    restore = { value ->
        CartesianGridScaleState(
            gridSpacingInitial = (value[cartesianGridSpacingKey] as Float).dp,
            gridScaleInitial = restore(value[cartesianGridScaleKey], GridScaleSaver)!!,
            gridScaleBaseInitial = value[cartesianGridScaleBaseKey] as Float,
            gridScaleExponentInitial = value[cartesianGridScaleExponentKey] as Float,
        )
    },
)

@Stable
class PolarGridScaleState(
    gridSpacingInitial: Dp = 100.dp,
    gridScaleInitial: GridScale = GridScale.Linear(gridSpacingInitial),
    gridScaleBaseInitial: Float = 2f,
    gridScaleExponentInitial: Float = 2f,
    thetaRadiansInitial: Float,
) {
    var gridSpacing by mutableStateOf(gridSpacingInitial)
        internal set
    var gridScale: GridScale by mutableStateOf(gridScaleInitial)
        internal set
    var gridScaleBase: Float by mutableStateOf(gridScaleBaseInitial)
        internal set
    var gridScaleExponent: Float by mutableStateOf(gridScaleExponentInitial)
        internal set
    var thetaRadians by mutableStateOf(thetaRadiansInitial)
        internal set
}

private const val polarGridSpacingKey = "polarGridSpacing"
private const val polarGridScaleKey = "polarGridScale"
private const val polarGridScaleBaseKey = "polarGridScaleBase"
private const val polarGridScaleExponentKey = "polarGridScaleExponent"
private const val polarGridThetaRadiansKey = "polarGridThetaRadians"

val PolarGridScaleStateSaver = mapSaverSafe(
    save = { value ->
        mapOf(
            polarGridSpacingKey to value.gridSpacing.value,
            polarGridScaleKey to save(value.gridScale, GridScaleSaver, this),
            polarGridScaleBaseKey to value.gridScaleBase,
            polarGridScaleExponentKey to value.gridScaleExponent,
            polarGridThetaRadiansKey to value.thetaRadians,
        )
    },
    restore = { value ->
        PolarGridScaleState(
            gridSpacingInitial = (value[polarGridSpacingKey] as Float).dp,
            gridScaleInitial = restore(value[polarGridScaleKey], GridScaleSaver)!!,
            gridScaleBaseInitial = value[polarGridScaleBaseKey] as Float,
            gridScaleExponentInitial = value[polarGridScaleExponentKey] as Float,
            thetaRadiansInitial = value[polarGridThetaRadiansKey] as Float,
        )
    },
)

private const val vertexKey = "vertex"
private const val vertexDrawStyleKey = "vertexDrawStyle"
private const val vertexWidthKey = "vertexWidth"
private const val vertexHeightKey = "vertexHeight"
private const val vertexRotationDegreesKey = "vertexRotationDegrees"

class GridVertexState(
    vertexInitial: GridVertex?,
    drawStyleInitial: DrawStyle,
    widthInitial: Dp,
    heightInitial: Dp,
    rotationDegreesInitial: Float,
) {
    var vertex by mutableStateOf(vertexInitial)
        internal set
    var drawStyle by mutableStateOf(drawStyleInitial)
        internal set
    var width by mutableStateOf(widthInitial)
        internal set
    var height by mutableStateOf(heightInitial)
        internal set
    var rotationDegrees by mutableStateOf(rotationDegreesInitial)
        internal set
}

val GridVertexStateSaver = mapSaverSafe(
    save = { value ->
        mapOf(
            vertexKey to save(value.vertex, GridVertexSaver, this),
            vertexDrawStyleKey to save(value.drawStyle, DrawStyleSaver, this),
            vertexWidthKey to value.width.value,
            vertexHeightKey to value.height.value,
            vertexRotationDegreesKey to value.rotationDegrees,
        )
    },
    restore = { value ->
        GridVertexState(
            vertexInitial = restore(value[vertexKey], GridVertexSaver),
            drawStyleInitial = restore(value[vertexDrawStyleKey], DrawStyleSaver)!!,
            widthInitial = (value[vertexWidthKey] as Float).dp,
            heightInitial = (value[vertexHeightKey] as Float).dp,
            rotationDegreesInitial = value[vertexRotationDegreesKey] as Float,
        )
    },
)

@Composable
fun rememberGridDemoControl(
    state: GridDemoState,
): GridDemoControl = remember(state) { GridDemoControl(state) }

@Stable
class GridDemoControl(
    val state: GridDemoState,
) {
    val coordinateSystems = mapOf(
        GridCoordinateSystem.Cartesian::class to "Cartesian",
        GridCoordinateSystem.Polar::class to "Polar",
    )
    val coordinateSystemControl = Control.Dropdown(
        name = "Coordinate System",
        values = {
            coordinateSystems.map { (kclass, name) ->
                Control.Dropdown.DropdownItem(
                    name = name,
                    value = kclass,
                )
            }.toPersistentList()
        },
        selectedIndex = { coordinateSystems.keys.indexOf(state.coordinateSystem::class) },
        onValueChange = { index ->
            state.coordinateSystem = when (coordinateSystems.keys.elementAt(index)) {
                GridCoordinateSystem.Cartesian::class -> GridCoordinateSystem.Cartesian(
                    scaleX = state.gridScaleX,
                    scaleY = state.gridScaleY,
                    rotationDegrees = state.rotationDegrees,
                )

                GridCoordinateSystem.Polar::class -> GridCoordinateSystem.Polar(
                    radiusScale = state.radiusScale,
                    theta = state.thetaRadians,
                    rotationDegrees = state.rotationDegrees,
                )

                else -> state.coordinateSystem
            }
        }
    )

    val gridSpacingXControl = Control.Slider(
        name = "Spacing X",
        value = { state.gridSpacingX.value },
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
        valueRange = { 0f..200f },
    )
    val gridSpacingYControl = Control.Slider(
        name = "Spacing Y",
        value = { state.gridSpacingY.value },
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
        valueRange = { 0f..200f },
    )

    val gridScales = listOf(
        GridScale.Linear::class to "Linear",
        GridScale.Logarithmic::class to "Logarithmic",
        GridScale.LogarithmicDecay::class to "Logarithmic Decay",
        GridScale.Exponential::class to "Exponential",
        GridScale.ExponentialDecay::class to "Exponential Decay",
    )
    val gridScaleXControl = Control.Dropdown(
        name = "Grid Scale X",
        values = {
            gridScales.map { (kclass, name) ->
                Control.Dropdown.DropdownItem(
                    name = name,
                    value = kclass,
                )
            }.toPersistentList()
        },
        selectedIndex = { gridScales.indexOfFirst { it.first == state.gridScaleX::class } },
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
    val gridScaleYControl = Control.Dropdown(
        name = "Grid Scale Y",
        values = {
            gridScales.map { (kclass, name) ->
                Control.Dropdown.DropdownItem(
                    name = name,
                    value = kclass,
                )
            }.toPersistentList()
        },
        selectedIndex = { gridScales.indexOfFirst { it.first == state.gridScaleY::class } },
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
    val gridScaleXBaseControl = Control.Slider(
        name = "Grid Scale X Log Base",
        value = { logToLinearScale(state.gridScaleXBase, scaleBaseRange) },
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
        valueRange = { scaleBaseRange },
    )
    val gridScaleYBaseControl = Control.Slider(
        name = "Grid Scale Y Log Base",
        value = { logToLinearScale(state.gridScaleYBase, scaleBaseRange) },
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
        valueRange = { scaleBaseRange },
    )
    val radiusScaleBaseControl = Control.Slider(
        name = "Radius Scale Log Base",
        value = { logToLinearScale(state.radiusScaleBase, scaleBaseRange) },
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
        valueRange = { scaleBaseRange },
    )

    val scaleExponentRange = 1.001f..10f
    val gridScaleXExponentControl = Control.Slider(
        name = "Grid Scale X Exponent",
        value = { logToLinearScale(state.gridScaleXExponent, scaleExponentRange) },
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
        valueRange = { scaleExponentRange },
    )
    val gridScaleYExponentControl = Control.Slider(
        name = "Grid Scale Y Exponent",
        value = { logToLinearScale(state.gridScaleYExponent, scaleExponentRange) },
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
        valueRange = { scaleExponentRange },
    )
    val radiusScaleExponentControl = Control.Slider(
        name = "Radius Scale Exponent",
        value = { logToLinearScale(state.radiusScaleExponent, scaleExponentRange) },
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
        valueRange = { scaleExponentRange },
    )

    val thetaRadiansRange = (0.01f..(PI * 2f).toFloat())
    val thetaControl = Control.Slider(
        name = "Theta",
        value = { logToLinearScale(state.thetaRadians, thetaRadiansRange) },
        onValueChange = {
            state.thetaRadians = linearToLogScale(it, thetaRadiansRange)
            state.coordinateSystem = when (val coordinateSystem = state.coordinateSystem) {
                is GridCoordinateSystem.Cartesian -> coordinateSystem
                is GridCoordinateSystem.Polar -> coordinateSystem.copy(
                    theta = state.thetaRadians,
                )
            }
        },
        valueRange = { thetaRadiansRange },
    )

    val radiusSpacingControl = Control.Slider(
        name = "Radius Spacing",
        value = { state.radiusSpacing.value },
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
        valueRange = { 0f..200f },
    )

    val radiusScaleControl = Control.Dropdown(
        name = "Radius Scale",
        values = {
            gridScales.map { (kclass, name) ->
                Control.Dropdown.DropdownItem(
                    name = name,
                    value = kclass,
                )
            }.toPersistentList()
        },
        selectedIndex = { gridScales.indexOfFirst { it.first == state.radiusScale::class } },
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

    val rotationDegreesControl = Control.Slider(
        name = "Grid Rotation",
        value = { state.rotationDegrees },
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
        valueRange = { 0f..360f },
    )

    val showLinesControl = Control.Toggle(
        name = "Show Grid Lines",
        value = { state.showLines },
        onValueChange = { state.showLines = it },
    )

    val vertexItems = mapOf(
        null to "None",
        GridVertex.Oval::class to "Oval",
        GridVertex.Rect::class to "Rect",
        GridVertex.Plus::class to "Plus",
        GridVertex.X::class to "X",
    )
    val vertexControl = Control.Dropdown(
        name = "Vertex",
        values = {
            vertexItems.map { (kclass, name) ->
                Control.Dropdown.DropdownItem(
                    name = name,
                    value = kclass,
                )
            }.toPersistentList()
        },
        selectedIndex = {
            vertexItems.keys.indexOf<KClass<out Any>?>(state.vertex?.let { it::class })
                .coerceAtLeast(0) // -1 to null
        },
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
    val vertexDrawStyleControl = Control.Dropdown(
        name = "Vertex Draw Style",
        values = {
            vertexDrawStyles.map { (kclass, name) ->
                Control.Dropdown.DropdownItem(
                    name = name,
                    value = kclass,
                )
            }.toPersistentList()
        },
        selectedIndex = { vertexDrawStyles.keys.indexOf(state.vertexDrawStyle::class) },
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

    val vertexRotationControl = Control.Slider(
        name = "Vertex Rotation",
        value = { state.vertexRotationDegrees },
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
        valueRange = { 0f..360f },
    )

    val strokeWidthControl = Control.Slider(
        name = "Line Stroke Width",
        value = { state.strokeWidthPx },
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
        valueRange = { 1f..100f },
    )

    val vertexSizeControl = Control.Slider(
        name = "Vertex Size",
        value = { (state.vertexWidth.value + state.vertexHeight.value) / 2f },
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
        valueRange = { 1f..100f },
    )

    val vertexWidthControl = Control.Slider(
        name = "Vertex Width",
        value = { state.vertexWidth.value },
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
        valueRange = { 1f..100f },
    )
    val vertexHeightControl = Control.Slider(
        name = "Vertex Height",
        value = { state.vertexHeight.value },
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
        valueRange = { 1f..100f },
    )

    val offsetXControl = Control.Slider(
        name = "Offset X",
        value = { state.offsetX.value },
        onValueChange = { state.offsetX = it.dp },
        valueRange = { -2000f..2000f },
    )

    val offsetYControl = Control.Slider(
        name = "Offset Y",
        value = { state.offsetY.value },
        onValueChange = { state.offsetY = it.dp },
        valueRange = { -2000f..2000f },
    )

    val clipControl = Control.Toggle(
        name = "Clip to bounds",
        value = { state.clipToBounds },
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
