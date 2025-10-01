package com.alexrdclement.uiplayground.app.theme.typography

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.components.core.TextStyle
import com.alexrdclement.uiplayground.app.demo.components.core.toCompose
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.control.enumControl
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.components.layout.Scaffold
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.theme.FontFamily
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.PlaygroundTypographyDefaults
import com.alexrdclement.uiplayground.theme.control.ThemeController
import com.alexrdclement.uiplayground.theme.control.ThemeState
import com.alexrdclement.uiplayground.theme.makePlaygroundTypography
import com.alexrdclement.uiplayground.theme.toComposeFontFamily
import com.alexrdclement.uiplayground.theme.toFontFamily
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun TypographyScreen(
    themeController: ThemeController,
    onNavigateBack: () -> Unit,
) {
    val state = rememberTypographyScreenState(themeState = themeController)
    val control = rememberTypographyScreenControl(state = state, themeController = themeController)

    Scaffold(
        topBar = {
            DemoTopBar(
                title = "Typography",
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
            }
        }
    }
}

@Composable
fun rememberTypographyScreenState(
    themeState: ThemeState,
): TypographyScreenState {
    return rememberSaveable(
        themeState,
        saver = TypographyScreenStateSaver(themeState),
    ) {
        TypographyScreenState(
            themeState = themeState,
        )
    }
}

@Stable
class TypographyScreenState(
    val themeState: ThemeState,
) {
    val fontFamily: FontFamily
        get() {
            val composeFontFamily = themeState.typography.headline.fontFamily
                ?: PlaygroundTypographyDefaults.fontFamily
            return composeFontFamily.toFontFamily()
        }
}

fun TypographyScreenStateSaver(themeState: ThemeState) = mapSaverSafe(
    save = { state ->
        mapOf()
    },
    restore = { map ->
        TypographyScreenState(
            themeState = themeState,
        )
    }
)

@Composable
fun rememberTypographyScreenControl(
    state: TypographyScreenState,
    themeController: ThemeController,
): TypographyScreenControl {
    return remember(state, themeController) {
        TypographyScreenControl(state = state, themeController = themeController)
    }
}

@Stable
class TypographyScreenControl(
    val state: TypographyScreenState,
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

    val controls: PersistentList<Control> = persistentListOf(
        fontFamilyControl,
    )
}
