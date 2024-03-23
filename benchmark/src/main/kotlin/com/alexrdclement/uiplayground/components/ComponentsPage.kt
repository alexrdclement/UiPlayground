package com.alexrdclement.uiplayground.components

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until

class ComponentsPage(
    private val device: UiDevice,
) {
    fun navigateToMediaControlSheet() {
        device.wait(Until.hasObject(By.text("MediaControlSheet")), 1000)
        device.findObject(By.text("MediaControlSheet")).click()
    }
}
