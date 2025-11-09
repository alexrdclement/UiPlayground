package com.alexrdclement.uiplayground.app.theme.color

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.components.core.Button
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.components.demo.Demo
import com.alexrdclement.uiplayground.components.demo.control.Control
import com.alexrdclement.uiplayground.components.layout.Scaffold
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.theme.ColorToken
import com.alexrdclement.uiplayground.theme.PlaygroundDarkColorScheme
import com.alexrdclement.uiplayground.theme.PlaygroundLightColorScheme
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.control.ThemeController
import com.alexrdclement.uiplayground.theme.control.ThemeState
import com.alexrdclement.uiplayground.theme.control.rememberThemeController
import com.alexrdclement.uiplayground.theme.copy
import com.alexrdclement.uiplayground.theme.styles.ButtonStyleToken
import com.alexrdclement.uiplayground.theme.toColor
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ColorScreen(
    themeController: ThemeController,
    onNavigateBack: () -> Unit,
) {
    val state = rememberColorScreenState(themeState = themeController)
    val control = rememberColorScreenControl(state = state, themeController = themeController)

    var selectedColorToken by remember { mutableStateOf<ColorToken?>(null) }

    Scaffold(
        topBar = {
            DemoTopBar(
                title = "Color",
                onNavigateBack = onNavigateBack,
                onConfigureClick = {},
                actions = {},
            )
        },
    ) { paddingValues ->
        Demo(
            controls = control.controls,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(PlaygroundTheme.spacing.medium),
                horizontalAlignment = Alignment.Start,
                contentPadding = PaddingValues(PlaygroundTheme.spacing.medium),
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                items(
                    items = ColorToken.entries,
                    key = { it.name },
                ) { colorToken ->
                    ColorDisplay(
                        label = colorToken.name,
                        color = colorToken.toColor(),
                        onColorClick = { selectedColorToken = colorToken },
                    )
                }
            }
        }
    }

    selectedColorToken?.let { colorToken ->
        ColorPickerDialog(
            colorToken = colorToken,
            onColorSelected = {
                control.onColorSelected(color = it, colorToken = colorToken)
                selectedColorToken = null
            },
            onDismissRequest = {
                selectedColorToken = null
            }
        )
    }
}

@Composable
private fun ColorDisplay(
    label: String,
    color: Color,
    onColorClick: (Color) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(PlaygroundTheme.spacing.medium),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Button(
            style = ButtonStyleToken.Secondary,
            onClick = { onColorClick(color) },
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .width(IntrinsicSize.Min)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
            )
        }
        Text(
            text = label,
            style = PlaygroundTheme.typography.labelMedium,
            modifier = Modifier
                .padding(end = PlaygroundTheme.spacing.medium)
        )
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

    val resetButton = Control.Button(
        name = "Reset",
        onClick = {
            if (themeController.isDarkMode) {
                themeController.setDarkColorScheme(PlaygroundDarkColorScheme)
            } else {
                themeController.setLightColorScheme(PlaygroundLightColorScheme)
            }
        }
    )

    val controls: PersistentList<Control> = persistentListOf(
        isDarkModeControl,
        resetButton,
    )

    fun onColorSelected(color: Color, colorToken: ColorToken) {
        val colorScheme = themeController.colorScheme.copy(
            token = colorToken,
            color = color,
        )
        if (themeController.isDarkMode) {
            themeController.setDarkColorScheme(colorScheme = colorScheme)
        } else {
            themeController.setLightColorScheme(colorScheme = colorScheme)
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PlaygroundTheme {
        ColorScreen(
            themeController = rememberThemeController(),
            onNavigateBack = {},
        )
    }
}
