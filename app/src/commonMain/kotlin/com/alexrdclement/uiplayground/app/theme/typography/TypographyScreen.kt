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
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.control.enumControl
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.components.layout.Scaffold
import com.alexrdclement.uiplayground.components.util.mapSaverSafe
import com.alexrdclement.uiplayground.components.util.restore
import com.alexrdclement.uiplayground.components.util.save
import com.alexrdclement.uiplayground.theme.FontFamily
import com.alexrdclement.uiplayground.theme.FontStyle
import com.alexrdclement.uiplayground.theme.FontWeight
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.PlaygroundTypographyDefaults
import com.alexrdclement.uiplayground.theme.TypographyToken
import com.alexrdclement.uiplayground.theme.control.ThemeController
import com.alexrdclement.uiplayground.theme.control.ThemeState
import com.alexrdclement.uiplayground.theme.copy
import com.alexrdclement.uiplayground.theme.toComposeFontFamily
import com.alexrdclement.uiplayground.theme.toComposeFontStyle
import com.alexrdclement.uiplayground.theme.toComposeFontWeight
import com.alexrdclement.uiplayground.theme.toFontFamily
import com.alexrdclement.uiplayground.theme.toFontStyle
import com.alexrdclement.uiplayground.theme.toFontWeight
import com.alexrdclement.uiplayground.theme.toTextStyle
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
                items(TypographyToken.entries) { textStyle ->
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
                            text = state.text,
                            style = textStyle.toTextStyle(),
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
    initialText: String = "The quick brown fox jumps over the lazy dog",
): TypographyScreenState {
    val textFieldState = rememberTextFieldState(initialText = initialText)
    return rememberSaveable(
        themeState,
        saver = TypographyScreenStateSaver(themeState),
    ) {
        TypographyScreenState(
            themeState = themeState,
            textFieldState = textFieldState,
        )
    }
}

@Stable
class TypographyScreenState(
    val themeState: ThemeState,
    val textFieldState: TextFieldState,
) {
    val typography
        get() = themeState.typography

    val text: String
        get() = textFieldState.text.toString()
}

private val textFieldKey = "textField"

fun TypographyScreenStateSaver(themeState: ThemeState) = mapSaverSafe(
    save = { state ->
        mapOf(
            textFieldKey to save(state.textFieldState, TextFieldState.Saver, this),
        )
    },
    restore = { map ->
        TypographyScreenState(
            themeState = themeState,
            textFieldState = restore(map[textFieldKey], TextFieldState.Saver)!!,
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
    val textFieldControl = Control.TextField(
        name = "Sample text",
        includeLabel = false,
        textFieldState = state.textFieldState,
    )

    val tokenControls = TypographyToken.entries.map {
        makeControlForToken(it, state, themeController)
    }

    val controls: PersistentList<Control> = persistentListOf(
        textFieldControl,
        *tokenControls.toTypedArray(),
    )
}

private fun makeControlForToken(
    token: TypographyToken,
    state: TypographyScreenState,
    themeController: ThemeController,
): Control {
    val textStyle = token.toTextStyle(state.typography)

    val composeFontFamily = textStyle.fontFamily ?: PlaygroundTypographyDefaults.fontFamily
    val fontFamilyControl = enumControl(
        name = "Font family",
        values = { FontFamily.entries },
        selectedValue = { composeFontFamily.toFontFamily() },
        onValueChange = {
            val typography = state.typography.copy(
                token = token,
                textStyle = textStyle.copy(
                    fontFamily = it.toComposeFontFamily(),
                )
            )
            themeController.setTypography(typography)
        }
    )

    val fontSizeControl = Control.Slider(
        name = "Font size",
        value = { textStyle.fontSize.value },
        onValueChange = { newSize: Float ->
            val typography = state.typography.copy(
                token = token,
                textStyle = textStyle.copy(
                    fontSize = TextUnit(newSize, TextUnitType.Sp)
                )
            )
            themeController.setTypography(typography)
        },
        valueRange = { 8f..200f },
    )

    val fontWeightControl = enumControl(
        name = "Font weight",
        values = { FontWeight.entries },
        selectedValue = { textStyle.fontWeight?.toFontWeight() ?: FontWeight.Normal },
        onValueChange = {
            val typography = state.typography.copy(
                token = token,
                textStyle = textStyle.copy(
                    fontWeight = it.toComposeFontWeight(),
                )
            )
            themeController.setTypography(typography)
        }
    )

    val fontStyleControl = enumControl(
        name = "Font style",
        values = { FontStyle.entries },
        selectedValue = { textStyle.fontStyle?.toFontStyle() ?: FontStyle.Normal },
        onValueChange = {
            val typography = state.typography.copy(
                token = token,
                textStyle = textStyle.copy(
                    fontStyle = it.toComposeFontStyle(),
                )
            )
            themeController.setTypography(typography)
        },
    )

    val lineHeightControl = Control.Slider(
        name = "Line height",
        value = { textStyle.lineHeight.value },
        onValueChange = { newSize: Float ->
            val typography = state.typography.copy(
                token = token,
                textStyle = textStyle.copy(
                    lineHeight = TextUnit(newSize, TextUnitType.Sp)
                )
            )
            themeController.setTypography(typography)
        },
        valueRange = { 8f..200f },
    )

    val letterSpacingControl = Control.Slider(
        name = "Letter spacing",
        value = { textStyle.letterSpacing.value },
        onValueChange = { newSize: Float ->
            val typography = state.typography.copy(
                token = token,
                textStyle = textStyle.copy(
                    letterSpacing = TextUnit(newSize, TextUnitType.Sp)
                )
            )
            themeController.setTypography(typography)
        },
        valueRange = { -10f..10f },
    )

    return Control.ControlColumn(
        name = token.name,
        controls = {
            persistentListOf(
                fontFamilyControl,
                fontWeightControl,
                fontStyleControl,
                fontSizeControl,
                lineHeightControl,
                letterSpacingControl,
            )
        }
    )
}
