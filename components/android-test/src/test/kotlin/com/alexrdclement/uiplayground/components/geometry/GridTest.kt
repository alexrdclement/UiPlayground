package com.alexrdclement.uiplayground.components.geometry

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.core.Surface
import com.alexrdclement.uiplayground.components.testing.ComponentsPaparazziTestRule
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.PI

@RunWith(TestParameterInjector::class)
class GridTest {
    @get:Rule
    val paparazzi = ComponentsPaparazziTestRule


    @Test
    fun cartesianGrid() {
        paparazzi.snapshot {
            PlaygroundTheme {
                Surface {
                    CartesianGrid(
                        xSpacing = { 20.dp.toPx() },
                        ySpacing = { 20.dp.toPx() },
                        lineStyle = GridLineStyle(
                            color = PlaygroundTheme.colorScheme.primary,
                            stroke = Stroke(width = 1f),
                        ),
                        modifier = Modifier.size(200.dp),
                        offset = Offset(0f, 10f),
                    )
                }
            }
        }
    }

    @Test
    fun cartesianGridLogarithmicScale() {
        paparazzi.snapshot {
            PlaygroundTheme {
                Surface {
                    Grid(
                        coordinateSystem = GridCoordinateSystem.Cartesian(
                            scaleX = GridScale.Logarithmic(spacing = 1.dp, base = 2f),
                            scaleY = GridScale.Logarithmic(spacing = 1.dp, base = 2f),
                        ),
                        lineStyle = GridLineStyle(
                            color = PlaygroundTheme.colorScheme.primary,
                            stroke = Stroke(width = 1f),
                        ),
                        modifier = Modifier.size(200.dp),
                    )
                }
            }
        }
    }

    @Test
    fun cartesianGridLogarithmicDecayScale() {
        paparazzi.snapshot {
            PlaygroundTheme {
                Surface {
                    Grid(
                        coordinateSystem = GridCoordinateSystem.Cartesian(
                            scaleX = GridScale.LogarithmicDecay(spacing = 50.dp, base = 2f),
                            scaleY = GridScale.LogarithmicDecay(spacing = 50.dp, base = 2f),
                        ),
                        lineStyle = GridLineStyle(
                            color = PlaygroundTheme.colorScheme.primary,
                            stroke = Stroke(width = 1f),
                        ),
                        modifier = Modifier.size(200.dp),
                    )
                }
            }
        }
    }

    @Test
    fun cartesianGridExponentialScale() {
        paparazzi.snapshot {
            PlaygroundTheme {
                Surface {
                    Grid(
                        coordinateSystem = GridCoordinateSystem.Cartesian(
                            scaleX = GridScale.Exponential(spacing = 1.dp, exponent = 2f),
                            scaleY = GridScale.Exponential(spacing = 1.dp, exponent = 2f),
                        ),
                        lineStyle = GridLineStyle(
                            color = PlaygroundTheme.colorScheme.primary,
                            stroke = Stroke(width = 1f),
                        ),
                        modifier = Modifier.size(200.dp),
                    )
                }
            }
        }
    }

    @Test
    fun cartesianGridExponentialDecayScale() {
        paparazzi.snapshot {
            PlaygroundTheme {
                Surface {
                    Grid(
                        coordinateSystem = GridCoordinateSystem.Cartesian(
                            scaleX = GridScale.ExponentialDecay(spacing = 100.dp, exponent = 2f),
                            scaleY = GridScale.ExponentialDecay(spacing = 100.dp, exponent = 2f),
                        ),
                        lineStyle = GridLineStyle(
                            color = PlaygroundTheme.colorScheme.primary,
                            stroke = Stroke(width = 1f),
                        ),
                        modifier = Modifier.size(200.dp),
                    )
                }
            }
        }
    }

    @Test
    fun polarGrid() {
        paparazzi.snapshot {
            PlaygroundTheme {
                Surface {
                    PolarGrid(
                        radiusSpacing = { 30.dp.toPx() },
                        theta = { (PI / 3).toFloat() },
                        lineStyle = GridLineStyle(
                            color = PlaygroundTheme.colorScheme.primary,
                            stroke = Stroke(width = 1f),
                        ),
                        modifier = Modifier.size(200.dp),
                    )
                }
            }
        }
    }

    @Test
    fun ovalGrid() {
        val coordinateSystem = GridCoordinateSystem.Cartesian(spacing = 20.dp)
        paparazzi.snapshot {
            PlaygroundTheme {
                Surface {
                    Grid(
                        coordinateSystem = coordinateSystem,
                        lineStyle = null,
                        vertex = GridVertex.Oval(
                            color = PlaygroundTheme.colorScheme.primary,
                            size = DpSize(
                                PlaygroundTheme.spacing.xs / 2f,
                                PlaygroundTheme.spacing.xs / 2f
                            ),
                            drawStyle = Stroke(width = 1f),
                        ),
                        modifier = Modifier.size(200.dp),
                        offset = Offset(80f, 10f),
                    )
                }
            }
        }
    }

    @Test
    fun rectGrid() {
        val coordinateSystem = GridCoordinateSystem.Cartesian(spacing = 20.dp)
        paparazzi.snapshot {
            PlaygroundTheme {
                Surface {
                    Grid(
                        coordinateSystem = coordinateSystem,
                        lineStyle = null,
                        vertex = GridVertex.Rect(
                            color = PlaygroundTheme.colorScheme.primary,
                            size = DpSize(
                                PlaygroundTheme.spacing.small,
                                PlaygroundTheme.spacing.small
                            ),
                            drawStyle = Stroke(width = 1f),
                        ),
                        modifier = Modifier.size(200.dp),
                        offset = Offset(80f, 10f),
                    )
                }
            }
        }
    }

    @Test
    fun plusGrid() {
        val coordinateSystem = GridCoordinateSystem.Cartesian(spacing = 20.dp)
        paparazzi.snapshot {
            PlaygroundTheme {
                Surface {
                    Grid(
                        coordinateSystem = coordinateSystem,
                        lineStyle = null,
                        vertex = GridVertex.Plus(
                            color = PlaygroundTheme.colorScheme.primary,
                            size = DpSize(
                                PlaygroundTheme.spacing.small,
                                PlaygroundTheme.spacing.small
                            ),
                            strokeWidth = Dp.Hairline,
                        ),
                        modifier = Modifier.size(200.dp),
                        offset = Offset(80f, 0f),
                    )
                }
            }
        }
    }
}
