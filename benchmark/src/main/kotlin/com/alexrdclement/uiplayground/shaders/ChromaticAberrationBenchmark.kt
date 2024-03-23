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
class ChromaticAberrationBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun xyAdjustment() = benchmarkRule.measureRepeated(
        packageName = packageName,
        metrics = listOf(
            FrameTimingMetric(),
            TraceSectionMetric("chromaticAberration", Mode.Sum),
        ),
        iterations = 5,
        startupMode = StartupMode.WARM,
        setupBlock = {
            pressHome()
            startActivityAndWait()

            MainCatalogPage(device).navigateToShaders()
            ShadersPage(device).selectModifier("Chromatic Aberration")
        }
    ) {
        val xAmount = device.waitAndFindObject(By.desc("X Amount"))
        xAmount.drag(Point(xAmount.visibleCenter.x + 100, xAmount.visibleCenter.y))
        val yAmount = device.waitAndFindObject(By.desc("Y Amount"))
        yAmount.drag(Point(yAmount.visibleCenter.x + 100, yAmount.visibleCenter.y))
    }
}
