package com.alexrdclement.uiplayground.app.demo.experiments.demo.gradient

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.onSizeChanged
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlin.math.min
import kotlin.math.sin

@Composable
fun GradientDemo(modifier: Modifier = Modifier) {
    val gradientColors = listOf(
        Color.Red,
        Color.Magenta,
        Color.Blue,
        Color.Cyan,
        Color.Green,
        Color.Yellow,
        Color.Red,
    )

    var widthPx by remember { mutableStateOf(0) }
    val widthPxFloat = widthPx.toFloat()

    val infiniteTransition = rememberInfiniteTransition()
    val offset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = widthPxFloat,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 10000,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart,
        ),
    )

    val baseModifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = PlaygroundTheme.spacing.large)
        .aspectRatio(1f)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding()
            .onSizeChanged {
                widthPx = it.width
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(PlaygroundTheme.spacing.large),
    ) {
        Box(
            modifier = baseModifier
                .background(
                    brush = Brush.sweepGradient(
                        colors = gradientColors,
                    ),
                    shape = CircleShape,
                )
        )
        Box(
            modifier = baseModifier
                .background(
                    brush = Brush.linearGradient(
                        colors = gradientColors,
                        start = Offset(offset, offset),
                        end = Offset(offset + widthPxFloat, offset + widthPxFloat),
                        tileMode = TileMode.Repeated,
                    ),
                )
        )
        Box(
            modifier = baseModifier
                .meshGradient(
                    points = gradientColors.mapIndexed { index, color ->
                        listOf(
                            Offset(0.0f, index / (gradientColors.size.toFloat() - 1)) to color,
                            Offset(
                                offset / widthPxFloat,
                                if (index == 0) 0.0f else min(1f, index / (gradientColors.size.toFloat() - 1) + 0.2f),
                            ) to color,
                            Offset(
                                min(.999f, offset / widthPxFloat + 0.2f),
                                if (index == 0) 0f else min(1f, index / (gradientColors.size.toFloat() - 1) + 0.2f),
                            ) to color,
                            Offset(1.0f, index / (gradientColors.size.toFloat() - 1)) to color,
                        )
                    },
                    resolutionX = 10,
                    resolutionY = 1,
                    showPoints = false,
                    indicesModifier = { it },
                )
        )
        Box(
            modifier = baseModifier
                .meshGradient(
                    points = gradientColors.mapIndexed { index, color ->
                        val progress = (sin(2 * kotlin.math.PI * offset / widthPxFloat) / 8)
                        listOf(
                            Offset(
                                0.0f,
                                if (index == 0) 0f else index / (gradientColors.lastIndex.toFloat())
                            ) to color,
                            Offset(
                                .33f,
                                when (index) {
                                    0 -> 0f
                                    gradientColors.lastIndex -> 1f
                                    else -> (index / (gradientColors.lastIndex.toFloat()) - progress.toFloat()).coerceIn(0f, 1f)
                                }
                            ) to color,
                            Offset(
                                .66f,
                                when (index) {
                                    0 -> 0f
                                    gradientColors.lastIndex -> 1f
                                    else -> (index / (gradientColors.lastIndex.toFloat()) + progress.toFloat()).coerceIn(0f, 1f)
                                }
                            ) to color,
                            Offset(
                                1.0f,
                                if (index == gradientColors.lastIndex) 1f
                                else index / (gradientColors.size.toFloat() - 1)) to color,
                        )
                    },
                    resolutionX = 10,
                    resolutionY = 1,
                    showPoints = false,
                    indicesModifier = { it },
                )
        )
    }
}
