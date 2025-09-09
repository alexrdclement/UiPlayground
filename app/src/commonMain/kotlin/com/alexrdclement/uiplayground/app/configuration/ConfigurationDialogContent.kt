package com.alexrdclement.uiplayground.app.configuration

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.control.Controls
import com.alexrdclement.uiplayground.components.Surface
import com.alexrdclement.uiplayground.theme.FontFamily
import com.alexrdclement.uiplayground.theme.PlaygroundIndicationType
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.alexrdclement.uiplayground.theme.PlaygroundTypographyDefaults
import com.alexrdclement.uiplayground.theme.makePlaygroundTypography
import com.alexrdclement.uiplayground.theme.toComposeFontFamily
import com.alexrdclement.uiplayground.theme.toFontFamily
import com.alexrdclement.uiplayground.theme.toIndication
import com.alexrdclement.uiplayground.theme.toPlaygroundIndicationType
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ConfigurationDialogContent(
    configurationController: ConfigurationController,
) {
    var colorMode by remember(configurationController) {
        mutableStateOf(configurationController.colorMode)
    }
    val colorModeControl = Control.Dropdown(
        name = "Color mode",
        values = {
            ColorMode.entries.map {
                Control.Dropdown.DropdownItem(
                    name = it.name,
                    value = it,
                )
            }.toPersistentList()
        },
        selectedIndex = { ColorMode.entries.indexOf(colorMode) },
        onValueChange = {
            val newValue = ColorMode.entries[it]
            if (configurationController.setColorMode(newValue)) {
                colorMode = newValue
            }
        }
    )

    var fontFamily by remember(configurationController) {
        val composeFontFamily = configurationController.typography.headline.fontFamily
            ?: PlaygroundTypographyDefaults.fontFamily
        mutableStateOf(composeFontFamily.toFontFamily())
    }
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
        selectedIndex = { FontFamily.entries.indexOf(fontFamily) },
        onValueChange = {
            val newValue = FontFamily.entries[it]
            val typography = makePlaygroundTypography(
                fontFamily = newValue.toComposeFontFamily(),
            )
            if (configurationController.setTypography(typography)) {
                fontFamily = newValue
            }
        }
    )

    var indicationType by remember(configurationController) {
        mutableStateOf(configurationController.indication.toPlaygroundIndicationType())
    }
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
        selectedIndex = { PlaygroundIndicationType.entries.indexOf(indicationType) },
        onValueChange = {
            val newValue = PlaygroundIndicationType.entries[it]
            if (configurationController.setIndication(newValue.toIndication())) {
                indicationType = newValue
            }
        }
    )

    Surface(
        border = BorderStroke(1.dp, PlaygroundTheme.colorScheme.outline),
    ) {
        Controls(
            controls = persistentListOf(
                colorModeControl,
                fontFamilyControl,
                indicationControl,
            ),
            modifier = Modifier.padding(PlaygroundTheme.spacing.medium)
        )
    }
}
