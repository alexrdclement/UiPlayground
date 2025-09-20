package com.alexrdclement.uiplayground.app.demo.components.core

import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.components.core.navigation.CoreComponent
import com.alexrdclement.uiplayground.components.layout.Scaffold

@Composable
fun CoreComponentScreen(
    component: CoreComponent,
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
            CoreComponent.Button -> ButtonDemo(
                modifier = Modifier.padding(innerPadding)
            )
            CoreComponent.Text -> TextDemo(
                modifier = Modifier.padding(innerPadding)
            )
            CoreComponent.TextField -> TextFieldDemo(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
