package com.alexrdclement.uiplayground.app.demo.components.color

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.components.color.navigation.ColorComponent
import com.alexrdclement.uiplayground.components.layout.Scaffold

@Composable
fun ColorComponentScreen(
    component: ColorComponent,
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
    ) { innerPadding ->
        when (component) {
            ColorComponent.ColorPicker -> ColorPickerDemo(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
