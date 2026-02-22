package com.alexrdclement.uiplayground

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice

class ExperimentPage(
    private val device: UiDevice,
    private val title: String,
) {
    fun assertIsDisplayed() {
        device.waitAndFindObject(By.text(title))
    }
}
