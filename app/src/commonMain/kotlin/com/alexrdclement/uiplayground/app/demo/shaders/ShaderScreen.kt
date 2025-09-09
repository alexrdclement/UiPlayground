package com.alexrdclement.uiplayground.app.demo.shaders

import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import com.alexrdclement.uiplayground.components.Scaffold
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShaderScreen(
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            DemoTopBar(
                title = "Shaders",
                onNavigateBack = onNavigateBack,
                onConfigureClick = onConfigureClick,
            )
        },
        modifier = Modifier
            .displayCutoutPadding(),
    ) { innerPadding ->
        ShaderDemo(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        )
    }
}

@Preview
@Composable
private fun Preview() {
    UiPlaygroundPreview {
        ShaderScreen(
            onNavigateBack = {},
            onConfigureClick = {},
        )
    }
}
