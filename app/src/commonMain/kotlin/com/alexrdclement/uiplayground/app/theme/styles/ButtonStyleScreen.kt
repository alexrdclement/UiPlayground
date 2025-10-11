package com.alexrdclement.uiplayground.app.theme.styles

import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.components.core.ButtonDemo
import com.alexrdclement.uiplayground.app.demo.components.core.ButtonDemoControl
import com.alexrdclement.uiplayground.app.demo.components.core.ButtonDemoState
import com.alexrdclement.uiplayground.app.demo.components.core.ButtonDemoStateSaver
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.control.enumControl
import com.alexrdclement.uiplayground.components.layout.Scaffold
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.components.util.restore
import com.alexrdclement.uiplayground.components.util.save
import com.alexrdclement.uiplayground.theme.ColorToken
import com.alexrdclement.uiplayground.theme.ShapeToken
import com.alexrdclement.uiplayground.theme.Styles
import com.alexrdclement.uiplayground.theme.control.ThemeController
import com.alexrdclement.uiplayground.theme.control.ThemeState
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
            this@Demo.ButtonDemo(
                state = state.buttonDemoState,
                control = control.buttonDemoControl,
            )
        }
    }
}

@Composable
fun rememberButtonStyleScreenState(
    themeState: ThemeState,
    buttonDemoStateInitial: ButtonDemoState = ButtonDemoState(),
): ButtonStyleScreenState {
    return rememberSaveable(
        themeState,
        buttonDemoStateInitial,
        saver = ButtonStyleScreenStateSaver(themeState),
    ) {
        ButtonStyleScreenState(
            themeState = themeState,
            buttonDemoStateInitial = buttonDemoStateInitial,
        )
    }
}

@Stable
class ButtonStyleScreenState(
    val themeState: ThemeState,
    buttonDemoStateInitial: ButtonDemoState,
) {
    val styles: Styles
        get() = themeState.styles

    val buttonStyles
        get() = styles.buttonStyles

    val buttonStylesByToken = ButtonStyleToken.entries.associateWith { token ->
        token.toStyle(buttonStyles)
    }

    var buttonDemoState by mutableStateOf(buttonDemoStateInitial)
        internal set
}

private const val buttonDemoStateKey = "buttonDemoState"

fun ButtonStyleScreenStateSaver(themeState: ThemeState) = mapSaverSafe(
    save = { state ->
        mapOf(
            buttonDemoStateKey to save(state.buttonDemoState, ButtonDemoStateSaver, this)
        )
    },
    restore = { map ->
        ButtonStyleScreenState(
            themeState = themeState,
            buttonDemoStateInitial = restore(map[buttonDemoStateKey], ButtonDemoStateSaver)!!,
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

    val buttonDemoControl = ButtonDemoControl(state = state.buttonDemoState)

    val controls: PersistentList<Control> = persistentListOf(
        *buttonStyleControls.toTypedArray(),
        Control.ControlColumn(
            name = "Demo button controls",
            indent = true,
            controls = { buttonDemoControl.controls },
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

    val borderWidthControl = Control.Slider(
        name = "Border width",
        value = { state.buttonStylesByToken[token]!!.border?.width?.value ?: 0f },
        onValueChange = { newValue ->
            val border = state.buttonStylesByToken[token]!!.border
            val newBorder = if (border != null) {
                border.copy(width = newValue.dp)
            } else {
                null
            }
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
        valueRange = { 0f..10f },
    )

    return Control.ControlColumn(
        name = token.name,
        controls = {
            persistentListOf(
                contentColorControl,
                containerColorControl,
                shapeControl,
                borderWidthControl,
            )
        },
    )
}
