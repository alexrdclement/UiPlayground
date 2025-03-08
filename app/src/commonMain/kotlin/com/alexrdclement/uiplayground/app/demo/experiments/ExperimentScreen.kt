package com.alexrdclement.uiplayground.app.demo.experiments

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.app.demo.experiments.demo.button.ButtonDemo
import com.alexrdclement.uiplayground.app.demo.experiments.demo.text.TextDemo
import com.alexrdclement.uiplayground.app.demo.experiments.demo.textfield.TextFieldDemo
import com.alexrdclement.uiplayground.components.BackNavigationButton
import com.alexrdclement.uiplayground.components.Scaffold
import com.alexrdclement.uiplayground.components.Text
import com.alexrdclement.uiplayground.components.TopBar
import com.alexrdclement.uiplayground.theme.PlaygroundTheme

@Composable
fun ExperimentScreen(
    experiment: Experiment,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopBar(
                title = { Text(experiment.title, style = PlaygroundTheme.typography.titleMedium) },
                navButton = { BackNavigationButton(onNavigateBack) },
            )
        },
    ) {
        when (experiment) {
            Experiment.Button -> ButtonDemo()
            Experiment.Text -> TextDemo()
            Experiment.TextField -> TextFieldDemo()
        }
    }
}
