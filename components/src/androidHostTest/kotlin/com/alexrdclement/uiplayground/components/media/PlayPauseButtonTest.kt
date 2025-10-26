package com.alexrdclement.uiplayground.components.media

import com.alexrdclement.uiplayground.testing.PaparazziTestRule
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class PlayPauseButtonTest {

    @get:Rule
    val paparazzi = PaparazziTestRule

    @Test
    fun playPauseButton(
        @TestParameter isPlaying: Boolean,
        @TestParameter isEnabled: Boolean,
    ) {
        paparazzi.snapshot {
            PlaygroundTheme {
                PlayPauseButton(
                    isPlaying = isPlaying,
                    isEnabled = isEnabled,
                    onClick = {}
                )
            }
        }
    }
}
