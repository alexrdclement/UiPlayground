package com.alexrdclement.uiplayground.demo.subject

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun DemoTextField(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = PlaygroundTheme.typography.display,
) {
    var text by remember { mutableStateOf("") }
    Box(modifier = modifier.fillMaxSize()) {
        TextField(
            value = text,
            onValueChange = { text = it },
            textStyle = textStyle,
            modifier = modifier.align(Alignment.Center)
        )
    }
}
