package com.alexrdclement.uiplayground.components

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice

class ComponentsPage(
    private val device: UiDevice,
) {
    fun navigateToMediaControlSheet() {
        device.findObject(By.text("MediaControlSheet")).click()
    }
}
