package com.alexrdclement.uiplayground.components.core

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import com.alexrdclement.uiplayground.waitAndFindObject

class TextFieldPage(
    private val device: UiDevice,
) {
    val textField: UiObject2
        get() = device.waitAndFindObject(By.text("Hello world").enabled(true))
}
