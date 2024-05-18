package com.alexrdclement.uiplayground.benchmark.shaders

import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.TraceSectionMetric
import androidx.benchmark.macro.TraceSectionMetric.Mode
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alexrdclement.uiplayground.MainCatalogPage
import com.alexrdclement.uiplayground.appPackageName
import com.alexrdclement.uiplayground.shaders.ShadersPage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChromaticAberrationBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    lateinit var shadersPage: ShadersPage

    @Test
    fun compilationModeNone() = xyAdjustment(CompilationMode.None())

    @Test
    fun compilationModePartial() = xyAdjustment(CompilationMode.Partial(BaselineProfileMode.Require))

    @OptIn(ExperimentalMetricApi::class)
    fun xyAdjustment(compilationMode: CompilationMode) = benchmarkRule.measureRepeated(
        packageName = appPackageName,
        metrics = listOf(
            FrameTimingMetric(),
            TraceSectionMetric("chromaticAberration", Mode.Sum),
        ),
        iterations = 5,
        startupMode = StartupMode.WARM,
        compilationMode = compilationMode,
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
