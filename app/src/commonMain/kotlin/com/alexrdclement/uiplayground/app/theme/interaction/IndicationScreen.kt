package com.alexrdclement.uiplayground.app.theme.interaction

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
import com.alexrdclement.uiplayground.components.demo.Demo
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.components.core.ButtonDemo
import com.alexrdclement.uiplayground.app.demo.components.core.ButtonDemoControl
import com.alexrdclement.uiplayground.app.demo.components.core.ButtonDemoState
import com.alexrdclement.uiplayground.app.demo.components.core.ButtonDemoStateSaver
import com.alexrdclement.uiplayground.components.demo.control.Control
import com.alexrdclement.uiplayground.components.demo.control.enumControl
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
fun IndicationScreen(
    themeController: ThemeController,
    onNavigateBack: () -> Unit,
) {
    val state = rememberIndicationScreenState(themeState = themeController)
    val control = rememberIndicationScreenControl(state = state, themeController = themeController)

    Scaffold(
        topBar = {
            DemoTopBar(
                title = "Indication",
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
fun rememberIndicationScreenState(
    themeState: ThemeState,
    buttonDemoStateInitial: ButtonDemoState = ButtonDemoState(),
): IndicationScreenState {
    return rememberSaveable(
        themeState,
        buttonDemoStateInitial,
        saver = IndicationScreenStateSaver(themeState),
    ) {
        IndicationScreenState(
            themeState = themeState,
            buttonDemoStateInitial = buttonDemoStateInitial,
        )
    }
}

@Stable
class IndicationScreenState(
    val themeState: ThemeState,
    buttonDemoStateInitial: ButtonDemoState,
) {
    val indicationType
        get() = themeState.indication.toPlaygroundIndicationType()

    var buttonDemoState by mutableStateOf(buttonDemoStateInitial)
        internal set
}

private const val buttonDemoStateKey = "buttonDemoState"

fun IndicationScreenStateSaver(themeState: ThemeState) = mapSaverSafe(
    save = { state ->
        mapOf(
            buttonDemoStateKey to save(state.buttonDemoState, ButtonDemoStateSaver, this)
        )
    },
    restore = { map ->
        IndicationScreenState(
            themeState = themeState,
            buttonDemoStateInitial = restore(map[buttonDemoStateKey], ButtonDemoStateSaver)!!,
        )
    }
)

@Composable
fun rememberIndicationScreenControl(
    state: IndicationScreenState,
    themeController: ThemeController,
): IndicationScreenControl {
    return remember(state, themeController) {
        IndicationScreenControl(state = state, themeController = themeController)
    }
}

@Stable
class IndicationScreenControl(
    val state: IndicationScreenState,
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
