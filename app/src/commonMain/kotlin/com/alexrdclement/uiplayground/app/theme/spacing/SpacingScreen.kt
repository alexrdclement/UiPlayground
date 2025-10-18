package com.alexrdclement.uiplayground.app.theme.spacing

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.alexrdclement.uiplayground.components.demo.Demo
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.components.demo.control.Control
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.components.layout.Scaffold
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.Spacing
import com.alexrdclement.uiplayground.theme.SpacingToken
import com.alexrdclement.uiplayground.theme.control.ThemeController
import com.alexrdclement.uiplayground.theme.control.ThemeState
import com.alexrdclement.uiplayground.theme.copy
import com.alexrdclement.uiplayground.theme.toSpacing
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun SpacingScreen(
    themeController: ThemeController,
    onNavigateBack: () -> Unit,
) {
    val state = rememberSpacingScreenState(themeState = themeController)
    val control = rememberSpacingScreenControl(state = state, themeController = themeController)

    Scaffold(
        topBar = {
            DemoTopBar(
                title = "Spacing",
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
                items(SpacingToken.entries) { spacing ->
                    SpacingDemo(
                        spacing = spacing,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun SpacingDemo(
    modifier: Modifier = Modifier,
    spacing: SpacingToken,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .border(1.dp, PlaygroundTheme.colorScheme.primary)
            .padding(spacing.toSpacing())
            .border(1.dp, PlaygroundTheme.colorScheme.primary)
    ) {
        Text(
            text = spacing.name,
            style = PlaygroundTheme.typography.headline,
        )
    }
}

@Composable
fun rememberSpacingScreenState(
    themeState: ThemeState,
): SpacingScreenState {
    return rememberSaveable(
        themeState,
        saver = SpacingScreenStateSaver(themeState),
    ) {
        SpacingScreenState(
            themeState = themeState,
        )
    }
}

@Stable
class SpacingScreenState(
    val themeState: ThemeState,
) {
    val spacing: Spacing
        get() = themeState.spacing

    val spacingByToken = SpacingToken.entries.associateWith { token ->
        token.toSpacing(spacing)
    }
}

fun SpacingScreenStateSaver(themeState: ThemeState) = mapSaverSafe(
    save = { state ->
        mapOf()
    },
    restore = { map ->
        SpacingScreenState(
            themeState = themeState,
        )
    }
)

@Composable
fun rememberSpacingScreenControl(
    state: SpacingScreenState,
    themeController: ThemeController,
): SpacingScreenControl {
    return remember(state, themeController) {
        SpacingScreenControl(state = state, themeController = themeController)
    }
}

@Stable
class SpacingScreenControl(
    val state: SpacingScreenState,
    val themeController: ThemeController,
) {
    val spacingControls = SpacingToken.entries.map { token ->
        makeControlForToken(
            token = token,
            state = state,
            themeController = themeController,
        )
    }

    val controls: PersistentList<Control> = persistentListOf(
        *spacingControls.toTypedArray(),
    )
}

private fun makeControlForToken(
    token: SpacingToken,
    state: SpacingScreenState,
    themeController: ThemeController,
): Control {
    return Control.Slider(
        name = token.name,
        value = { state.spacingByToken[token]!!.value },
        onValueChange = { radius ->
            val spacing = state.spacing.copy(
                token = token,
                value = radius.dp,
            )
            themeController.setSpacing(spacing)
        },
        valueRange = { 0f..64f },
    )
}
