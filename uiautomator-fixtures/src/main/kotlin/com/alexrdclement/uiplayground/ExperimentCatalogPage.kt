package com.alexrdclement.uiplayground

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice

class ExperimentCatalogPage(
    private val device: UiDevice,
) {
    fun assertIsDisplayed() {
        device.waitAndFindObject(By.text("Blur"))
    }
}
