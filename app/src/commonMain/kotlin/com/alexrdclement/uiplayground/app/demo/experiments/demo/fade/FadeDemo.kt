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
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import com.alexrdclement.uiplayground.theme.PlaygroundSpacing
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FadeDemo(
    modifier: Modifier = Modifier,
    state: FadeDemoState = rememberFadeDemoState(),
    control: FadeDemoControl = rememberFadeDemoControl(state),
) {
    Demo(
        controls = control.controls,
        modifier = modifier
            .fillMaxSize()
    ) {
        LaunchedEffect(this@Demo.maxWidth) {
            control.onSizeChanged(this@Demo.maxWidth)
        }
        Column(
            modifier = Modifier
                .bottomFade(
                    length = state.fadeLength,
                    borderColor = state.borderColor.takeIf { state.showBorder },
                )
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
                            .padding(state.width / 8)
                            .size(state.width / 4)
                            .fade(
                                side = fadeSide,
                                length = state.fadeLength,
                                borderColor = state.borderColor.takeIf { state.showBorder },
                            )
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
                            .padding(state.width / 8)
                            .size(state.width / 4)
                            .fade(
                                sides = fadeSides,
                                length = state.fadeLength,
                                borderColor = state.borderColor.takeIf { state.showBorder },
                            )
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
    }
}

@Composable
fun rememberFadeDemoState(
    borderColor: Color = Color.Red,
) = remember {
    FadeDemoState(
        borderColor = borderColor,
    )
}

@Stable
class FadeDemoState(
    val borderColor: Color,
) {
    var fadeLength by mutableStateOf(20.dp)
    var showBorder by mutableStateOf(false)

    var width by mutableStateOf(0.dp)
}

@Composable
fun rememberFadeDemoControl(state: FadeDemoState) = remember(state) {
    FadeDemoControl(state)
}

@Stable
class FadeDemoControl(
    val state: FadeDemoState,
) {
    val fadeLength
        get() = Control.Slider(
            name = "Fade length",
            value = state.fadeLength.value,
            onValueChange = { state.fadeLength = it.dp },
            valueRange = 0f..500f,
        )

    val showBorder
        get() = Control.Toggle(
            name = "Show border",
            value = state.showBorder,
            onValueChange = { state.showBorder = it },
        )

    val controls
        get() = persistentListOf(
            fadeLength,
            showBorder,
        )

    fun onSizeChanged(width: Dp) {
        if (width > 0.dp) {
            fadeLength.onValueChange(width.value / 4f)
        }
        state.width = width
    }
}

@Preview
@Composable
fun FadeDemoPreview() {
    UiPlaygroundPreview {
        FadeDemo()
    }
}
