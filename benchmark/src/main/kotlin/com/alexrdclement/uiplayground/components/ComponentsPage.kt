package com.alexrdclement.uiplayground.components

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.alexrdclement.uiplayground.waitAndFindObject

class ComponentsPage(
    private val device: UiDevice,
) {
    fun navigateToMediaControlSheet() {
        device.waitAndFindObject(By.text("MediaControlSheet")).click()
    }
}
