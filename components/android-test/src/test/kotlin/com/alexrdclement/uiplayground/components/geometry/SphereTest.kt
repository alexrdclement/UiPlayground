package com.alexrdclement.uiplayground.components.geometry

import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alexrdclement.uiplayground.components.core.Surface
import com.alexrdclement.uiplayground.components.util.ViewingAngle
import com.alexrdclement.uiplayground.components.testing.ComponentsPaparazziTestRule
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class SphereTest {
    @get:Rule
    val paparazzi = ComponentsPaparazziTestRule

    @Test
    fun gridSphere() {
        paparazzi.snapshot {
            PlaygroundTheme {
                Surface {
                    GridSphere(
                        numLatitudeLines = 20,
                        numLongitudeLines = 10,
                        modifier = Modifier.size(200.dp),
                        viewingAngle = ViewingAngle(
                            rotationX = 20f,
                        ),
                    )
                }
            }
        }
    }
}

