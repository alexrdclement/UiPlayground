package com.alexrdclement.uiplayground.app.demo.subject

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.alexrdclement.uiplayground.components.TextField
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun DemoTextField(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = PlaygroundTheme.typography.display,
) {
    Box(modifier = modifier.fillMaxSize()) {
        TextField(
            state = rememberTextFieldState(),
            textStyle = textStyle,
            modifier = modifier.align(Alignment.Center)
        )
    }
}
