package com.alexrdclement.uiplayground.components

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.alexrdclement.uiplayground.waitAndFindObject

class ComponentsPage(
    private val device: UiDevice,
) {
    fun navigateToMediaComponents() {
        device.waitAndFindObject(By.text("Media")).click()
    }
}
