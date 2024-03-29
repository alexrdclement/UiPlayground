package com.alexrdclement.uiplayground.components.mediacontrolsheet

import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.TraceSectionMetric
import androidx.benchmark.macro.TraceSectionMetric.Mode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import com.alexrdclement.uiplayground.MainCatalogPage
import com.alexrdclement.uiplayground.components.ComponentsPage
import com.alexrdclement.uiplayground.packageName
import com.alexrdclement.uiplayground.waitAndFindObject
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.time.Duration.Companion.milliseconds

@RunWith(AndroidJUnit4::class)
class MediaControlSheetBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun mediaControlSheetOpen() = benchmarkRule.measureRepeated(
        packageName = packageName,
        metrics = listOf(
            FrameTimingMetric(),
            TraceSectionMetric("MediaControlSheet", Mode.Sum),
            TraceSectionMetric("MediaControlBar", Mode.Sum),
            TraceSectionMetric("MediaControlBar:MediaItemArtwork:measure", Mode.Sum),
        ),
        iterations = 5,
        startupMode = StartupMode.WARM,
        setupBlock = {
            pressHome()
            startActivityAndWait()

            MainCatalogPage(device).navigateToComponents()
            ComponentsPage(device).navigateToMediaControlSheet()

            device.wait(
                Until.hasObject(By.descContains("Media control bar")),
                50.milliseconds.inWholeMilliseconds,
            )
        }
    ) {
        device.waitAndFindObject(By.descContains("Media control bar")).click()
    }
}
