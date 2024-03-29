package com.alexrdclement.uiplayground

import androidx.benchmark.macro.BaselineProfileMode
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startupCompilationNone() =
        benchmark(CompilationMode.None())

    @Test
    fun startupCompilationBaselineProfiles() =
        benchmark(CompilationMode.Partial(BaselineProfileMode.Require))

    private fun benchmark(compilationMode: CompilationMode) {
        benchmarkRule.measureRepeated(
            packageName = appPackageName,
            metrics = listOf(StartupTimingMetric()),
            iterations = 10,
            compilationMode = compilationMode,
            startupMode = StartupMode.COLD,
            setupBlock = {
                pressHome()
            }
        ) {
            startActivityAndWait()
        }
    }
}
