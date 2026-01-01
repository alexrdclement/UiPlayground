package com.alexrdclement.uiplayground.app.configuration

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alexrdclement.palette.components.core.Surface
import com.alexrdclement.palette.components.core.Text
import com.alexrdclement.palette.components.demo.control.enumControl
import com.alexrdclement.palette.theme.PaletteTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ConfigurationDialogContent(
    configurationController: ConfigurationController,
) {
    var colorMode by remember(configurationController) {
        mutableStateOf(configurationController.colorMode)
    }
    val colorModeControl = enumControl(
        name = "Color mode",
        values = { ColorMode.entries },
        selectedValue = { colorMode },
        onValueChange = {
            if (configurationController.setColorMode(it)) {
                colorMode = it
            }
        },
    )

    Surface(
        border = BorderStroke(1.dp, PaletteTheme.colorScheme.outline),
    ) {
        Column(
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .padding(PaletteTheme.spacing.small)
        ) {
            Text(
                text = "Configure",
                style = PaletteTheme.typography.titleMedium.merge(
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(PaletteTheme.spacing.small)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview
@Composable
private fun ConfigurationDialogContentPreview() {
    PaletteTheme {
        ConfigurationDialogContent(
            configurationController = rememberConfigurationController(),
        )
    }
}
