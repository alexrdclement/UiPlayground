package com.alexrdclement.uiplayground.app.demo.experiments.demo.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.AutoSize
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.components.TextField
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun TextDemo() {
    val textFieldState = rememberTextFieldState()
    val text by snapshotFlow { textFieldState.text.toString() }
        .collectAsState(initial = textFieldState.text.toString())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = text,
            style = PlaygroundTheme.typography.headline,
            autoSize = AutoSize.StepBased(),
            maxLines = 1,
            softWrap = false,
        )
        TextField(
            state = textFieldState,
            modifier = Modifier.padding(PlaygroundTheme.spacing.small)
        )
    }
}