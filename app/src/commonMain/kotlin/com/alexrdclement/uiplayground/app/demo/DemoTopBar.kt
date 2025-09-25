package com.alexrdclement.uiplayground.app.demo

import androidx.compose.foundation.basicMarquee
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.configuration.ConfigureButton
import com.alexrdclement.uiplayground.components.core.Text
import com.alexrdclement.uiplayground.components.layout.TopBar
import com.alexrdclement.uiplayground.components.navigation.BackNavigationButton
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun DemoTopBar(
    title: String,
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
    navButton: @Composable () -> Unit = {
        BackNavigationButton(onNavigateBack)
    },
    actions: @Composable () -> Unit = {
        ConfigureButton(onClick = onConfigureClick)
    }
) {
    TopBar(
        title = {
            Text(
                text = title,
                style = PlaygroundTheme.typography.titleMedium,
                modifier = Modifier.basicMarquee(),
            )
        },
        navButton = navButton,
        actions = actions,
    )
}
