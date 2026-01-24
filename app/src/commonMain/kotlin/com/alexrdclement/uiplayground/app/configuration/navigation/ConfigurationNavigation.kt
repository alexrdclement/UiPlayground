package com.alexrdclement.uiplayground.app.configuration.navigation

import androidx.compose.runtime.Composable
import com.alexrdclement.uiplayground.app.configuration.ConfigurationController
import com.alexrdclement.uiplayground.app.configuration.ConfigurationDialogContent
import com.alexrdclement.uiplayground.app.navigation.UiPlaygroundNavKey
import com.alexrdclement.uiplayground.app.navigation.toPathSegment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("configuration")
data object ConfigurationRoute : UiPlaygroundNavKey {
    override val pathSegment = "configuration".toPathSegment()
    override val isDialog = true
}

@Composable
fun ConfigurationContent(
    configurationController: ConfigurationController,
) {
    ConfigurationDialogContent(
        configurationController = configurationController,
    )
}
