package com.alexrdclement.uiplayground

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice

class MainCatalogPage(
    private val device: UiDevice,
) {
    fun navigateToComponents() {
        device.waitAndFindObject(By.text("Components")).click()
    }

    fun navigateToShaders() {
        device.waitAndFindObject(By.text("Shaders")).click()
    }
}
