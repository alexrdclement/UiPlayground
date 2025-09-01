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
import com.alexrdclement.uiplayground.components.GridStyle
import com.alexrdclement.uiplayground.components.HorizontalDivider
import com.alexrdclement.uiplayground.components.Surface
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun GridDemo(
    modifier: Modifier = Modifier,
) {
    val color = PlaygroundTheme.colorScheme.primary

    var strokeWidthPx by remember { mutableStateOf(1f) }
    val strokeWidth = with(LocalDensity.current) { strokeWidthPx.toDp() }
    var drawStyle by remember { mutableStateOf<DrawStyle>(Stroke(width = strokeWidthPx)) }

    var vertexWidthPx by remember { mutableStateOf(10f) }
    val vertexWidth = with(LocalDensity.current) { vertexWidthPx.toDp() }
    var vertexHeightPx by remember { mutableStateOf(10f) }
    val vertexHeight = with(LocalDensity.current) { vertexHeightPx.toDp() }
    val size = DpSize(vertexWidth, vertexHeight)


    val initialGridStyle = GridStyle.Line(
        color = color,
        strokeWidth = strokeWidth,
    )
    var gridStyle: GridStyle by remember { mutableStateOf(initialGridStyle) }

    val gridStyleItems = mapOf(
        GridStyle.Line::class to "Line",
        GridStyle.Vertex.Oval::class to "Oval",
        GridStyle.Vertex.Rect::class to "Rect",
        GridStyle.Vertex.Plus::class to "Plus",
    )
    val gridStyleControl = Control.Dropdown(
        name = "Grid Style",
        values = gridStyleItems.map { (kclass, name) ->
            Control.Dropdown.DropdownItem(
                name = name,
                value = kclass,
            )
        }.toPersistentList(),
        selectedIndex = gridStyleItems.keys.indexOf(gridStyle::class),
        onValueChange = { index ->
            gridStyle = when (gridStyleItems.keys.elementAt(index)) {
                GridStyle.Line::class -> GridStyle.Line(
                    color = color,
                    strokeWidth = strokeWidth,
                )
                GridStyle.Vertex.Oval::class -> GridStyle.Vertex.Oval(
                    color = color,
                    size = size,
                    drawStyle = drawStyle,
                )
                GridStyle.Vertex.Rect::class -> GridStyle.Vertex.Rect(
                    color = color,
                    size = size,
                    drawStyle = drawStyle,
                )
                GridStyle.Vertex.Plus::class -> GridStyle.Vertex.Plus(
                    color = color,
                    size = size,
                    strokeWidth = strokeWidth,
                )
                else -> initialGridStyle
            }
        }
    )

    var gridSpacingPx by remember { mutableStateOf(100f) }
    val gridSpacing = with(LocalDensity.current) { gridSpacingPx.toDp() }
    val gridSpacingControl = Control.Slider(
        name = "Spacing",
        value = gridSpacingPx,
        onValueChange = { gridSpacingPx = it },
        valueRange = 0f..200f,
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

    val drawStyles = mapOf(
        Stroke::class to "Stroke",
        Fill::class to "Fill",
    )
    val drawStyleControl = Control.Dropdown(
        name = "Draw Style",
        values = drawStyles.map { (kclass, name) ->
            Control.Dropdown.DropdownItem(
                name = name,
                value = kclass,
            )
        }.toPersistentList(),
        selectedIndex = drawStyles.keys.indexOf(drawStyle::class),
        onValueChange = { index ->
            drawStyle = when (drawStyles.keys.elementAt(index)) {
                Stroke::class -> Stroke(width = strokeWidthPx)
                Fill::class -> Fill
                else -> Stroke(width = strokeWidthPx)
            }
            gridStyle = when (val gridStyle = gridStyle) {
                is GridStyle.Line -> gridStyle
                is GridStyle.Vertex.Oval -> gridStyle.copy(
                    drawStyle = drawStyle,
                )
                is GridStyle.Vertex.Rect -> gridStyle.copy(
                    drawStyle = drawStyle,
                )
                is GridStyle.Vertex.Plus -> gridStyle
            }
        }
    )

    val strokeWidthControl = Control.Slider(
        name = "Stroke Width",
        value = strokeWidthPx,
        onValueChange = {
            strokeWidthPx = it
            drawStyle = Stroke(width = strokeWidthPx)
            gridStyle = when (val gridStyle = gridStyle) {
                is GridStyle.Line -> gridStyle.copy(
                    strokeWidth = strokeWidth,
                )
                is GridStyle.Vertex.Oval -> gridStyle.copy(
                    drawStyle = Stroke(width = strokeWidthPx),
                )
                is GridStyle.Vertex.Rect -> gridStyle.copy(
                    drawStyle = Stroke(width = strokeWidthPx),
                )
                is GridStyle.Vertex.Plus -> gridStyle.copy(
                    strokeWidth = strokeWidth,
                )
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
            gridStyle = when (val gridStyle = gridStyle) {
                is GridStyle.Line -> gridStyle
                is GridStyle.Vertex.Oval -> gridStyle.copy(
                    size = DpSize(vertexWidth, vertexHeight),
                )
                is GridStyle.Vertex.Rect -> gridStyle.copy(
                    size = DpSize(vertexWidth, vertexHeight),
                )
                is GridStyle.Vertex.Plus -> gridStyle.copy(
                    size = DpSize(vertexWidth, vertexHeight),
                )
            }
        },
        valueRange = 1f..100f,
    )

    val vertexWidthControl = Control.Slider(
        name = "Vertex Width",
        value = vertexWidthPx,
        onValueChange = {
            vertexWidthPx = it
            gridStyle = when (val gridStyle = gridStyle) {
                is GridStyle.Line -> gridStyle
                is GridStyle.Vertex.Oval -> gridStyle.copy(
                    size = gridStyle.size.copy(
                        width = vertexWidth,
                    ),
                )
                is GridStyle.Vertex.Rect -> gridStyle.copy(
                    size = gridStyle.size.copy(
                        width = vertexWidth,
                    ),
                )
                is GridStyle.Vertex.Plus -> gridStyle.copy(
                    size = gridStyle.size.copy(
                        width = vertexWidth,
                    ),
                )
            }
        },
        valueRange = 1f..100f,
    )
    val vertexHeightControl = Control.Slider(
        name = "Vertex Height",
        value = vertexHeightPx,
        onValueChange = {
            vertexHeightPx = it
            gridStyle = when (val gridStyle = gridStyle) {
                is GridStyle.Line -> gridStyle
                is GridStyle.Vertex.Oval -> gridStyle.copy(
                    size = gridStyle.size.copy(
                        height = vertexHeight,
                    ),
                )
                is GridStyle.Vertex.Rect -> gridStyle.copy(
                    size = gridStyle.size.copy(
                        height = vertexHeight,
                    ),
                )
                is GridStyle.Vertex.Plus -> gridStyle.copy(
                    size = gridStyle.size.copy(
                        height = vertexHeight,
                    ),
                )
            }
        },
        valueRange = 1f..100f,
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
            style = gridStyle,
            spacing = gridSpacing,
            offset = Offset(offsetXPx, offsetYPx),
            clipToBounds = clipToBounds,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        Controls(
            controls = persistentListOf(
                gridStyleControl,
                drawStyleControl,
                strokeWidthControl,
                gridSpacingControl,
                vertexSizeControl,
                vertexWidthControl,
                vertexHeightControl,
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
