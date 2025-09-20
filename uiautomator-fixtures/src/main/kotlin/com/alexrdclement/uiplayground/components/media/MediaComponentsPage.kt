package com.alexrdclement.uiplayground.components.media

import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.alexrdclement.uiplayground.waitAndFindObject

class MediaComponentsPage(
    private val device: UiDevice,
) {
    fun navigateToMediaControlSheet() {
        device.waitAndFindObject(By.text("MediaControlSheet")).click()
    }
}
