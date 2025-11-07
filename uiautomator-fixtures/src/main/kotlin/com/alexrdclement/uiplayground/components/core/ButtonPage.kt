package com.alexrdclement.uiplayground.components.core

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import com.alexrdclement.uiplayground.waitAndFindObject

class ButtonPage(
    private val device: UiDevice,
) {
    val button: UiObject2
        get() = device.waitAndFindObject(By.text("Button"))
}
