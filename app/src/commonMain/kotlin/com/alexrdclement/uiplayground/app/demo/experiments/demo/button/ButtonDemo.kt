package com.alexrdclement.uiplayground.app.demo.experiments.demo.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.AutoSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.Button
import com.alexrdclement.uiplayground.components.Slider
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun ButtonDemo() {
    var width by remember { mutableStateOf(100.dp) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {},
            modifier = Modifier.width(width)
        ) {
            Text(
                text = "Button",
                maxLines = 1,
                softWrap = false,
                autoSize = AutoSize.StepBased(),
            )
        }
        Slider(
            value = width.value,
            onValueChange = { width = it.dp },
            valueRange = 0f..1000f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(PlaygroundTheme.spacing.small),
        )
    }
}