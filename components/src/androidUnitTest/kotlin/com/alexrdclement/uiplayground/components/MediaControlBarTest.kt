package com.alexrdclement.uiplayground.components

import com.alexrdclement.uiplayground.testing.PaparazziTestRule
import com.alexrdclement.uiplayground.theme.PlaygroundTheme
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class MediaControlBarTest(
    @TestParameter(valuesProvider = ProgressProvider::class)
    private val progress: Float,
) {

    object ProgressProvider : TestParameter.TestParameterValuesProvider {
        override fun provideValues() = listOf(0f, .1f, .2f, .5f, .75f, 1f)
    }

    @get:Rule
    val paparazzi = PaparazziTestRule

    @Test
    fun mediaControlBar() {
        paparazzi.snapshot {
            PlaygroundTheme {
                MediaControlBar(
                    mediaItem = testMediaItem,
                    isPlaying = false,
                    onPlayPauseClick = {},
                    progress = { progress }
                )
            }
        }
    }
}
