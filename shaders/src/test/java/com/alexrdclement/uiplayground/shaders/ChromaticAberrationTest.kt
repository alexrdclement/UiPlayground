package com.alexrdclement.uiplayground.shaders

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.alexrdclement.uiplayground.shaders.preview.DemoCircle
import com.alexrdclement.uiplayground.testing.PaparazziTestRule
import com.android.ide.common.rendering.api.SessionParams
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class ChromaticAberrationTest(
    private val xAmount: Float,
    private val yAmount: Float,
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}, {1}")
        fun data() = listOf(
            arrayOf(0f, 0f),
            arrayOf(0.1f, 0f),
            arrayOf(0f, 0.1f),
            arrayOf(0.2f, 0f),
            arrayOf(0f, 0.2f),
            arrayOf(0.2f, 0.2f),
        )
    }

    @get:Rule
    val paparazzi = PaparazziTestRule

    @Test
    fun blackCircle() {
        paparazzi.snapshot {
            DemoCircle(
                modifier = Modifier
                    .chromaticAberration(
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
                    .chromaticAberration(
                        xAmount = { xAmount },
                        yAmount = { yAmount },
                    )
            )
        }
    }
}
