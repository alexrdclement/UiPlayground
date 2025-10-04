package com.alexrdclement.uiplayground.app.theme.shape

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.control.enumControl
import com.alexrdclement.uiplayground.components.core.Button
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.components.layout.Scaffold
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.ShapeScheme
import com.alexrdclement.uiplayground.theme.ShapeToken
import com.alexrdclement.uiplayground.theme.ShapeType
import com.alexrdclement.uiplayground.theme.control.ThemeController
import com.alexrdclement.uiplayground.theme.control.ThemeState
import com.alexrdclement.uiplayground.theme.copy
import com.alexrdclement.uiplayground.theme.toShape
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ShapeScreen(
    themeController: ThemeController,
    onNavigateBack: () -> Unit,
) {
    val state = rememberShapeScreenState(themeState = themeController)
    val control = rememberShapeScreenControl(state = state, themeController = themeController)

    Scaffold(
        topBar = {
            DemoTopBar(
                title = "Shape",
                onNavigateBack = onNavigateBack,
                onConfigureClick = {},
                actions = {},
            )
        },
        modifier = Modifier
           .displayCutoutPadding()
    ) { paddingValues ->
        Demo(
            controls = control.controls,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(
                    space = PlaygroundTheme.spacing.large,
                    alignment = Alignment.CenterVertically,
                ),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(PlaygroundTheme.spacing.medium),
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(ShapeToken.entries) { shape ->
                    Button(
                        shape = shape,
                        onClick = {},
                    ) {
                        Text(
                            text = shape.name,
                            style = PlaygroundTheme.typography.headline,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun rememberShapeScreenState(
    themeState: ThemeState,
): ShapeScreenState {
    return rememberSaveable(
        themeState,
        saver = ShapeScreenStateSaver(themeState),
    ) {
        ShapeScreenState(
            themeState = themeState,
        )
    }
}

@Stable
class ShapeScreenState(
    val themeState: ThemeState,
) {
    val shapeScheme: ShapeScheme
        get() = themeState.shapeScheme

    val cornerRadiusByShapeToken = ShapeToken.entries.associateWith { token ->
        token.toShape(shapeScheme).cornerRadius
    }
}

fun ShapeScreenStateSaver(themeState: ThemeState) = mapSaverSafe(
    save = { state ->
        mapOf()
    },
    restore = { map ->
        ShapeScreenState(
            themeState = themeState,
        )
    }
)

@Composable
fun rememberShapeScreenControl(
    state: ShapeScreenState,
    themeController: ThemeController,
): ShapeScreenControl {
    return remember(state, themeController) {
        ShapeScreenControl(state = state, themeController = themeController)
    }
}

@Stable
class ShapeScreenControl(
    val state: ShapeScreenState,
    val themeController: ThemeController,
) {
    val shapeControls = ShapeToken.entries.map { token ->
        makeControlForToken(
            token = token,
            state = state,
            themeController = themeController,
        )
    }

    val controls: PersistentList<Control> = persistentListOf(
        *shapeControls.toTypedArray(),
    )
}

private fun makeControlForToken(
    token: ShapeToken,
    state: ShapeScreenState,
    themeController: ThemeController,
): Control {
    val shapeType = token.toShape(state.shapeScheme).type
    val shapeControl = enumControl(
        name = "Shape",
        values = { ShapeType.entries },
        selectedValue = { shapeType },
        onValueChange = { shapeType ->
            val shapeScheme = state.shapeScheme.copy(
                token = token,
                shape = shapeType.toShape(
                    cornerRadius = state.cornerRadiusByShapeToken[token]!!
                )
            )
            themeController.setShapeScheme(shapeScheme)
        }
    )

    val cornerRadiusControl = Control.Slider(
        name = "Corner radius",
        value = { state.cornerRadiusByShapeToken[token]!!.value },
        onValueChange = { radius ->
            val shapeScheme = state.shapeScheme.copy(
                token = token,
                shape = ShapeType.Rectangle.toShape(
                    cornerRadius = radius.dp,
                )
            )
            themeController.setShapeScheme(shapeScheme)
        },
        valueRange = { 0f..64f },
    )

    return Control.ControlColumn(
        name = token.name,
        controls = {
            when (shapeType) {
                ShapeType.Rectangle -> persistentListOf(
                    shapeControl,
                    cornerRadiusControl,
                )
                else -> persistentListOf(
                    shapeControl,
                )
            }
        }
    )
}
