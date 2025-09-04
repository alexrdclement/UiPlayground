package com.alexrdclement.uiplayground.shaders

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.shaders.preview.DemoGrid
import com.alexrdclement.uiplayground.testing.PaparazziTestRule
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class WarpTest(
    @TestParameter(valuesProvider = AmountProvider::class)
    private val amount: Float,
) {

    object AmountProvider : TestParameter.TestParameterValuesProvider {
        override fun provideValues() = listOf(0f, .1f, .2f, .5f, 1f)
    }

    @get:Rule
    val paparazzi = PaparazziTestRule

    @Test
    fun gridWhite() {
        paparazzi.snapshot {
            DemoGrid(
                color = Color.White,
                background = Color.Black,
                modifier = Modifier
                    .size(400.dp, 800.dp)
                    .warp(
                        offset = { Offset(200f, 400f) },
                        radius = { 200.dp },
                        amount = { amount },
                    )
            )
        }
    }

    @Test
    fun gridBlack() {
        paparazzi.snapshot {
            DemoGrid(
                color = Color.Black,
                background = Color.White,
                modifier = Modifier
                    .size(400.dp, 800.dp)
                    .warp(
                        offset = { Offset(200f, 400f) },
                        radius = { 200.dp },
                        amount = { amount },
                    )
            )
        }
    }
}
