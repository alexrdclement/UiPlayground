package com.alexrdclement.uiplayground.app.theme.styles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.demo.Demo
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.components.core.TextAlign
import com.alexrdclement.uiplayground.app.demo.components.core.TextDemo
import com.alexrdclement.uiplayground.app.demo.components.core.TextDemoControl
import com.alexrdclement.uiplayground.app.demo.components.core.TextDemoState
import com.alexrdclement.uiplayground.app.demo.components.core.TextDemoStateSaver
import com.alexrdclement.uiplayground.components.demo.control.Control
import com.alexrdclement.uiplayground.components.demo.control.enumControl
import com.alexrdclement.uiplayground.components.core.Button
import com.alexrdclement.uiplayground.components.layout.Scaffold
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.components.util.restore
import com.alexrdclement.uiplayground.components.util.save
import com.alexrdclement.uiplayground.theme.ColorToken
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.ShapeToken
import com.alexrdclement.uiplayground.theme.Styles
import com.alexrdclement.uiplayground.theme.control.ThemeController
import com.alexrdclement.uiplayground.theme.control.ThemeState
import com.alexrdclement.uiplayground.theme.styles.Border
import com.alexrdclement.uiplayground.theme.styles.ButtonStyleToken
import com.alexrdclement.uiplayground.theme.styles.copy
import com.alexrdclement.uiplayground.theme.styles.toStyle
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ButtonStyleScreen(
    themeController: ThemeController,
    onNavigateBack: () -> Unit,
) {
    val state = rememberButtonStyleScreenState(themeState = themeController)
    val control = rememberButtonStyleScreenControl(state = state, themeController = themeController)

    Scaffold(
        topBar = {
            DemoTopBar(
                title = "Button style",
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
                items(state.buttonStylesByToken.keys.toList()) { style ->
                    Button(
                        style = style,
                        onClick = {},
                    ) {
                        this@Demo.TextDemo(
                            state = state.textDemoState,
                            control = control.textDemoControl,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun rememberButtonStyleScreenState(
    themeState: ThemeState,
    textDemoStateInitial: TextDemoState = TextDemoState(
        textAlignInitial = TextAlign.Center,
    ),
): ButtonStyleScreenState {
    return rememberSaveable(
        themeState,
        textDemoStateInitial,
        saver = ButtonStyleScreenStateSaver(themeState),
    ) {
        ButtonStyleScreenState(
            themeState = themeState,
            textDemoStateInitial = textDemoStateInitial,
        )
    }
}

@Stable
class ButtonStyleScreenState(
    val themeState: ThemeState,
    textDemoStateInitial: TextDemoState,
) {
    val styles: Styles
        get() = themeState.styles

    val buttonStyles
        get() = styles.buttonStyles

    val buttonStylesByToken = ButtonStyleToken.entries.associateWith { token ->
        token.toStyle(buttonStyles)
    }

    var textDemoState by mutableStateOf(textDemoStateInitial)
        internal set
}

private const val textDemoStateKey = "buttonDemoState"

fun ButtonStyleScreenStateSaver(themeState: ThemeState) = mapSaverSafe(
    save = { state ->
        mapOf(
            textDemoStateKey to save(state.textDemoState, TextDemoStateSaver, this)
        )
    },
    restore = { map ->
        ButtonStyleScreenState(
            themeState = themeState,
            textDemoStateInitial = restore(map[textDemoStateKey], TextDemoStateSaver)!!,
        )
    }
)

@Composable
fun rememberButtonStyleScreenControl(
    state: ButtonStyleScreenState,
    themeController: ThemeController,
): ButtonStyleScreenControl {
    return remember(state, themeController) {
        ButtonStyleScreenControl(state = state, themeController = themeController)
    }
}

@Stable
class ButtonStyleScreenControl(
    val state: ButtonStyleScreenState,
    val themeController: ThemeController,
) {
    val buttonStyleControls = ButtonStyleToken.entries.map { token ->
        makeControlForToken(
            token = token,
            state = state,
            themeController = themeController,
        )
    }

    val textDemoControl = TextDemoControl(state.textDemoState)

    val controls: PersistentList<Control> = persistentListOf(
        *buttonStyleControls.toTypedArray(),
        Control.ControlColumn(
            name = "Demo text controls",
            indent = true,
            controls = { textDemoControl.controls },
            expandedInitial = false,
        )
    )
}

private fun makeControlForToken(
    token: ButtonStyleToken,
    state: ButtonStyleScreenState,
    themeController: ThemeController,
): Control {
    val contentColorControl = enumControl(
        name = "Content color",
        values = { ColorToken.entries },
        selectedValue = { state.buttonStylesByToken[token]!!.contentColor },
        onValueChange = { newValue ->
            val buttonStyles = state.buttonStyles.copy(
                token = token,
                value = state.buttonStylesByToken[token]!!.copy(
                    contentColor = newValue
                )
            )
            val styles = state.styles.copy(
                buttonStyles = buttonStyles,
            )
            themeController.setStyles(styles)
        },
    )

    val containerColorControl = enumControl(
        name = "Container color",
        values = { ColorToken.entries },
        selectedValue = { state.buttonStylesByToken[token]!!.containerColor },
        onValueChange = { newValue ->
            val buttonStyles = state.buttonStyles.copy(
                token = token,
                value = state.buttonStylesByToken[token]!!.copy(
                    containerColor = newValue
                )
            )
            val styles = state.styles.copy(
                buttonStyles = buttonStyles,
            )
            themeController.setStyles(styles)
        },
    )

    val shapeControl = enumControl(
        name = "Shape",
        values = { ShapeToken.entries },
        selectedValue = { state.buttonStylesByToken[token]!!.shape },
        onValueChange = { newValue ->
            val buttonStyles = state.buttonStyles.copy(
                token = token,
                value = state.buttonStylesByToken[token]!!.copy(
                    shape = newValue,
                )
            )
            val styles = state.styles.copy(
                buttonStyles = buttonStyles,
            )
            themeController.setStyles(styles)
        },
    )

    val borderControl = Control.Toggle(
        name = "Border",
        value = { state.buttonStylesByToken[token]!!.border != null },
        onValueChange = { newValue ->
            val border = if (newValue) {
                state.buttonStylesByToken[token]!!.border ?: Border(
                    width = 1.dp,
                    color = ColorToken.Outline,
                )
            } else {
                null
            }
            val buttonStyles = state.buttonStyles.copy(
                token = token,
                value = state.buttonStylesByToken[token]!!.copy(
                    border = border,
                )
            )
            val styles = state.styles.copy(
                buttonStyles = buttonStyles,
            )
            themeController.setStyles(styles)
        },
    )

    val borderColorControl = enumControl(
        name = "Border color",
        values = { ColorToken.entries },
        selectedValue = { state.buttonStylesByToken[token]!!.border?.color ?: ColorToken.Outline },
        onValueChange = { newValue ->
            val border = state.buttonStylesByToken[token]!!.border
            val newBorder = border?.copy(color = newValue)
            val buttonStyles = state.buttonStyles.copy(
                token = token,
                value = state.buttonStylesByToken[token]!!.copy(
                    border = newBorder,
                )
            )
            val styles = state.styles.copy(
                buttonStyles = buttonStyles,
            )
            themeController.setStyles(styles)
        },
    )

    val borderWidthControl = Control.Slider(
        name = "Border width",
        value = { state.buttonStylesByToken[token]!!.border?.width?.value ?: 0f },
        onValueChange = { newValue ->
            val border = state.buttonStylesByToken[token]!!.border
            val newBorder = border?.copy(width = newValue.dp)
            val buttonStyles = state.buttonStyles.copy(
                token = token,
                value = state.buttonStylesByToken[token]!!.copy(
                    border = newBorder,
                )
            )
            val styles = state.styles.copy(
                buttonStyles = buttonStyles,
            )
            themeController.setStyles(styles)
        },
        valueRange = { 0f..100f },
    )

    return Control.ControlColumn(
        name = token.name,
        controls = {
            val borderControls = if (state.buttonStylesByToken[token]!!.border != null) {
                listOf(
                    borderControl,
                    borderColorControl,
                    borderWidthControl,
                )
            } else {
                listOf(borderControl)
            }
            persistentListOf(
                contentColorControl,
                containerColorControl,
                shapeControl,
                *borderControls.toTypedArray()
            )
        },
    )
}
