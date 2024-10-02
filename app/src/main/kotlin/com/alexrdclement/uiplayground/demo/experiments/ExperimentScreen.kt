package com.alexrdclement.uiplayground.demo.experiments

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.components.Surface
import com.alexrdclement.uiplayground.demo.experiments.demo.textfield.TextFieldDemo

@Composable
fun ExperimentScreen(
    experiment: Experiment,
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        when (experiment) {
            Experiment.TextField -> TextFieldDemo()
        }
    }
}
