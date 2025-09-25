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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import com.alexrdclement.uiplayground.app.demo.Demo
import com.alexrdclement.uiplayground.app.demo.control.Control
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
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
    val currentGradientControl = Control.Dropdown(
        name = "Demo",
        includeLabel = false,
        values = {
            GradientDemo.entries.map {
                Control.Dropdown.DropdownItem(
                    name = it.name,
                    value = it,
                )
            }.toPersistentList()
        },
        selectedIndex = { GradientDemo.entries.indexOf(currentGradient) },
        onValueChange = { currentGradient = GradientDemo.entries[it] },
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
        .fillMaxSize()
        .padding(horizontal = PlaygroundTheme.spacing.large)
        .aspectRatio(1f)

    Demo(
        controls = persistentListOf(
            currentGradientControl,
        ),
        modifier = modifier
            .fillMaxSize(),
    ) {
        val density = LocalDensity.current
        LaunchedEffect(this@Demo.maxWidth) {
            widthPx = with(density) { this@Demo.maxWidth.toPx().toInt() }
        }
        when (currentGradient) {
            GradientDemo.RadialSweep -> Box(
                modifier = baseModifier
                    .background(
                        brush = Brush.sweepGradient(
                            colors = gradientColors,
                        ),
                        shape = CircleShape,
                    )
            )
            GradientDemo.Linear ->  Box(
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
            GradientDemo.Mesh -> MeshGradientDemo(
                gradientColors = gradientColors,
                offset = offset,
                widthPxFloat = widthPxFloat,
                modifier = baseModifier
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
