package com.alexrdclement.uiplayground.app.demo.components.media

import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.components.media.navigation.MediaComponent
import com.alexrdclement.uiplayground.components.layout.Scaffold

@Composable
fun MediaComponentScreen(
    component: MediaComponent,
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            DemoTopBar(
                title = component.title,
                onNavigateBack = onNavigateBack,
                onConfigureClick = onConfigureClick,
            )
        },
        modifier = Modifier
            .displayCutoutPadding()
    ) { innerPadding ->
        when (component) {
            MediaComponent.MediaControlSheet -> MediaControlSheetDemo(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
