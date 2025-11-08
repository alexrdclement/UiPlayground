package com.alexrdclement.uiplayground.app.demo.components.money

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.components.money.navigation.MoneyComponent
import com.alexrdclement.uiplayground.components.layout.Scaffold

@Composable
fun MoneyComponentScreen(
    component: MoneyComponent,
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
            MoneyComponent.CurrencyAmountField -> CurrencyAmountFieldDemo(
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
