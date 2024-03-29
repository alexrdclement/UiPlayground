package com.alexrdclement.uiplayground.shaders

import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.TraceSectionMetric
import androidx.benchmark.macro.TraceSectionMetric.Mode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexrdclement.uiplayground.MainCatalogPage
import com.alexrdclement.uiplayground.appPackageName
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChromaticAberrationBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    lateinit var shadersPage: ShadersPage

    @OptIn(ExperimentalMetricApi::class)
    @Test
    fun xyAdjustment() = benchmarkRule.measureRepeated(
        packageName = appPackageName,
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
            shadersPage = ShadersPage(device).apply { selectChromaticAberration() }
        }
    ) {
        shadersPage.adjustChromaticAberration()
    }
}
