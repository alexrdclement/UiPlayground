package com.alexrdclement.uiplayground.components.mediacontrolsheet

import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import com.alexrdclement.uiplayground.MainCatalogPage
import com.alexrdclement.uiplayground.components.ComponentsPage
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
class MediaControlSheetBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun mediaControlSheetOpen() = benchmarkRule.measureRepeated(
        packageName = packageName,
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.WARM,
        setupBlock = {
            pressHome()
            startActivityAndWait()

            MainCatalogPage(device).navigateToComponents()
            ComponentsPage(device).navigateToMediaControlSheet()
        }
    ) {
        device.findObject(By.descContains("Media control bar")).click()
    }
}
