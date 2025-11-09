package com.alexrdclement.uiplayground.app.demo.components.auth

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.components.auth.navigation.AuthComponent
import com.alexrdclement.uiplayground.components.layout.Scaffold

@Composable
fun AuthComponentScreen(
    component: AuthComponent,
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
            AuthComponent.AuthButton -> AuthButtonDemo(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
