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
class PixelateTest(
    @TestParameter(valuesProvider = SubdivisionProvider::class)
    private val subdivisions: Int,
) {

    object SubdivisionProvider : TestParameter.TestParameterValuesProvider {
        override fun provideValues() = listOf(0, 1, 2, 5, 10, 25, 50, 100)
    }

    @get:Rule
    val paparazzi = PaparazziTestRule

    @Test
    fun blackCircle() {
        paparazzi.snapshot {
            DemoCircle(
                modifier = Modifier
                    .pixelate { subdivisions }
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
                    .pixelate { subdivisions }
            )
        }
    }
}
