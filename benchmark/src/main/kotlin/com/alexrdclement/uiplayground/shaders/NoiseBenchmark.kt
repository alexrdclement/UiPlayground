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
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 */
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
        val xAmount = device.findObject(By.desc("Amount"))
        xAmount.drag(Point(xAmount.visibleCenter.x + 100, xAmount.visibleCenter.y))
        xAmount.drag(Point(xAmount.visibleCenter.x + 200, xAmount.visibleCenter.y))
    }
}
