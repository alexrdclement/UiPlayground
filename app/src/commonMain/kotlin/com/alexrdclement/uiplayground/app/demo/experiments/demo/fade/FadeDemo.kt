package com.alexrdclement.uiplayground.app.demo.experiments.demo.fade

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import com.alexrdclement.uiplayground.app.preview.UiPlaygroundPreview
import com.alexrdclement.uiplayground.theme.PlaygroundSpacing
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FadeDemo(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(PlaygroundSpacing.medium),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BoxWithConstraints(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            val width = with(LocalDensity.current) { constraints.maxWidth.toDp() }
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
                        .fade(side = fadeSide, height = width / 4)
                        .background(PlaygroundTheme.colorScheme.primary)
                        .align(alignment)
                )
            }
        }

        BoxWithConstraints(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            val width = with(LocalDensity.current) { constraints.maxWidth.toDp() }
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
                        .fade(sides = fadeSides, height = width / 4)
                        .background(PlaygroundTheme.colorScheme.primary)
                        .align(alignment)
                )
            }
        }
    }
}

@Preview
@Composable
fun FadeDemoPreview() {
    UiPlaygroundPreview {
        FadeDemo()
    }
}
