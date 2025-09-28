package com.alexrdclement.uiplayground.app.theme.color

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.components.layout.Scaffold
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
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
            Column(
                verticalArrangement = Arrangement.spacedBy(PlaygroundTheme.spacing.medium),
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .fillMaxHeight()
                    .padding(PlaygroundTheme.spacing.medium)
            ) {
                ColorDisplay(
                    label = "Primary",
                    color = PlaygroundTheme.colorScheme.primary,
                )
                ColorDisplay(
                    label = "On Primary",
                    color = PlaygroundTheme.colorScheme.onPrimary,
                )
                ColorDisplay(
                    label = "Secondary",
                    color = PlaygroundTheme.colorScheme.secondary,
                )
                ColorDisplay(
                    label = "On Secondary",
                    color = PlaygroundTheme.colorScheme.onSecondary,
                )
                ColorDisplay(
                    label = "Background",
                    color = PlaygroundTheme.colorScheme.background,
                )
                ColorDisplay(
                    label = "On Background",
                    color = PlaygroundTheme.colorScheme.onBackground,
                )
                ColorDisplay(
                    label = "Surface",
                    color = PlaygroundTheme.colorScheme.surface,
                )
                ColorDisplay(
                    label = "On Surface",
                    color = PlaygroundTheme.colorScheme.onSurface,
                )
                ColorDisplay(
                    label = "Outline",
                    color = PlaygroundTheme.colorScheme.outline,
                )
            }
        }
    }
}

@Composable
private fun ColorDisplay(
    label: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            style = PlaygroundTheme.typography.labelMedium,
            modifier = Modifier
                .weight(1f)
                .padding(end = PlaygroundTheme.spacing.medium)
        )
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(color = color, shape = RectangleShape)
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

    val controls: PersistentList<Control> = persistentListOf(
        isDarkModeControl,
    )
}
