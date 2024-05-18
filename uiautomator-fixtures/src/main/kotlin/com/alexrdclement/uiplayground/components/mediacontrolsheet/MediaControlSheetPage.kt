package com.alexrdclement.uiplayground.components.mediacontrolsheet

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import com.alexrdclement.uiplayground.waitAndFindObject

class MediaControlSheetPage(
    private val device: UiDevice,
) {
    val mediaControlBar: UiObject2
        get() = device.waitAndFindObject(By.descContains("Media control bar"))
}
