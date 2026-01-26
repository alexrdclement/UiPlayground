package com.alexrdclement.uiplayground.app

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.uikit.EndEdgePanGestureBehavior
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

@OptIn(ExperimentalComposeUiApi::class)
fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = {
        endEdgePanGestureBehavior = EndEdgePanGestureBehavior.Back
    }
) {
    App()
}
