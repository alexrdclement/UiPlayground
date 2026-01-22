package com.alexrdclement.uiplayground.app.demo

import androidx.compose.foundation.basicMarquee
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.palette.components.core.Text
import com.alexrdclement.palette.components.layout.TopBar
import com.alexrdclement.palette.components.navigation.BackNavigationButton
import com.alexrdclement.palette.theme.PaletteTheme
import com.alexrdclement.uiplayground.app.configuration.ConfigureButton

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
                style = PaletteTheme.styles.text.titleMedium,
                modifier = Modifier.basicMarquee(),
            )
        },
        navButton = navButton,
        actions = actions,
    )
}
