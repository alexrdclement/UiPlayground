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
class ColorSplitTest(
    @TestParameter(valuesProvider = AmountProvider::class)
    private val amounts: Pair<Float, Float>,
) {
    private val xAmount = amounts.first
    private val yAmount = amounts.second

    object AmountProvider : TestParameter.TestParameterValuesProvider {
        override fun provideValues() = listOf(
            Pair(0f, 0f),
            Pair(0.1f, 0f),
            Pair(0f, 0.1f),
            Pair(0.2f, 0f),
            Pair(0f, 0.2f),
            Pair(0.2f, 0.2f),
        )
    }

    @get:Rule
    val paparazzi = PaparazziTestRule

    @Test
    fun blackCircle() {
        paparazzi.snapshot {
            DemoCircle(
                modifier = Modifier
                    .colorSplit(
                        xAmount = { xAmount },
                        yAmount = { yAmount },
                    )
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
                    .colorSplit(
                        xAmount = { xAmount },
                        yAmount = { yAmount },
                    )
            )
        }
    }
}
