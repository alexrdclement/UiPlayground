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
import com.alexrdclement.uiplayground.components.core.Surface
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ConfigurationDialogContent(
    configurationController: ConfigurationController,
    onConfigureThemeClick: () -> Unit,
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

    val configureThemeControl = Control.Button(
        name = "Theme",
        onClick = onConfigureThemeClick,
    )

    Surface(
        border = BorderStroke(1.dp, PlaygroundTheme.colorScheme.outline),
    ) {
        Controls(
            controls = persistentListOf(
                colorModeControl,
                configureThemeControl
            ),
            modifier = Modifier.padding(PlaygroundTheme.spacing.medium)
        )
    }
}
