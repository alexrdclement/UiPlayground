package com.alexrdclement.uiplayground.components.core

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.alexrdclement.uiplayground.waitAndFindObject

class CoreComponentsPage(
    private val device: UiDevice,
) {
    fun navigateToButton() {
        device.waitAndFindObject(By.text("Button")).click()
    }

    fun navigateToText() {
        device.waitAndFindObject(By.text("Text")).click()
    }

    fun navigateToTextField() {
        device.waitAndFindObject(By.text("TextField")).click()
    }
}
