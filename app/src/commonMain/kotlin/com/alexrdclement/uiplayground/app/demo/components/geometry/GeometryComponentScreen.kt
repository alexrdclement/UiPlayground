package com.alexrdclement.uiplayground.app.demo.components.geometry

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.components.geometry.navigation.GeometryComponent
import com.alexrdclement.uiplayground.components.layout.Scaffold

@Composable
fun GeometryComponentScreen(
    component: GeometryComponent,
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
            GeometryComponent.CurveStitch -> CurveStitchDemo(
                modifier = Modifier.padding(innerPadding)
            )
            GeometryComponent.Grid -> GridDemo(
                modifier = Modifier.padding(innerPadding)
            )
            GeometryComponent.Sphere -> SphereDemo(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
