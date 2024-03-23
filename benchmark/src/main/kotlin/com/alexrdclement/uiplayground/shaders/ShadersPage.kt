package com.alexrdclement.uiplayground.shaders

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until

class ShadersPage(
    private val device: UiDevice,
) {

    fun selectModifier(modifierName: String) {
        device.wait(Until.hasObject(By.text("None")), 1000)
        device.findObject(By.text("None")).click()
        device.wait(Until.hasObject(By.text(modifierName)), 1000)
        device.findObject(By.text(modifierName)).click()
    }
}
