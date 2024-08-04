package com.alexrdclement.uiplayground.shaders

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.alexrdclement.uiplayground.shaders.preview.DemoCircle
import com.alexrdclement.uiplayground.testing.PaparazziTestRule
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class NoiseTest(
    @TestParameter(valuesProvider = ValuesProvider::class)
    private val values: Pair<Float, NoiseColorMode>,
) {
    private val amount = values.first
    private val colorMode = values.second

    object ValuesProvider : TestParameter.TestParameterValuesProvider {
        override fun provideValues() = listOf(0f, .1f, .2f, .5f, 1f).flatMap { amount ->
            listOf(
                NoiseColorMode.Monochrome,
                NoiseColorMode.RandomColor,
                NoiseColorMode.RandomColorFilterBlack,
            ).map { colorEnabled ->
                Pair(amount, colorEnabled)
            }
        }
    }

    @get:Rule
    val paparazzi = PaparazziTestRule

    @Test
    fun blackCircle() {
        paparazzi.snapshot {
            DemoCircle(
                modifier = Modifier
                    .noise(colorMode) { amount }
            )
        }
    }

    @Test
    fun whiteCircle() {
        paparazzi.snapshot {
            DemoCircle(
                color = Color.White,
                background = Color.Black,
                modifier = Modifier
                    .noise(colorMode) { amount }
            )
        }
    }
}
