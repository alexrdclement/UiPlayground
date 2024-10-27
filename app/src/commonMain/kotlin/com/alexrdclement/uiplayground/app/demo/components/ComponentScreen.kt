package com.alexrdclement.uiplayground.app.demo.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.demo.components.demo.MediaControlSheetDemo
import com.alexrdclement.uiplayground.components.BackNavigationButton
import com.alexrdclement.uiplayground.components.Scaffold
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.components.TopBar
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun ComponentScreen(
    component: Component,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(
                title = { Text(component.title, style = PlaygroundTheme.typography.titleMedium) },
                navButton = { BackNavigationButton(onNavigateBack) },
            )
        },
    ) { innerPadding ->
        when (component) {
            Component.MediaControlSheet -> MediaControlSheetDemo(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
