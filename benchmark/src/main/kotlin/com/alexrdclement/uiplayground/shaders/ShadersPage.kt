package com.alexrdclement.uiplayground.shaders

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.alexrdclement.uiplayground.waitAndFindObject

class ShadersPage(
    private val device: UiDevice,
) {
    fun selectModifier(modifierName: String) {
        device.waitAndFindObject(By.text("None")).click()
        device.waitAndFindObject(By.text(modifierName)).click()
    }
}
