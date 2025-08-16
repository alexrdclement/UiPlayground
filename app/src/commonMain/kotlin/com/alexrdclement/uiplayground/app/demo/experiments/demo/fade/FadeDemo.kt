package com.alexrdclement.uiplayground.app.demo.experiments.demo.fade

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.demo.control.Controls
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import com.alexrdclement.uiplayground.components.HorizontalDivider
import com.alexrdclement.uiplayground.theme.PlaygroundSpacing
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FadeDemo(
    modifier: Modifier = Modifier,
) {
    var widthPx by remember { mutableIntStateOf(0) }
    val width = with(LocalDensity.current) { widthPx.toDp() }

    var fadeLength by remember { mutableStateOf(20.dp) }
    val fadeLengthControl = Control.Slider(
        name = "Fade length",
        value = fadeLength.value,
        onValueChange = { fadeLength = it.dp },
        valueRange = 0f..width.value,
    )

    LaunchedEffect(width) {
        if (width.value > 0) {
            fadeLength = width / 4
        }
    }

    var showBorder by remember { mutableStateOf(false) }
    val borderColor = if (showBorder) Color.Red else null
    val showBorderControl = Control.Toggle(
        name = "Show border",
        value = showBorder,
        onValueChange = { showBorder = it },
    )

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = modifier
                .weight(1f)
                .onSizeChanged { widthPx = it.width }
                .bottomFade(length = fadeLength, borderColor = borderColor)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(PlaygroundSpacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                listOf(
                    FadeSide.Left to Alignment.TopStart,
                    FadeSide.Top to Alignment.TopEnd,
                    FadeSide.Right to Alignment.BottomEnd,
                    FadeSide.Bottom to Alignment.BottomStart,
                ).map { (fadeSide, alignment) ->
                    Box(
                        modifier = Modifier
                            .padding(width / 8)
                            .size(width / 4)
                            .fade(side = fadeSide, length = fadeLength, borderColor = borderColor)
                            .background(PlaygroundTheme.colorScheme.primary)
                            .align(alignment)
                    )
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            ) {
                listOf(
                    FadeSide.Left + FadeSide.Top to Alignment.TopStart,
                    FadeSide.Top + FadeSide.Right to Alignment.TopEnd,
                    FadeSide.Right + FadeSide.Bottom to Alignment.BottomEnd,
                    FadeSide.Left + FadeSide.Bottom to Alignment.BottomStart,
                ).map { (fadeSides, alignment) ->
                    Box(
                        modifier = Modifier
                            .padding(width / 8)
                            .size(width / 4)
                            .fade(sides = fadeSides, length = fadeLength, borderColor = borderColor)
                            .background(PlaygroundTheme.colorScheme.primary)
                            .align(alignment)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(PlaygroundTheme.colorScheme.primary)
            )
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
        Controls(
            controls = persistentListOf(
                fadeLengthControl,
                showBorderControl,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(PlaygroundSpacing.medium)
        )
    }
}

@Preview
@Composable
fun FadeDemoPreview() {
    UiPlaygroundPreview {
        FadeDemo()
    }
}
