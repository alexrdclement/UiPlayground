package com.alexrdclement.uiplayground.app.demo.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.components.demo.ButtonDemo
import com.alexrdclement.uiplayground.app.demo.components.demo.CurrencyAmountFieldDemo
import com.alexrdclement.uiplayground.app.demo.components.demo.GridDemo
import com.alexrdclement.uiplayground.app.demo.components.demo.MediaControlSheetDemo
import com.alexrdclement.uiplayground.app.demo.components.demo.SphereDemo
import com.alexrdclement.uiplayground.app.demo.components.demo.TextDemo
import com.alexrdclement.uiplayground.app.demo.components.demo.TextFieldDemo
import com.alexrdclement.uiplayground.app.demo.components.demo.CurveStitchDemo
import com.alexrdclement.uiplayground.components.Scaffold

@Composable
fun ComponentScreen(
    component: Component,
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
            Component.Button -> ButtonDemo()
            Component.CurrencyAmountField -> CurrencyAmountFieldDemo()
            Component.CurveStitch -> CurveStitchDemo(
                modifier = Modifier.padding(innerPadding)
            )
            Component.Grid -> GridDemo(
                modifier = Modifier.padding(innerPadding),
            )
            Component.MediaControlSheet -> MediaControlSheetDemo(
                modifier = Modifier.padding(innerPadding)
            )
            Component.Sphere -> SphereDemo(
                modifier = Modifier.padding(innerPadding)
            )
            Component.Text -> TextDemo()
            Component.TextField -> TextFieldDemo()
        }
    }
}
