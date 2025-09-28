package com.alexrdclement.uiplayground.app.theme.color

import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.components.layout.Scaffold
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.theme.control.ThemeController
import com.alexrdclement.uiplayground.theme.control.ThemeState
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
            
        }
    }
}

@Composable
fun rememberColorScreenState(
    themeState: ThemeState,
): ColorScreenState {
    return rememberSaveable(
        themeState,
        saver = ColorScreenStateSaver(themeState),
    ) {
        ColorScreenState(
            themeState = themeState,
        )
    }
}

@Stable
class ColorScreenState(
    val themeState: ThemeState,
) {
    val isDarkMode: Boolean
        get() = themeState.isDarkMode
}

fun ColorScreenStateSaver(themeState: ThemeState) = mapSaverSafe(
    save = { state ->
        mapOf()
    },
    restore = { map ->
        ColorScreenState(
            themeState = themeState,
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
    val isDarkModeControl = Control.Toggle(
        name = "Dark mode",
        value = { state.isDarkMode },
        onValueChange = {
            themeController.setIsDarkMode(it)
        }
    )

    val controls: PersistentList<Control> = persistentListOf(
        isDarkModeControl,
    )
}
