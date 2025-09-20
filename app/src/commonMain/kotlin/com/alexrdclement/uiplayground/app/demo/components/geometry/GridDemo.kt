package com.alexrdclement.uiplayground.app.demo.components.geometry

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
import com.alexrdclement.uiplayground.components.core.Surface
import com.alexrdclement.uiplayground.components.geometry.Grid
import com.alexrdclement.uiplayground.components.geometry.GridCoordinateSystem
import com.alexrdclement.uiplayground.components.geometry.GridCoordinateSystemType
import com.alexrdclement.uiplayground.components.geometry.GridLineStyle
import com.alexrdclement.uiplayground.components.geometry.GridLineStyleSaver
import com.alexrdclement.uiplayground.components.geometry.GridScale
import com.alexrdclement.uiplayground.components.geometry.GridScaleType
import com.alexrdclement.uiplayground.components.geometry.GridVertex
import com.alexrdclement.uiplayground.components.geometry.GridVertexType
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
            vertex = state.vertexState.vertex,
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
    val density: Density,
    val color: Color,
    xGridScaleStateInitial: CartesianGridScaleState = CartesianGridScaleState(),
    yGridScaleStateInitial: CartesianGridScaleState = CartesianGridScaleState(),
    polarGridScaleStateInitial: PolarGridScaleState = PolarGridScaleState(),
    coordinateSystemTypeInitial: GridCoordinateSystemType = GridCoordinateSystemType.Cartesian,
    vertexStateInitial: GridVertexState = GridVertexState(
        density = density,
        colorInitial = color,
    ),
    showLinesInitial: Boolean = true,
    lineStyleInitial: GridLineStyle = GridLineStyle(
        color = color,
        stroke = Stroke(width = 1f),
    ),
    strokeWidthPxInitial: Float = 1f,
    offsetXInitial: Dp = 0.dp,
    offsetYInitial: Dp = 0.dp,
    rotationDegreesInitial: Float = 0f,
    clipToBoundsInitial: Boolean = true,
) {
    var coordinateSystemType by mutableStateOf(coordinateSystemTypeInitial)
        internal set
    val coordinateSystem: GridCoordinateSystem
        get() = when (coordinateSystemType) {
            GridCoordinateSystemType.Cartesian -> GridCoordinateSystem.Cartesian(
                scaleX = xGridScaleState.gridScale,
                scaleY = yGridScaleState.gridScale,
                rotationDegrees = rotationDegrees,
            )

            GridCoordinateSystemType.Polar -> GridCoordinateSystem.Polar(
                radiusScale = polarGridScaleState.gridScale,
                thetaRadians = polarGridScaleState.thetaRadians,
                rotationDegrees = rotationDegrees,
            )
        }

    var xGridScaleState by mutableStateOf(xGridScaleStateInitial)
        internal set
    var yGridScaleState by mutableStateOf(yGridScaleStateInitial)
        internal set
    var polarGridScaleState by mutableStateOf(polarGridScaleStateInitial)
        internal set

    var vertexState by mutableStateOf(vertexStateInitial)
        internal set

    var showLines by mutableStateOf(showLinesInitial)
        internal set
    var lineStyle: GridLineStyle by mutableStateOf(lineStyleInitial)
        internal set
    var strokeWidthPx by mutableStateOf(strokeWidthPxInitial)
        internal set

    var offsetX by mutableStateOf(offsetXInitial)
        internal set
    var offsetY by mutableStateOf(offsetYInitial)
        internal set
    var rotationDegrees by mutableStateOf(rotationDegreesInitial)
        internal set
    var clipToBounds by mutableStateOf(clipToBoundsInitial)
        internal set
}

private const val colorKey = "color"
private const val densityKey = "density"
private const val coordinateSystemTypeKey = "coordinateSystem"
private const val xGridScaleStateKey = "xGridScaleState"
private const val yGridScaleStateKey = "yGridScaleState"
private const val polarGridScaleStateKey = "radiusGridScaleState"
private const val vertexStateKey = "vertexState"
private const val showLinesKey = "showLines"
private const val lineStyleKey = "lineStyle"
private const val strokeWidthPxKey = "strokeWidthPx"
private const val offsetXKey = "offsetX"
private const val offsetYKey = "offsetY"
private const val rotationDegreesKey = "rotationDegrees"
private const val clipToBoundsKey = "clipToBounds"

val GridDemoStateSaver = mapSaverSafe(
    save = { value ->
        mapOf(
            colorKey to save(value.color, ColorSaver, this),
            densityKey to save(value.density, DensitySaver, this),
            coordinateSystemTypeKey to value.coordinateSystemType,
            xGridScaleStateKey to save(
                value = value.xGridScaleState,
                saver = CartesianGridScaleStateSaver,
                scope = this,
            ),
            yGridScaleStateKey to save(
                value = value.yGridScaleState,
                saver = CartesianGridScaleStateSaver,
                scope = this,
            ),
            polarGridScaleStateKey to save(
                value = value.polarGridScaleState,
                saver = PolarGridScaleStateSaver,
                scope = this,
            ),
            vertexStateKey to save(
                value = value.vertexState,
                saver = GridVertexStateSaver,
                scope = this,
            ),
            showLinesKey to value.showLines,
            lineStyleKey to save(value.lineStyle, GridLineStyleSaver, this),
            strokeWidthPxKey to value.strokeWidthPx,
            offsetXKey to value.offsetX.value,
            offsetYKey to value.offsetY.value,
            rotationDegreesKey to value.rotationDegrees,
            clipToBoundsKey to value.clipToBounds,
        )
    },
    restore = { value ->
        GridDemoState(
            color = restore(value[colorKey], ColorSaver)!!,
            density = restore(value[densityKey], DensitySaver)!!,
            coordinateSystemTypeInitial = value[coordinateSystemTypeKey] as GridCoordinateSystemType,
            xGridScaleStateInitial = restore(
                value = value[xGridScaleStateKey],
                saver = CartesianGridScaleStateSaver,
            )!!,
            yGridScaleStateInitial = restore(
                value = value[yGridScaleStateKey],
                saver = CartesianGridScaleStateSaver,
            )!!,
            polarGridScaleStateInitial = restore(
                value = value[polarGridScaleStateKey],
                saver = PolarGridScaleStateSaver,
            )!!,
            vertexStateInitial = restore(value[vertexStateKey], GridVertexStateSaver)!!,
            showLinesInitial = value[showLinesKey] as Boolean,
            strokeWidthPxInitial = value[strokeWidthPxKey] as Float,
            lineStyleInitial = restore(value[lineStyleKey], GridLineStyleSaver)!!,
            offsetXInitial = (value[offsetXKey] as Float).dp,
            offsetYInitial = (value[offsetYKey] as Float).dp,
            rotationDegreesInitial = value[rotationDegreesKey] as Float,
            clipToBoundsInitial = value[clipToBoundsKey] as Boolean,
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
    val gridScaleXControls = CartesianGridScaleControl(
        name = "x-axis",
        state = state.xGridScaleState,
        onStateChanged = { state.xGridScaleState = it },
    ).controls

    val gridScaleYControls = CartesianGridScaleControl(
        name = "y-axis",
        state = state.yGridScaleState,
        onStateChanged = { state.yGridScaleState = it },
    ).controls

    val polarControls = PolarGridScaleControl(
        state = state.polarGridScaleState,
        onStateChanged = { state.polarGridScaleState = it },
    ).controls

    val coordinateSystemControl = Control.Dropdown(
        name = "Coordinate System",
        values = {
            GridCoordinateSystemType.entries.map { type ->
                Control.Dropdown.DropdownItem(
                    name = type.name,
                    value = type,
                )
            }.toPersistentList()
        },
        selectedIndex = { GridCoordinateSystemType.entries.indexOf(state.coordinateSystemType) },
        onValueChange = { index ->
            state.coordinateSystemType = GridCoordinateSystemType.entries.elementAt(index)
        }
    )

    val vertexControls = GridVertexControl(
        state = state.vertexState,
        onStateChanged = { vertexState -> state.vertexState = vertexState },
    ).controls

    val showLinesControl = Control.Toggle(
        name = "Show Grid Lines",
        value = { state.showLines },
        onValueChange = { state.showLines = it },
    )

    val strokeWidthControl = Control.Slider(
        name = "Line Stroke Width",
        value = { state.strokeWidthPx },
        onValueChange = {
            state.strokeWidthPx = it
            state.lineStyle = state.lineStyle.copy(
                stroke = Stroke(width = state.strokeWidthPx),
            )
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

    val rotationDegreesControl = Control.Slider(
        name = "Rotation",
        value = { state.rotationDegrees },
        onValueChange = { state.rotationDegrees = it },
        valueRange = { -180f..180f },
    )

    val clipControl = Control.Toggle(
        name = "Clip to bounds",
        value = { state.clipToBounds },
        onValueChange = { state.clipToBounds = it },
    )

    val cartesianControls = persistentListOf(
        *gridScaleXControls.toTypedArray(),
        *gridScaleYControls.toTypedArray(),
    )

    val coordinateSystemControls: PersistentList<Control>
        get() {
            val scaleControls = when (state.coordinateSystem) {
                is GridCoordinateSystem.Cartesian -> cartesianControls
                is GridCoordinateSystem.Polar -> polarControls
            }
            return persistentListOf(
                coordinateSystemControl,
                *scaleControls.toTypedArray()
            )
        }

    val lineControls
        get() = if (state.showLines) {
            persistentListOf(
                showLinesControl,
                strokeWidthControl,
            )
        } else {
            persistentListOf(
                showLinesControl,
            )
        }

    val controls
        get() = persistentListOf(
            *coordinateSystemControls.toTypedArray(),
            *vertexControls.toTypedArray(),
            *lineControls.toTypedArray(),
            offsetXControl,
            offsetYControl,
            rotationDegreesControl,
            clipControl,
        )
}

@Stable
class CartesianGridScaleState(
    gridScaleTypeInitial: GridScaleType = GridScaleType.Linear,
    gridSpacingInitial: Dp = 100.dp,
    gridScaleBaseInitial: Float = 2f,
    gridScaleExponentInitial: Float = 2f,
) : GridScaleState(
    gridScaleTypeInitial = gridScaleTypeInitial,
    gridSpacingInitial = gridSpacingInitial,
    gridScaleBaseInitial = gridScaleBaseInitial,
    gridScaleExponentInitial = gridScaleExponentInitial,
)

private const val cartesianGridSpacingKey = "cartesianGridSpacing"
private const val cartesianGridScaleBaseKey = "cartesianGridScaleBase"
private const val cartesianGridScaleExponentKey = "cartesianGridScaleExponent"

val CartesianGridScaleStateSaver = mapSaverSafe(
    save = { value ->
        mapOf(
            cartesianGridSpacingKey to value.gridSpacing.value,
            cartesianGridScaleBaseKey to value.gridScaleBase,
            cartesianGridScaleExponentKey to value.gridScaleExponent,
        )
    },
    restore = { value ->
        CartesianGridScaleState(
            gridSpacingInitial = (value[cartesianGridSpacingKey] as Float).dp,
            gridScaleBaseInitial = value[cartesianGridScaleBaseKey] as Float,
            gridScaleExponentInitial = value[cartesianGridScaleExponentKey] as Float,
        )
    },
)

@Stable
class CartesianGridScaleControl(
    state: CartesianGridScaleState,
    onStateChanged: ((CartesianGridScaleState) -> Unit)? = null,
    private val name: String? = null,
) {
    val gridScaleControl = GridScaleControl(
        state = state,
        onStateChanged = { onStateChanged?.invoke(it as CartesianGridScaleState) },
    )

    val controls = persistentListOf(
        Control.ControlColumn(
            name = name ?: "Scale",
            indent = true,
            controls = {
                persistentListOf(
                    *gridScaleControl.controls.toTypedArray(),
                )
            },
        )
    )
}

@Stable
class PolarGridScaleState(
    gridScaleInitial: GridScaleType = GridScaleType.Linear,
    gridSpacingInitial: Dp = 50.dp,
    gridScaleBaseInitial: Float = 2f,
    gridScaleExponentInitial: Float = 2f,
    thetaRadiansInitial: Float = (PI / 3f).toFloat(),
) : GridScaleState(
    gridScaleTypeInitial = gridScaleInitial,
    gridSpacingInitial = gridSpacingInitial,
    gridScaleBaseInitial = gridScaleBaseInitial,
    gridScaleExponentInitial = gridScaleExponentInitial,
) {
    var thetaRadians by mutableStateOf(thetaRadiansInitial)
        internal set
}

private const val polarGridSpacingKey = "polarGridSpacing"
private const val polarGridScaleBaseKey = "polarGridScaleBase"
private const val polarGridScaleExponentKey = "polarGridScaleExponent"
private const val polarGridThetaRadiansKey = "polarGridThetaRadians"

val PolarGridScaleStateSaver = mapSaverSafe(
    save = { value ->
        mapOf(
            polarGridSpacingKey to value.gridSpacing.value,
            polarGridScaleBaseKey to value.gridScaleBase,
            polarGridScaleExponentKey to value.gridScaleExponent,
            polarGridThetaRadiansKey to value.thetaRadians,
        )
    },
    restore = { value ->
        PolarGridScaleState(
            gridSpacingInitial = (value[polarGridSpacingKey] as Float).dp,
            gridScaleBaseInitial = value[polarGridScaleBaseKey] as Float,
            gridScaleExponentInitial = value[polarGridScaleExponentKey] as Float,
            thetaRadiansInitial = value[polarGridThetaRadiansKey] as Float,
        )
    },
)

@Stable
class PolarGridScaleControl(
    val state: PolarGridScaleState,
    val onStateChanged: ((PolarGridScaleState) -> Unit)? = null,
) {
    val gridScaleControl = GridScaleControl(
        state = state,
        onStateChanged = { onStateChanged?.invoke(it as PolarGridScaleState) },
    )

    val thetaRadiansRange = 0.01f..(PI * 2f).toFloat()
    val thetaControl = Control.Slider(
        name = "Theta",
        value = { logToLinearScale(state.thetaRadians, thetaRadiansRange) },
        onValueChange = {
            state.thetaRadians = linearToLogScale(it, thetaRadiansRange)
            onStateChanged?.invoke(state)
        },
        valueRange = { thetaRadiansRange },
    )

    val controls = persistentListOf(
        Control.ControlColumn(
            indent = true,
            controls = {
                persistentListOf(
                    *gridScaleControl.controls.toTypedArray(),
                    thetaControl,
                )
            },
        )
    )
}

open class GridScaleState(
    gridScaleTypeInitial: GridScaleType,
    gridSpacingInitial: Dp,
    gridScaleBaseInitial: Float,
    gridScaleExponentInitial: Float,
) {
    var gridScaleType: GridScaleType by mutableStateOf(gridScaleTypeInitial)
        internal set
    var gridSpacing by mutableStateOf(gridSpacingInitial)
        internal set
    var gridScaleBase: Float by mutableStateOf(gridScaleBaseInitial)
        internal set
    var gridScaleExponent: Float by mutableStateOf(gridScaleExponentInitial)
        internal set

    val gridScale: GridScale
        get() = when (gridScaleType) {
            GridScaleType.Linear -> GridScale.Linear(gridSpacing)
            GridScaleType.Logarithmic -> GridScale.Logarithmic(
                spacing = gridSpacing,
                base = gridScaleBase,
            )

            GridScaleType.LogarithmicDecay -> GridScale.LogarithmicDecay(
                spacing = gridSpacing,
                base = gridScaleBase,
            )

            GridScaleType.Exponential -> GridScale.Exponential(
                spacing = gridSpacing,
                exponent = gridScaleExponent,
            )

            GridScaleType.ExponentialDecay -> GridScale.ExponentialDecay(
                spacing = gridSpacing,
                exponent = gridScaleExponent,
            )
        }
}

@Stable
open class GridScaleControl(
    val state: GridScaleState,
    val onStateChanged: ((GridScaleState) -> Unit)? = null,
) {
    val gridScaleTypeControl = Control.Dropdown(
        name = "Scale",
        values = {
            GridScaleType.entries.map { entry ->
                Control.Dropdown.DropdownItem(
                    name = entry.name,
                    value = entry,
                )
            }.toPersistentList()
        },
        selectedIndex = { GridScaleType.entries.indexOf(state.gridScaleType) },
        onValueChange = {
            state.gridScaleType = GridScaleType.entries.elementAt(it)
            onStateChanged?.invoke(state)
        },
    )

    val gridSpacingControl = Control.Slider(
        name = "Spacing",
        value = { state.gridSpacing.value },
        onValueChange = {
            state.gridSpacing = it.dp
            onStateChanged?.invoke(state)
        },
        valueRange = { 0f..200f },
    )

    val scaleBaseRange = 1f..10f
    val gridScaleBaseControl = Control.Slider(
        name = "Log Base",
        value = { logToLinearScale(state.gridScaleBase, scaleBaseRange) },
        onValueChange = {
            state.gridScaleBase = linearToLogScale(it, scaleBaseRange)
            onStateChanged?.invoke(state)
        },
        valueRange = { scaleBaseRange },
    )

    val scaleExponentRange = 1.001f..10f
    val gridScaleExponentControl = Control.Slider(
        name = "Exponent Factor",
        value = { logToLinearScale(state.gridScaleExponent, scaleExponentRange) },
        onValueChange = {
            state.gridScaleExponent = linearToLogScale(it, scaleExponentRange)
            onStateChanged?.invoke(state)
        },
        valueRange = { scaleExponentRange },
    )

    val controls
        get() = when (state.gridScale) {
            is GridScale.Linear -> persistentListOf(
                gridScaleTypeControl,
                gridSpacingControl,
            )

            is GridScale.Logarithmic,
            is GridScale.LogarithmicDecay,
                -> persistentListOf(
                gridScaleTypeControl,
                gridSpacingControl,
                gridScaleBaseControl,
            )

            is GridScale.Exponential,
            is GridScale.ExponentialDecay,
                -> persistentListOf(
                gridScaleTypeControl,
                gridSpacingControl,
                gridScaleExponentControl,
            )
        }
}

class GridVertexState(
    val density: Density,
    colorInitial: Color,
    vertexTypeInitial: GridVertexType? = null,
    strokeWidthInitial: Dp = Dp.Hairline,
    drawStyleInitial: DrawStyle = Stroke(width = with(density) { strokeWidthInitial.toPx() }),
    widthInitial: Dp = 10.dp,
    heightInitial: Dp = 10.dp,
    rotationDegreesInitial: Float = 0f,
) {
    var vertexType by mutableStateOf(vertexTypeInitial)
        internal set
    var color by mutableStateOf(colorInitial)
        internal set
    var drawStyle by mutableStateOf(drawStyleInitial)
        internal set
    var strokeWidth by mutableStateOf(strokeWidthInitial)
        internal set
    var width by mutableStateOf(widthInitial)
        internal set
    var height by mutableStateOf(heightInitial)
        internal set
    var rotationDegrees by mutableStateOf(rotationDegreesInitial)
        internal set

    val strokeWidthPx
        get() = with(density) { strokeWidth.toPx() }
    val size
        get() = DpSize(width, height)

    val vertex: GridVertex?
        get() = when (vertexType) {
            GridVertexType.Oval -> GridVertex.Oval(
                color = color,
                size = DpSize(width, height),
                drawStyle = drawStyle,
                rotationDegrees = rotationDegrees,
            )

            GridVertexType.Rect -> GridVertex.Rect(
                color = color,
                size = DpSize(width, height),
                drawStyle = drawStyle,
                rotationDegrees = rotationDegrees,
            )

            GridVertexType.Plus -> GridVertex.Plus(
                color = color,
                size = DpSize(width, height),
                strokeWidth = strokeWidth,
                rotationDegrees = rotationDegrees,
            )

            GridVertexType.X -> GridVertex.X(
                color = color,
                size = DpSize(width, height),
                strokeWidth = strokeWidth,
                rotationDegrees = rotationDegrees,
            )

            null -> null
        }
}

private const val vertexDensityKey = "density"
private const val vertexTypeKey = "vertexType"
private const val vertexColorKey = "color"
private const val vertexStrokeWidthKey = "vertexStrokeWidth"
private const val vertexDrawStyleKey = "vertexDrawStyle"
private const val vertexWidthKey = "vertexWidth"
private const val vertexHeightKey = "vertexHeight"
private const val vertexRotationDegreesKey = "vertexRotationDegrees"

val GridVertexStateSaver = mapSaverSafe(
    save = { value ->
        mapOf(
            vertexDensityKey to save(value.density, DensitySaver, this),
            vertexTypeKey to value.vertexType,
            vertexColorKey to save(value.color, ColorSaver, this),
            vertexStrokeWidthKey to value.strokeWidth.value,
            vertexDrawStyleKey to save(value.drawStyle, DrawStyleSaver, this),
            vertexWidthKey to value.width.value,
            vertexHeightKey to value.height.value,
            vertexRotationDegreesKey to value.rotationDegrees,
        )
    },
    restore = { value ->
        GridVertexState(
            density = restore(value[vertexDensityKey], DensitySaver)!!,
            vertexTypeInitial = value[vertexTypeKey] as GridVertexType,
            colorInitial = restore(value[vertexColorKey], ColorSaver)!!,
            strokeWidthInitial = (value[vertexStrokeWidthKey] as Float).dp,
            drawStyleInitial = restore(value[vertexDrawStyleKey], DrawStyleSaver)!!,
            widthInitial = (value[vertexWidthKey] as Float).dp,
            heightInitial = (value[vertexHeightKey] as Float).dp,
            rotationDegreesInitial = value[vertexRotationDegreesKey] as Float,
        )
    },
)

@Stable
class GridVertexControl(
    val state: GridVertexState,
    val onStateChanged: ((GridVertexState) -> Unit)? = null,
    private val name: String? = null,
) {
    val typeControl = Control.Dropdown(
        name = "Type",
        values = {
            val items = GridVertexType.entries.map { type ->
                Control.Dropdown.DropdownItem<GridVertexType?>(
                    name = type.name,
                    value = type,
                )
            }
            persistentListOf(
                Control.Dropdown.DropdownItem(name = "None", value = null),
                *items.toTypedArray(),
            )
        },
        selectedIndex = {
            state.vertexType?.let(GridVertexType.entries::indexOf)?.plus(1) ?: 0
        },
        onValueChange = {
            state.vertexType = if (it == 0) null else GridVertexType.entries.elementAt(it - 1)
            onStateChanged?.invoke(state)
        }
    )

    val strokeWidthControl = Control.Slider(
        name = "Stroke Width",
        value = { state.strokeWidth.value },
        onValueChange = {
            state.strokeWidth = it.dp
            state.drawStyle = when (state.drawStyle) {
                is Stroke -> Stroke(width = state.strokeWidthPx)
                is Fill -> Fill
            }
            onStateChanged?.invoke(state)
        },
        valueRange = { 1f..100f },
    )

    val vertexDrawStyles = mapOf(
        Stroke::class to "Stroke",
        Fill::class to "Fill",
    )
    val drawStyleControl = Control.Dropdown(
        name = "Draw Style",
        values = {
            vertexDrawStyles.map { (kclass, name) ->
                Control.Dropdown.DropdownItem(
                    name = name,
                    value = kclass,
                )
            }.toPersistentList()
        },
        selectedIndex = { vertexDrawStyles.keys.indexOf(state.drawStyle::class) },
        onValueChange = { index ->
            state.drawStyle = when (vertexDrawStyles.keys.elementAt(index)) {
                Stroke::class -> Stroke(width = state.strokeWidthPx)
                Fill::class -> Fill
                else -> Stroke(width = state.strokeWidthPx)
            }
            onStateChanged?.invoke(state)
        }
    )

    val rotationDegreesControl = Control.Slider(
        name = "Rotation",
        value = { state.rotationDegrees },
        onValueChange = {
            state.rotationDegrees = it
            onStateChanged?.invoke(state)
        },
        valueRange = { -180f..180f },
    )

    val sizeControl = Control.Slider(
        name = "Size",
        value = { (state.width.value + state.height.value) / 2f },
        onValueChange = {
            state.width = it.dp
            state.height = it.dp
            onStateChanged?.invoke(state)
        },
        valueRange = { 1f..100f },
    )

    val widthControl = Control.Slider(
        name = "Width",
        value = { state.width.value },
        onValueChange = {
            state.width = it.dp
            onStateChanged?.invoke(state)
        },
        valueRange = { 1f..100f },
    )

    val heightControl = Control.Slider(
        name = "Height",
        value = { state.height.value },
        onValueChange = {
            state.height = it.dp
            onStateChanged?.invoke(state)
        },
        valueRange = { 1f..100f },
    )

    val drawStyleControls
        get() = when (state.vertex) {
            is GridVertex.Oval,
            is GridVertex.Rect,
            -> persistentListOf(
                drawStyleControl,
                if (state.drawStyle is Stroke) strokeWidthControl else null,
            ).filterNotNull().toPersistentList()

            is GridVertex.Plus,
            is GridVertex.X,
            null,
            -> persistentListOf(strokeWidthControl)
        }

    val controls = persistentListOf(
        Control.ControlColumn(
            name = name ?: "Vertex",
            indent = true,
            controls = {
                if (state.vertex == null) {
                    persistentListOf(typeControl)
                } else {
                    persistentListOf(
                        typeControl,
                        *drawStyleControls.toTypedArray(),
                        sizeControl,
                        widthControl,
                        heightControl,
                        rotationDegreesControl,
                    )
                }
            },
        )
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
