package com.alexrdclement.uiplayground.demo.experiments

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.demo.experiments.demo.textfield.TextFieldDemo

@Composable
fun ExperimentScreen(
    experiment: Experiment,
) {
    when (experiment) {
        Experiment.TextField -> TextFieldDemo()
    }
}
