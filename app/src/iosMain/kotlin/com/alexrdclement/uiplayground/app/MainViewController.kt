package com.alexrdclement.uiplayground.app

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

// Swift calls this by name, as MainViewControllerKt.MainViewController().
@Suppress("ktlint:standard:function-naming")
fun MainViewController(): UIViewController = ComposeUIViewController {
    App()
}
