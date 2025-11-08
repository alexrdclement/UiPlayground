package com.alexrdclement.uiplayground.app.demo.components.datetime

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.components.datetime.navigation.DateTimeComponent
import com.alexrdclement.uiplayground.components.layout.Scaffold

@Composable
fun DateTimeComponentScreen(
    component: DateTimeComponent,
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
            DateTimeComponent.DateTimeFormat -> DateTimeDemo(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
