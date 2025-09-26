package com.alexrdclement.uiplayground.app.theme

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.components.core.ButtonDemo
import com.alexrdclement.uiplayground.app.demo.components.core.ButtonDemoControl
import com.alexrdclement.uiplayground.app.demo.components.core.ButtonDemoState
import com.alexrdclement.uiplayground.app.demo.components.core.ButtonDemoStateSaver
import com.alexrdclement.uiplayground.app.demo.components.core.TextStyle
import com.alexrdclement.uiplayground.app.demo.components.core.toCompose
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.control.enumControl
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.components.layout.Scaffold
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.components.util.restore
import com.alexrdclement.uiplayground.components.util.save
import com.alexrdclement.uiplayground.theme.FontFamily
import com.alexrdclement.uiplayground.theme.PlaygroundIndicationType
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.PlaygroundTypographyDefaults
import com.alexrdclement.uiplayground.theme.control.ThemeController
import com.alexrdclement.uiplayground.theme.control.ThemeState
import com.alexrdclement.uiplayground.theme.makePlaygroundTypography
import com.alexrdclement.uiplayground.theme.toComposeFontFamily
import com.alexrdclement.uiplayground.theme.toFontFamily
import com.alexrdclement.uiplayground.theme.toIndication
import com.alexrdclement.uiplayground.theme.toPlaygroundIndicationType
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ThemeScreen(
    themeController: ThemeController,
    onNavigateBack: () -> Unit,
) {
    val state = rememberThemeScreenState(themeState = themeController)
    val control = rememberThemeScreenControl(state = state, themeController = themeController)

    Scaffold(
        topBar = {
            DemoTopBar(
                title = "Theme",
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = PlaygroundTheme.spacing.medium)
            ) {
                items(TextStyle.entries) { textStyle ->
                    Column(
                        verticalArrangement = Arrangement.spacedBy(PlaygroundTheme.spacing.medium),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = textStyle.name,
                            style = PlaygroundTheme.typography.labelSmall,
                            modifier = Modifier
                                .border(1.dp, PlaygroundTheme.colorScheme.outline)
                                .padding(PlaygroundTheme.spacing.xs)
                        )
                        Text(
                            text = "The quick brown fox jumps over the lazy dog",
                            style = textStyle.toCompose(),
                        )
                    }
                }
                item {
                    this@Demo.ButtonDemo(
                        state = state.buttonDemoState,
                        control = control.buttonDemoControl,
                    )
                }
            }
        }
    }
}

@Composable
fun rememberThemeScreenState(
    themeState: ThemeState,
    buttonDemoStateInitial: ButtonDemoState = ButtonDemoState(),
): ThemeScreenState {
    return rememberSaveable(
        themeState,
        buttonDemoStateInitial,
        saver = ThemeScreenStateSaver(themeState),
    ) {
        ThemeScreenState(
            themeState = themeState,
            buttonDemoStateInitial = buttonDemoStateInitial,
        )
    }
}

@Stable
class ThemeScreenState(
    val themeState: ThemeState,
    buttonDemoStateInitial: ButtonDemoState,
) {
    val fontFamily: FontFamily
        get() {
            val composeFontFamily = themeState.typography.headline.fontFamily
                ?: PlaygroundTypographyDefaults.fontFamily
            return composeFontFamily.toFontFamily()
        }

    val indicationType
        get() = themeState.indication.toPlaygroundIndicationType()

    var buttonDemoState by mutableStateOf(buttonDemoStateInitial)
        internal set
}

private const val buttonDemoStateKey = "buttonDemoState"

fun ThemeScreenStateSaver(themeState: ThemeState) = mapSaverSafe(
    save = { state ->
        mapOf(
            buttonDemoStateKey to save(state.buttonDemoState, ButtonDemoStateSaver, this)
        )
    },
    restore = { map ->
        ThemeScreenState(
            themeState = themeState,
            buttonDemoStateInitial = restore(map[buttonDemoStateKey], ButtonDemoStateSaver)!!,
        )
    }
)

@Composable
fun rememberThemeScreenControl(
    state: ThemeScreenState,
    themeController: ThemeController,
): ThemeScreenControl {
    return remember(state, themeController) {
        ThemeScreenControl(state = state, themeController = themeController)
    }
}

@Stable
class ThemeScreenControl(
    val state: ThemeScreenState,
    val themeController: ThemeController,
) {
    val fontFamilyControl = enumControl(
        name = "Font family",
        values = { FontFamily.entries },
        selectedValue = { state.fontFamily },
        onValueChange = {
            val typography = makePlaygroundTypography(
                fontFamily = it.toComposeFontFamily(),
            )
            themeController.setTypography(typography)
        }
    )

    val indicationControl = enumControl(
        name = "Indication",
        values = { PlaygroundIndicationType.entries },
        selectedValue = { state.indicationType },
        onValueChange = { themeController.setIndication(it.toIndication()) },
    )

    val buttonDemoControl = ButtonDemoControl(state = state.buttonDemoState)

    val controls: PersistentList<Control> = persistentListOf(
        fontFamilyControl,
        indicationControl,
        Control.ControlColumn(
            name = "Demo button controls",
            indent = true,
            controls = { buttonDemoControl.controls },
            expandedInitial = false,
        )
    )
}
