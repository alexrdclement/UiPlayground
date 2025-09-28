package com.alexrdclement.uiplayground.app.theme.color

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
import com.alexrdclement.uiplayground.theme.PlaygroundIndicationType
import com.alexrdclement.uiplayground.theme.control.ThemeController
import com.alexrdclement.uiplayground.theme.control.ThemeState
import com.alexrdclement.uiplayground.theme.toIndication
import com.alexrdclement.uiplayground.theme.toPlaygroundIndicationType
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ColorScreen(
    themeController: ThemeController,
    onNavigateBack: () -> Unit,
) {
    val state = rememberColorScreenState(themeState = themeController)
    val control = rememberColorScreenControl(state = state, themeController = themeController)

    Scaffold(
        topBar = {
            DemoTopBar(
                title = "Color",
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
fun rememberColorScreenState(
    themeState: ThemeState,
    buttonDemoStateInitial: ButtonDemoState = ButtonDemoState(),
): ColorScreenState {
    return rememberSaveable(
        themeState,
        buttonDemoStateInitial,
        saver = ColorScreenStateSaver(themeState),
    ) {
        ColorScreenState(
            themeState = themeState,
            buttonDemoStateInitial = buttonDemoStateInitial,
        )
    }
}

@Stable
class ColorScreenState(
    val themeState: ThemeState,
    buttonDemoStateInitial: ButtonDemoState,
) {
    val indicationType
        get() = themeState.indication.toPlaygroundIndicationType()

    var buttonDemoState by mutableStateOf(buttonDemoStateInitial)
        internal set
}

private const val buttonDemoStateKey = "buttonDemoState"

fun ColorScreenStateSaver(themeState: ThemeState) = mapSaverSafe(
    save = { state ->
        mapOf(
            buttonDemoStateKey to save(state.buttonDemoState, ButtonDemoStateSaver, this)
        )
    },
    restore = { map ->
        ColorScreenState(
            themeState = themeState,
            buttonDemoStateInitial = restore(map[buttonDemoStateKey], ButtonDemoStateSaver)!!,
        )
    }
)

@Composable
fun rememberColorScreenControl(
    state: ColorScreenState,
    themeController: ThemeController,
): ColorScreenControl {
    return remember(state, themeController) {
        ColorScreenControl(state = state, themeController = themeController)
    }
}

@Stable
class ColorScreenControl(
    val state: ColorScreenState,
    val themeController: ThemeController,
) {
    val indicationControl = enumControl(
        name = "Indication",
        values = { PlaygroundIndicationType.entries },
        selectedValue = { state.indicationType },
        onValueChange = { themeController.setIndication(it.toIndication()) },
    )

    val buttonDemoControl = ButtonDemoControl(state = state.buttonDemoState)

    val controls: PersistentList<Control> = persistentListOf(
        indicationControl,
        Control.ControlColumn(
            name = "Demo button controls",
            indent = true,
            controls = { buttonDemoControl.controls },
            expandedInitial = false,
        )
    )
}
