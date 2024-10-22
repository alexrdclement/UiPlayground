package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.alexrdclement.uiplayground.testing.PaparazziTestRule
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class MediaControlSheetTest(
    @TestParameter(valuesProvider = AnchorProvider::class)
    private val anchor: MediaControlSheetAnchor,
) {

    object AnchorProvider : TestParameter.TestParameterValuesProvider {
        override fun provideValues() = listOf(
            MediaControlSheetAnchor.PartiallyExpanded,
            MediaControlSheetAnchor.Expanded
        )
    }

    @get:Rule
    val paparazzi = PaparazziTestRule

    @Test
    fun mediaControlSheet() {
        paparazzi.snapshot {
            val state = rememberMediaControlSheetState(
                initialValue = anchor,
            )
            MediaControlSheet(
                mediaItem = testMediaItem,
                isPlaying = false,
                onPlayPauseClick = {},
                onControlBarClick = {},
                state = state,
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Text("Content")
                }
            }
        }
    }
}
