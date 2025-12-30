package com.alexrdclement.uiplayground.shaders

import android.graphics.Point
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import com.alexrdclement.uiplayground.waitAndFindObject

class ShadersPage(
    private val device: UiDevice,
) {

    fun selectCircleSubject() {
        selectSubject("Circle")
    }

    fun selectGridLineSubject() {
        selectSubject("GridLine")
    }

    fun selectColorInvert() {
        selectModifier("Color Invert")
    }

    fun adjustColorInvert() {
        // One adjustment doesn't generate benchmark/profile frame data
        val amount = device.waitAndFindObject(By.desc("Amount"))
        amount.drag(Point(amount.visibleCenter.x + 100, amount.visibleCenter.y))
        amount.drag(Point(amount.visibleCenter.x + 200, amount.visibleCenter.y))
    }

    fun selectColorSplit() {
        selectModifier("Color Split")
    }

    fun adjustColorSplit() {
        val xAmount = device.waitAndFindObject(By.desc("X Amount"))
        xAmount.drag(Point(xAmount.visibleCenter.x + 100, xAmount.visibleCenter.y))
        val yAmount = device.waitAndFindObject(By.desc("Y Amount"))
        yAmount.drag(Point(yAmount.visibleCenter.x + 100, yAmount.visibleCenter.y))
    }

    fun selectNoise() {
        selectModifier("Noise")
    }

    fun adjustNoise() {
        // One adjustment doesn't generate benchmark/profile frame data
        val xAmount = device.waitAndFindObject(By.desc("Amount"))
        xAmount.drag(Point(xAmount.visibleCenter.x + 100, xAmount.visibleCenter.y))
        xAmount.drag(Point(xAmount.visibleCenter.x + 200, xAmount.visibleCenter.y))
    }

    fun selectPixelate() {
        selectModifier("Pixelate")
    }

    fun adjustPixelate() {
        // One adjustment doesn't generate benchmark/profile frame data
        val xAmount = device.waitAndFindObject(By.desc("Subdivisions"))
        xAmount.drag(Point(xAmount.visibleCenter.x + 100, xAmount.visibleCenter.y))
        xAmount.drag(Point(xAmount.visibleCenter.x + 200, xAmount.visibleCenter.y))
    }

    fun selectWarp() {
        selectModifier("Warp")
    }

    fun adjustWarp() {
        device.click(device.displayWidth / 2, device.displayHeight / 2)
        // One adjustment doesn't generate benchmark/profile frame data
        val xAmount = device.waitAndFindObject(By.desc("Amount"))
        xAmount.drag(Point(xAmount.visibleCenter.x + 100, xAmount.visibleCenter.y))
    }

    private fun selectSubject(subjectName: String) {
        device.waitAndFindObject(By.desc("Select subject")).click()
        device.waitAndFindObject(By.text(subjectName)).click()
    }

    private fun selectModifier(modifierName: String) {
        device.waitAndFindObject(By.desc("Select modifier")).click()
        device.waitAndFindObject(By.text(modifierName)).click()
    }
}
