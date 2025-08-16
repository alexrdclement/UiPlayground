package com.alexrdclement.uiplayground.app.demo.experiments

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.app.demo.DemoTopBar
import com.alexrdclement.uiplayground.app.demo.experiments.demo.fade.FadeDemo
import com.alexrdclement.uiplayground.app.demo.experiments.demo.gradient.GradientDemo
import com.alexrdclement.uiplayground.app.demo.experiments.demo.scroll.AnimateScrollItemVisibleDemo
import com.alexrdclement.uiplayground.components.Scaffold

@Composable
fun ExperimentScreen(
    experiment: Experiment,
    onNavigateBack: () -> Unit,
    onConfigureClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            DemoTopBar(
                title = experiment.title,
                onNavigateBack = onNavigateBack,
                onConfigureClick = onConfigureClick,
            )
        },
    ) { innerPadding ->
        when (experiment) {
            Experiment.AnimateScrollItemVisible -> AnimateScrollItemVisibleDemo(
                modifier = Modifier.padding(innerPadding),
            )
            Experiment.Fade -> FadeDemo(
                modifier = Modifier.padding(innerPadding),
            )
            Experiment.Gradients -> GradientDemo(
                modifier = Modifier.padding(innerPadding),
            )
        }
    }
}
