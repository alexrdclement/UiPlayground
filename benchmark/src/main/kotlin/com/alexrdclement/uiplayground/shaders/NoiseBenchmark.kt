package com.alexrdclement.uiplayground.shaders

import android.graphics.Point
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.TraceSectionMetric
import androidx.benchmark.macro.TraceSectionMetric.Mode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import com.alexrdclement.uiplayground.MainCatalogPage
import com.alexrdclement.uiplayground.packageName
import com.alexrdclement.uiplayground.waitAndFindObject
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NoiseBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun amountAdjustment() = benchmarkRule.measureRepeated(
        packageName = packageName,
        metrics = listOf(
            FrameTimingMetric(),
            TraceSectionMetric("noise", Mode.Sum),
        ),
        iterations = 5,
        startupMode = StartupMode.WARM,
        setupBlock = {
            pressHome()
            startActivityAndWait()

            MainCatalogPage(device).navigateToShaders()
            ShadersPage(device).selectModifier("Noise")
        }
    ) {
        // One adjustment doesn't generate frame data
        val xAmount = device.waitAndFindObject(By.desc("Amount"))
        xAmount.drag(Point(xAmount.visibleCenter.x + 100, xAmount.visibleCenter.y))
        xAmount.drag(Point(xAmount.visibleCenter.x + 200, xAmount.visibleCenter.y))
    }
}
