package com.alexrdclement.uiplayground.app.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.components.core.ButtonDemo
import com.alexrdclement.uiplayground.app.demo.components.core.ButtonDemoControl
import com.alexrdclement.uiplayground.app.demo.components.core.ButtonDemoState
import com.alexrdclement.uiplayground.app.demo.components.core.ButtonDemoStateSaver
import com.alexrdclement.uiplayground.app.demo.components.core.TextDemo
import com.alexrdclement.uiplayground.app.demo.components.core.TextDemoControl
import com.alexrdclement.uiplayground.app.demo.components.core.TextDemoState
import com.alexrdclement.uiplayground.app.demo.components.core.TextDemoStateSaver
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.components.util.restore
import com.alexrdclement.uiplayground.components.util.save
import com.alexrdclement.uiplayground.theme.FontFamily
import com.alexrdclement.uiplayground.theme.PlaygroundIndicationType
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.PlaygroundTypographyDefaults
import com.alexrdclement.uiplayground.theme.control.ThemeControl
import com.alexrdclement.uiplayground.theme.control.ThemeState
import com.alexrdclement.uiplayground.theme.makePlaygroundTypography
import com.alexrdclement.uiplayground.theme.toComposeFontFamily
import com.alexrdclement.uiplayground.theme.toFontFamily
import com.alexrdclement.uiplayground.theme.toIndication
import com.alexrdclement.uiplayground.theme.toPlaygroundIndicationType
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ThemeScreen(
    themeControl: ThemeControl,
) {
    val state = rememberThemeScreenState(themeState = themeControl)
    val control = rememberThemeScreenControl(state = state, themeControl = themeControl)

    Demo(
        controls = control.controls,
        modifier = Modifier.fillMaxSize()
    ) {
        val density = LocalDensity.current
        LaunchedEffect(this@Demo.maxWidth, density) {
            with(density) { control.onSizeChanged(this@Demo.maxWidth) }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = PlaygroundTheme.spacing.medium,
                alignment = Alignment.CenterVertically,
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            this@Demo.TextDemo(
                state = state.textDemoState,
                control = control.textDemoControl,
                modifier = Modifier
            )
            this@Demo.ButtonDemo(
                state = state.buttonDemoState,
                control = control.buttonDemoControl,
                modifier = Modifier
            )
        }
    }
}

@Composable
fun rememberThemeScreenState(
    themeState: ThemeState,
    textDemoStateInitial: TextDemoState = TextDemoState(
        initialText = "The quick brown fox jumps over the lazy dog",
    ),
    buttonDemoStateInitial: ButtonDemoState = ButtonDemoState(),
): ThemeScreenState {
    return rememberSaveable(
        themeState,
        textDemoStateInitial,
        buttonDemoStateInitial,
        saver = ThemeScreenStateSaver(themeState),
    ) {
        ThemeScreenState(
            themeState = themeState,
            textDemoStateInitial = textDemoStateInitial,
            buttonDemoStateInitial = buttonDemoStateInitial,
        )
    }
}

@Stable
class ThemeScreenState(
    val themeState: ThemeState,
    textDemoStateInitial: TextDemoState,
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

    var textDemoState by mutableStateOf(textDemoStateInitial)
        internal set

    var buttonDemoState by mutableStateOf(buttonDemoStateInitial)
        internal set
}

private const val textDemoStateKey = "textDemoState"
private const val buttonDemoStateKey = "buttonDemoState"

fun ThemeScreenStateSaver(themeState: ThemeState) = mapSaverSafe(
    save = { state ->
        mapOf(
            textDemoStateKey to save(state.textDemoState, TextDemoStateSaver, this),
            buttonDemoStateKey to save(state.buttonDemoState, ButtonDemoStateSaver, this)
        )
    },
    restore = { map ->
        ThemeScreenState(
            themeState = themeState,
            textDemoStateInitial = restore(map[textDemoStateKey], TextDemoStateSaver)!!,
            buttonDemoStateInitial = restore(map[buttonDemoStateKey], ButtonDemoStateSaver)!!,
        )
    }
)

@Composable
fun rememberThemeScreenControl(
    state: ThemeScreenState,
    themeControl: ThemeControl,
): ThemeScreenControl {
//    return remember(state, themeControl) {
//        ThemeScreenControl(state = state, themeControl = themeControl)
//    }
    return ThemeScreenControl(state = state, themeControl = themeControl)
}

@Stable
class ThemeScreenControl(
    val state: ThemeScreenState,
    val themeControl: ThemeControl,
) {
    val fontFamilyControl = Control.Dropdown(
        name = "Font family",
        values = {
            FontFamily.entries.map {
                Control.Dropdown.DropdownItem(
                    name = it.name,
                    value = it,
                )
            }.toPersistentList()
        },
        selectedIndex = { FontFamily.entries.indexOf(state.fontFamily) },
        onValueChange = {
            val newValue = FontFamily.entries[it]
            val typography = makePlaygroundTypography(
                fontFamily = newValue.toComposeFontFamily(),
            )
            themeControl.setTypography(typography)
        }
    )

    val indicationControl = Control.Dropdown(
        name = "Indication",
        values = {
            PlaygroundIndicationType.entries.map {
                Control.Dropdown.DropdownItem(
                    name = it.name,
                    value = it,
                )
            }.toPersistentList()
        },
        selectedIndex = { PlaygroundIndicationType.entries.indexOf(state.indicationType) },
        onValueChange = {
            val newValue = PlaygroundIndicationType.entries[it]
            themeControl.setIndication(newValue.toIndication())
        }
    )

    val textDemoControl = TextDemoControl(state = state.textDemoState)

    val buttonDemoControl = ButtonDemoControl(state = state.buttonDemoState)

    val controls: PersistentList<Control> = persistentListOf(
        fontFamilyControl,
        indicationControl,
        Control.ControlColumn(
            name = "Demo text",
            indent = true,
            controls = { textDemoControl.controls },
        ),
        Control.ControlColumn(
            name = "Demo button",
            indent = true,
            controls = { buttonDemoControl.controls },
        )
    )

    fun onSizeChanged(width: Dp) {
        textDemoControl.onSizeChanged(width)
        buttonDemoControl.onSizeChanged(width)
    }
}
