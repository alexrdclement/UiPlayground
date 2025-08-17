package com.alexrdclement.uiplayground.app.demo.components.demo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.control.Controls
import com.alexrdclement.uiplayground.components.Button
import com.alexrdclement.uiplayground.components.HorizontalDivider
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf

@Composable
fun ButtonDemo() {
    var maxWidthPx by remember { mutableIntStateOf(0) }
    val maxWidth = with(LocalDensity.current) { maxWidthPx.toDp() }
    var width by remember(maxWidth) { mutableStateOf(maxWidth) }
    val widthControl = Control.Slider(
        name = "Width",
        value = width.value,
        onValueChange = {
            width = it.dp
        },
        valueRange = 0f..maxWidth.value,
    )

    var autoSizeText by remember { mutableStateOf(true) }
    val autoSizeControl = Control.Toggle(
        name = "Auto-size text",
        value = autoSizeText,
        onValueChange = {
            autoSizeText = it
        }
    )

    var softWrap by remember { mutableStateOf(false) }
    val softWrapControl = Control.Toggle(
        name = "Soft-wrap text",
        value = softWrap,
        onValueChange = {
            softWrap = it
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .onSizeChanged { maxWidthPx = it.width },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center,
        ) {
            Button(
                onClick = {},
                modifier = Modifier.width(width)
            ) {
                Text(
                    text = "Button",
                    style = PlaygroundTheme.typography.labelLarge,
                    softWrap = softWrap,
                    autoSize = if (autoSizeText) TextAutoSize.StepBased() else null,
                )
            }
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        Controls(
            controls = persistentListOf(
                widthControl,
                autoSizeControl,
                softWrapControl,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(PlaygroundTheme.spacing.medium),
        )
    }
}
