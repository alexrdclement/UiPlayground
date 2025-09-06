package com.alexrdclement.uiplayground.app.demo.components.demo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.control.Controls
import com.alexrdclement.uiplayground.components.Grid
import com.alexrdclement.uiplayground.components.GridCoordinateSystem
import com.alexrdclement.uiplayground.components.GridLineStyle
import com.alexrdclement.uiplayground.components.GridVertex
import com.alexrdclement.uiplayground.components.HorizontalDivider
import com.alexrdclement.uiplayground.components.Surface
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.reflect.KClass

@Composable
fun GridDemo(
    modifier: Modifier = Modifier,
) {
    val color = PlaygroundTheme.colorScheme.primary

    var gridSpacingPx by remember { mutableStateOf(100f) }
    val gridSpacing = with(LocalDensity.current) { gridSpacingPx.toDp() }

    var rotationDegrees by remember { mutableStateOf(0f) }

    var theta by remember { mutableStateOf((PI / 3f).toFloat()) }

    val coordinateSystems = mapOf(
        GridCoordinateSystem.Cartesian::class to "Cartesian",
        GridCoordinateSystem.Polar::class to "Polar",
    )
    val initialCoordinateSystem = GridCoordinateSystem.Cartesian(spacing = gridSpacing)
    var coordinateSystem: GridCoordinateSystem by remember { mutableStateOf(initialCoordinateSystem) }
    val coordinateSystemControl = Control.Dropdown(
        name = "Coordinate System",
        values = coordinateSystems.map { (kclass, name) ->
            Control.Dropdown.DropdownItem(
                name = name,
                value = kclass,
            )
        }.toPersistentList(),
        selectedIndex = coordinateSystems.keys.indexOf(coordinateSystem::class),
        onValueChange = { index ->
            coordinateSystem = when (coordinateSystems.keys.elementAt(index)) {
                GridCoordinateSystem.Cartesian::class -> GridCoordinateSystem.Cartesian(
                    spacing = gridSpacing,
                    rotationDegrees = rotationDegrees,
                )
                GridCoordinateSystem.Polar::class -> GridCoordinateSystem.Polar(
                    radiusSpacing = gridSpacing,
                    theta = theta,
                    rotationDegrees = rotationDegrees,
                )
                else -> initialCoordinateSystem
            }
        }
    )

    val gridSpacingControl = Control.Slider(
        name = "Spacing",
        value = gridSpacingPx,
        onValueChange = {
            gridSpacingPx = it
            coordinateSystem = when (val coordinateSystem = coordinateSystem) {
                is GridCoordinateSystem.Cartesian -> coordinateSystem.copy(
                    xSpacing = gridSpacing,
                    ySpacing = gridSpacing,
                )
                is GridCoordinateSystem.Polar -> coordinateSystem.copy(
                    radiusSpacing = gridSpacing,
                )
            }
        },
        valueRange = 0f..200f,
    )
    val thetaRange = (0.01f..(PI * 2f).toFloat())
    val thetaControl = Control.Slider(
        name = "Theta",
        value = logToLinearScale(theta, thetaRange),
        onValueChange = {
            theta = linearToLogScale(it, thetaRange)
            coordinateSystem = when (val coordinateSystem = coordinateSystem) {
                is GridCoordinateSystem.Cartesian -> coordinateSystem
                is GridCoordinateSystem.Polar -> coordinateSystem.copy(
                    theta = theta,
                )
            }
        },
        valueRange = thetaRange,
    )

    val rotationDegreesControl = Control.Slider(
        name = "Rotation",
        value = rotationDegrees,
        onValueChange = {
            rotationDegrees = it
            coordinateSystem = when (val coordinateSystem = coordinateSystem) {
                is GridCoordinateSystem.Cartesian -> coordinateSystem.copy(
                    rotationDegrees = rotationDegrees,
                )
                is GridCoordinateSystem.Polar -> coordinateSystem.copy(
                    rotationDegrees = rotationDegrees,
                )
            }
        },
        valueRange = 0f..360f,
    )

    var strokeWidthPx by remember { mutableStateOf(1f) }
    val strokeWidth = with(LocalDensity.current) { strokeWidthPx.toDp() }

    val initialLineStyle = GridLineStyle(
        color = color,
        stroke = Stroke(width = strokeWidthPx),
    )
    var lineStyle: GridLineStyle by remember { mutableStateOf(initialLineStyle) }

    var showLines by remember { mutableStateOf(true) }
    val showLinesControl = Control.Toggle(
        name = "Show Lines",
        value = showLines,
        onValueChange = { showLines = it },
    )

    var vertexDrawStyle by remember { mutableStateOf<DrawStyle>(Stroke(width = strokeWidthPx)) }
    var vertexWidthPx by remember { mutableStateOf(10f) }
    val vertexWidth = with(LocalDensity.current) { vertexWidthPx.toDp() }
    var vertexHeightPx by remember { mutableStateOf(10f) }
    val vertexHeight = with(LocalDensity.current) { vertexHeightPx.toDp() }
    val vertexSize = DpSize(vertexWidth, vertexHeight)
    var vertexRotationDegrees by remember { mutableStateOf(0f) }

    var vertex by remember { mutableStateOf<GridVertex?>(null) }
    val vertexItems = mapOf(
        null to "None",
        GridVertex.Oval::class to "Oval",
        GridVertex.Rect::class to "Rect",
        GridVertex.Plus::class to "Plus",
        GridVertex.X::class to "X",
    )
    val vertexControl = Control.Dropdown(
        name = "Vertex",
        values = vertexItems.map { (kclass, name) ->
            Control.Dropdown.DropdownItem(
                name = name,
                value = kclass,
            )
        }.toPersistentList(),
        selectedIndex = vertexItems.keys.indexOf<KClass<out Any>?>(vertex?.let { it::class })
            .coerceAtLeast(0), // -1 to null
        onValueChange = { index ->
            vertex = when (vertexItems.keys.elementAt(index)) {
                GridVertex.Oval::class -> GridVertex.Oval(
                    color = color,
                    size = vertexSize,
                    drawStyle = vertexDrawStyle,
                    rotationDegrees = vertexRotationDegrees,
                )
                GridVertex.Rect::class -> GridVertex.Rect(
                    color = color,
                    size = vertexSize,
                    drawStyle = vertexDrawStyle,
                    rotationDegrees = vertexRotationDegrees,
                )
                GridVertex.Plus::class -> GridVertex.Plus(
                    color = color,
                    size = vertexSize,
                    strokeWidth = strokeWidth,
                    rotationDegrees = vertexRotationDegrees,
                )
                GridVertex.X::class -> GridVertex.X(
                    color = color,
                    size = vertexSize,
                    strokeWidth = strokeWidth,
                    rotationDegrees = vertexRotationDegrees,
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
        values = vertexDrawStyles.map { (kclass, name) ->
            Control.Dropdown.DropdownItem(
                name = name,
                value = kclass,
            )
        }.toPersistentList(),
        selectedIndex = vertexDrawStyles.keys.indexOf(vertexDrawStyle::class),
        onValueChange = { index ->
            vertexDrawStyle = when (vertexDrawStyles.keys.elementAt(index)) {
                Stroke::class -> Stroke(width = strokeWidthPx)
                Fill::class -> Fill
                else -> Stroke(width = strokeWidthPx)
            }
            vertex = when (val vertex = vertex) {
                is GridVertex.Oval -> vertex.copy(
                    drawStyle = vertexDrawStyle,
                )
                is GridVertex.Rect -> vertex.copy(
                    drawStyle = vertexDrawStyle,
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
        value = vertexRotationDegrees,
        onValueChange = {
            vertexRotationDegrees = it
            vertex = when (val vertex = vertex) {
                is GridVertex.Oval -> vertex.copy(
                    rotationDegrees = vertexRotationDegrees,
                )
                is GridVertex.Rect -> vertex.copy(
                    rotationDegrees = vertexRotationDegrees,
                )
                is GridVertex.Plus -> vertex.copy(
                    rotationDegrees = vertexRotationDegrees,
                )
                is GridVertex.X -> vertex.copy(
                    rotationDegrees = vertexRotationDegrees,
                )
                null -> null
            }
        },
        valueRange = 0f..360f,
    )

    val strokeWidthControl = Control.Slider(
        name = "Stroke Width",
        value = strokeWidthPx,
        onValueChange = {
            strokeWidthPx = it
            vertexDrawStyle = Stroke(width = strokeWidthPx)
            lineStyle = lineStyle.copy(
                stroke = Stroke(width = strokeWidthPx),
            )
            vertex = when (vertex) {
                is GridVertex.Oval -> (vertex as GridVertex.Oval).copy(
                    drawStyle = vertexDrawStyle,
                )
                is GridVertex.Rect -> (vertex as GridVertex.Rect).copy(
                    drawStyle = vertexDrawStyle,
                )
                is GridVertex.Plus -> (vertex as GridVertex.Plus).copy(
                    strokeWidth = strokeWidth,
                )
                is GridVertex.X -> (vertex as GridVertex.X).copy(
                    strokeWidth = strokeWidth,
                )
                null -> null
            }
        },
        valueRange = 1f..100f,
    )

    val vertexSizeControl = Control.Slider(
        name = "Vertex Size",
        value = (vertexWidthPx + vertexHeightPx) / 2f,
        onValueChange = {
            vertexWidthPx = it
            vertexHeightPx = it
            vertex = when (val vertex = vertex) {
                is GridVertex.Oval -> vertex.copy(
                    size = DpSize(vertexWidth, vertexHeight),
                )
                is GridVertex.Rect -> vertex.copy(
                    size = DpSize(vertexWidth, vertexHeight),
                )
                is GridVertex.Plus -> vertex.copy(
                    size = DpSize(vertexWidth, vertexHeight),
                )
                is GridVertex.X -> vertex.copy(
                    size = DpSize(vertexWidth, vertexHeight),
                )
                null -> null
            }
        },
        valueRange = 1f..100f,
    )

    val vertexWidthControl = Control.Slider(
        name = "Vertex Width",
        value = vertexWidthPx,
        onValueChange = {
            vertexWidthPx = it
            vertex = when (val vertex = vertex) {
                is GridVertex.Oval -> vertex.copy(
                    size = DpSize(vertexWidth, vertexHeight),
                )
                is GridVertex.Rect -> vertex.copy(
                    size = DpSize(vertexWidth, vertexHeight),
                )
                is GridVertex.Plus -> vertex.copy(
                    size = DpSize(vertexWidth, vertexHeight),
                )
                is GridVertex.X -> vertex.copy(
                    size = DpSize(vertexWidth, vertexHeight),
                )
                null -> null
            }
        },
        valueRange = 1f..100f,
    )
    val vertexHeightControl = Control.Slider(
        name = "Vertex Height",
        value = vertexHeightPx,
        onValueChange = {
            vertexHeightPx = it
            vertex = when (vertex) {
                is GridVertex.Oval -> (vertex as GridVertex.Oval).copy(
                    size = DpSize(vertexWidth, vertexHeight),
                )
                is GridVertex.Rect -> (vertex as GridVertex.Rect).copy(
                    size = DpSize(vertexWidth, vertexHeight),
                )
                is GridVertex.Plus -> (vertex as GridVertex.Plus).copy(
                    size = DpSize(vertexWidth, vertexHeight),
                )
                is GridVertex.X -> (vertex as GridVertex.X).copy(
                    size = DpSize(vertexWidth, vertexHeight),
                )
                null -> null
            }
        },
        valueRange = 1f..100f,
    )

    var offsetXPx by remember { mutableStateOf(0f) }
    val offsetXControl = Control.Slider(
        name = "Offset X",
        value = offsetXPx,
        onValueChange = { offsetXPx = it },
        valueRange = 0f..200f,
    )

    var offsetYPx by remember { mutableStateOf(0f) }
    val offsetYControl = Control.Slider(
        name = "Offset Y",
        value = offsetYPx,
        onValueChange = { offsetYPx = it },
        valueRange = 0f..200f,
    )

    var clipToBounds by remember { mutableStateOf(true) }
    val clipControl = Control.Toggle(
        name = "Clip to bounds",
        value = clipToBounds,
        onValueChange = { clipToBounds = it },
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Grid(
            lineStyle = lineStyle.takeIf { showLines },
            vertex = vertex,
            coordinateSystem = coordinateSystem,
            offset = Offset(offsetXPx, offsetYPx),
            clipToBounds = clipToBounds,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        Controls(
            controls = persistentListOf(
                coordinateSystemControl,
                showLinesControl,
                gridSpacingControl,
                thetaControl,
                rotationDegreesControl,
                strokeWidthControl,
                vertexControl,
                vertexDrawStyleControl,
                vertexSizeControl,
                vertexWidthControl,
                vertexHeightControl,
                vertexRotationControl,
                offsetXControl,
                offsetYControl,
                clipControl,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .verticalScroll(rememberScrollState())
                .padding(PlaygroundTheme.spacing.medium)
                .navigationBarsPadding(),
        )
    }
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
