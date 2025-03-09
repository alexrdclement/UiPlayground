package com.alexrdclement.uiplayground.app.configuration

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.control.Controls
import com.alexrdclement.uiplayground.components.BackNavigationButton
import com.alexrdclement.uiplayground.components.Scaffold
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.components.TopBar
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun ConfigurationScreen(
    onNavigateBack: () -> Unit,
) {
    val configurationController = rememberConfigurationController()

    var colorMode by remember(configurationController) {
        mutableStateOf(configurationController.colorMode)
    }
    val colorModeControl = Control.Dropdown(
        name = "Color mode",
        values = ColorMode.entries.map {
            Control.Dropdown.DropdownItem(
                name = it.name,
                value = it,
            )
        }.toPersistentList(),
        selectedIndex = ColorMode.entries.indexOf(colorMode),
        onValueChange = {
            val newValue = ColorMode.entries[it]
            if (configurationController.setColorMode(newValue)) {
                colorMode = newValue
            }
        }
    )

    Scaffold(
        topBar = {
            TopBar(
                title = { Text("Configuration", style = PlaygroundTheme.typography.titleMedium) },
                navButton = { BackNavigationButton(onNavigateBack) },
            )
        }
    ) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxHeight()
                .padding(innerPadding)
                .padding(horizontal = PlaygroundTheme.spacing.medium)
        ) {
            Controls(
                controls = persistentListOf(
                    colorModeControl,
                )
            )
        }
    }
}
