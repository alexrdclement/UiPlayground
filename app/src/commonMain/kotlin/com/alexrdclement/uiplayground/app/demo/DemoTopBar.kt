package com.alexrdclement.uiplayground.app.demo

import androidx.compose.foundation.basicMarquee
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.embarrasdf.palette.components.core.Text
import com.embarrasdf.palette.theme.components.layout.TopBar
import com.embarrasdf.palette.theme.components.navigation.BackNavigationButton
import com.embarrasdf.palette.theme.PaletteTheme
import com.alexrdclement.uiplayground.app.configuration.ConfigureButton

@Composable
fun DemoTopBar(
    title: String,
    onNavigateUp: () -> Unit,
    onConfigureClick: () -> Unit,
    navButton: @Composable () -> Unit = {
        BackNavigationButton(onNavigateUp)
    },
    actions: @Composable () -> Unit = {
        ConfigureButton(onClick = onConfigureClick)
    }
) {
    TopBar(
        title = {
            Text(
                text = title,
                style = PaletteTheme.component.core.text.titleMedium,
                modifier = Modifier.basicMarquee(),
            )
        },
        navButton = navButton,
        actions = actions,
    )
}
