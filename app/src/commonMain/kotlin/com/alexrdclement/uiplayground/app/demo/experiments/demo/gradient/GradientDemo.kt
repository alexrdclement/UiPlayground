package com.alexrdclement.uiplayground.app.demo.experiments.demo.gradient

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun GradientDemo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(PlaygroundTheme.spacing.large),
    ) {
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Color.Red,
                            Color.Magenta,
                            Color.Blue,
                            Color.Cyan,
                            Color.Green,
                            Color.Yellow,
                            Color.Red,
                        ),
                    ),
                    shape = CircleShape,
                )
        )
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Color.Red,
                            Color.Magenta,
                            Color.Blue,
                            Color.Cyan,
                            Color.Green,
                            Color.Yellow,
                            Color.Red,
                        ),
                    ),
                    shape = CircleShape,
                )
        )
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Color.Red,
                            Color.Magenta,
                            Color.Blue,
                            Color.Cyan,
                            Color.Green,
                            Color.Yellow,
                            Color.Red,
                        ),
                    ),
                    shape = CircleShape,
                )
        )
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(
                    brush = Brush.sweepGradient(
                        colors = listOf(
                            Color.Red,
                            Color.Magenta,
                            Color.Blue,
                            Color.Cyan,
                            Color.Green,
                            Color.Yellow,
                            Color.Red,
                        ),
                    ),
                    shape = CircleShape,
                )
        )
    }
}
