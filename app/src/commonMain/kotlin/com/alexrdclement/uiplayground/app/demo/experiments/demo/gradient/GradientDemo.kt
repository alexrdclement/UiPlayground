package com.alexrdclement.uiplayground.app.demo.experiments.demo.gradient

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalDensity
import com.alexrdclement.palette.components.demo.Demo
import com.alexrdclement.palette.components.demo.control.enumControl
import kotlinx.collections.immutable.persistentListOf
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

    var currentGradient by remember { mutableStateOf(GradientDemo.RadialSweep) }
    val currentGradientControl = enumControl(
        name = "Demo",
        includeLabel = false,
        values = { GradientDemo.entries },
        selectedValue = { currentGradient },
        onValueChange = { currentGradient = it },
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

    Demo(
        controls = persistentListOf(
            currentGradientControl,
        ),
        modifier = modifier
            .fillMaxSize()
    ) {
        val density = LocalDensity.current
        LaunchedEffect(this@Demo.maxWidth) {
            widthPx = with(density) { this@Demo.maxWidth.toPx().toInt() }
        }

        val baseModifier = Modifier
            .align(Alignment.Center)
        when (currentGradient) {
            GradientDemo.RadialSweep -> Box(
                modifier = baseModifier
                    .aspectRatio(1f)
                    .background(
                        brush = Brush.sweepGradient(
                            colors = gradientColors,
                        ),
                        shape = CircleShape,
                    )
            )
            GradientDemo.Linear -> Box(
                modifier = baseModifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = gradientColors,
                            start = Offset(offset, offset),
                            end = Offset(offset + widthPxFloat, offset + widthPxFloat),
                            tileMode = TileMode.Repeated,
                        ),
                    )
            )
            GradientDemo.Mesh -> MeshGradientDemo(
                gradientColors = gradientColors,
                offset = offset,
                widthPxFloat = widthPxFloat,
                modifier = baseModifier
                    .fillMaxSize()
            )
        }
    }
}

enum class GradientDemo {
    RadialSweep,
    Linear,
    Mesh,
}

@Composable
private fun MeshGradientDemo(
    gradientColors: List<Color>,
    offset: Float,
    widthPxFloat: Float,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
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
