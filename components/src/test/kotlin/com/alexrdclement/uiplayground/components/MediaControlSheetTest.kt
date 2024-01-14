package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.GraphicsMode

@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(RobolectricTestRunner::class)
class MediaControlSheetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Composable
    private fun ComposableUnderTest(
        state: MediaControlSheetState = rememberMediaControlSheetState(),
    ) {
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

    @Test
    fun partiallyExpanded_snapshot() {
        captureRoboImage {
            val state = rememberMediaControlSheetState(
                initialValue = MediaControlSheetAnchorState.PartiallyExpanded,
            )
            ComposableUnderTest(
                state = state,
            )
        }
    }

    @Test
    fun expanded_snapshot() {
        captureRoboImage {
            val state = rememberMediaControlSheetState(
                initialValue = MediaControlSheetAnchorState.Expanded,
            )
            ComposableUnderTest(
                state = state,
            )
        }
    }
}
