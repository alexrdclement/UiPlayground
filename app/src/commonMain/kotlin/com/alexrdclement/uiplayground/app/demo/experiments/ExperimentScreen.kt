package com.alexrdclement.uiplayground.app.demo.experiments

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.configuration.ConfigureButton
import com.alexrdclement.uiplayground.app.demo.experiments.demo.fade.FadeDemo
import com.alexrdclement.uiplayground.app.demo.experiments.demo.gradient.GradientDemo
import com.alexrdclement.uiplayground.components.BackNavigationButton
import com.alexrdclement.uiplayground.components.Scaffold
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.components.TopBar
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun ExperimentScreen(
    experiment: Experiment,
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(
                title = { Text(experiment.title, style = PlaygroundTheme.typography.titleMedium) },
                navButton = { BackNavigationButton(onNavigateBack) },
                actions = {
                    ConfigureButton(onClick = onConfigureClick)
                }
            )
        },
    ) { innerPadding ->
        when (experiment) {
            Experiment.Fade -> FadeDemo(
                modifier = Modifier.padding(innerPadding),
            )
            Experiment.Gradients -> GradientDemo(
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}
