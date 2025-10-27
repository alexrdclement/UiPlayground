package com.alexrdclement.uiplayground.components.geometry

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.core.Surface
import com.alexrdclement.uiplayground.components.testing.ComponentsPaparazziTestRule
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class CurveStitchTest(
    @TestParameter(valuesProvider = NumLinesProvider::class)
    private val numLines: Int,
    @TestParameter(valuesProvider = NumPointsProvider::class)
    private val numPoints: Int,
) {

    @get:Rule
    val paparazzi = ComponentsPaparazziTestRule

    object NumLinesProvider : TestParameter.TestParameterValuesProvider {
        override fun provideValues() = listOf(2, 4, 8, 16, 32)
    }

    object NumPointsProvider : TestParameter.TestParameterValuesProvider {
        override fun provideValues() = listOf(3, 5, 7, 9)
    }

    @Test
    fun curveStitch() {
        paparazzi.snapshot {
            PlaygroundTheme {
                Surface {
                    CurveStitch(
                        start = Offset(0.1f, 0.1f),
                        vertex = Offset(0.1f, 0.9f),
                        end = Offset(0.9f, 0.9f),
                        numLines = numLines,
                        strokeWidth = Dp.Hairline,
                        color = PlaygroundTheme.colorScheme.primary,
                        modifier = Modifier.size(200.dp),
                    )
                }
            }
        }
    }

    @Test
    fun curveStitchStar() {
        paparazzi.snapshot {
            PlaygroundTheme {
                Surface {
                    CurveStitchStar(
                        numLines = numLines,
                        numPoints = numPoints,
                        strokeWidth = Dp.Hairline,
                        color = PlaygroundTheme.colorScheme.primary,
                        innerRadius = 0.5f,
                        drawInsidePoints = true,
                        drawOutsidePoints = true,
                        modifier = Modifier.size(200.dp),
                    )
                }
            }
        }
    }

    @Test
    fun curveStitchStarInsideOnly() {
        paparazzi.snapshot {
            PlaygroundTheme {
                Surface {
                    CurveStitchStar(
                        numLines = numLines,
                        numPoints = numPoints,
                        strokeWidth = Dp.Hairline,
                        color = PlaygroundTheme.colorScheme.primary,
                        innerRadius = 0.5f,
                        drawOutsidePoints = false,
                        modifier = Modifier.size(200.dp),
                    )
                }
            }
        }
    }

    @Test
    fun curveStitchStarOutsideOnly() {
        paparazzi.snapshot {
            PlaygroundTheme {
                Surface {
                    CurveStitchStar(
                        numLines = numLines,
                        numPoints = numPoints,
                        strokeWidth = Dp.Hairline,
                        color = PlaygroundTheme.colorScheme.primary,
                        innerRadius = 0.5f,
                        drawInsidePoints = true,
                        modifier = Modifier.size(200.dp),
                    )
                }
            }
        }
    }

    @Test
    fun curveStitchStarInnerRadius0() {
        paparazzi.snapshot {
            PlaygroundTheme {
                Surface {
                    CurveStitchStar(
                        numLines = numLines,
                        numPoints = numPoints,
                        strokeWidth = Dp.Hairline,
                        color = PlaygroundTheme.colorScheme.primary,
                        innerRadius = 0f,
                        modifier = Modifier.size(200.dp),
                    )
                }
            }
        }
    }

    @Test
    fun curveStitchShape() {
        paparazzi.snapshot {
            PlaygroundTheme {
                Surface {
                    CurveStitchShape(
                        numLines = numLines,
                        numPoints = numPoints,
                        strokeWidth = Dp.Hairline,
                        color = PlaygroundTheme.colorScheme.primary,
                        modifier = Modifier.size(200.dp),
                    )
                }
            }
        }
    }
}
