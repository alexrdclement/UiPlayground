package com.alexrdclement.uiplayground.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasStateDescription
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performClick
import com.alexrdclement.uiplayground.components.model.Artist
import com.alexrdclement.uiplayground.components.model.MediaItem
import kotlinx.coroutines.launch
import org.junit.Rule
import org.junit.Test

class MediaControlSheetTest {

    @get:Rule
    val rule = createComposeRule()

    private val mediaItem = MediaItem(
        artworkThumbnailUrl = null,
        artworkLargeUrl = null,
        title = "Title",
        artists = listOf(Artist("Artist 1"), Artist("Artist 2")),
    )

    @Composable
    private fun ComposableUnderTest(
        initialValue: MediaControlSheetAnchorState,
    ) {
        var isPlaying by remember { mutableStateOf(false) }
        val state = rememberMediaControlSheetState(
            initialValue = initialValue,
        )
        val coroutineScope = rememberCoroutineScope()
        MediaControlSheet(
            mediaItem = mediaItem,
            isPlaying = isPlaying,
            onPlayPauseClick = { isPlaying = !isPlaying },
            onControlBarClick = {
                coroutineScope.launch {
                    if (state.isExpanded) {
                        state.partialExpand()
                    } else {
                        state.expand()
                    }
                }
            },
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
    fun partiallyExpandedToExpanded_updatesStateDescription() {
        rule.setContent {
            ComposableUnderTest(
                initialValue = MediaControlSheetAnchorState.PartiallyExpanded,
            )
        }

        val mediaControlBar = rule.onNode(
            hasContentDescription(MediaControlBarContentDescription)
        )
        mediaControlBar.performClick()

        mediaControlBar.assert(
            hasStateDescription(MediaControlBarStateDescriptionExpanded)
        )
    }

    @Test
    fun expandedToPartiallyExpanded_updatesStateDescription() {
        rule.setContent {
            ComposableUnderTest(
                initialValue = MediaControlSheetAnchorState.Expanded,
            )
        }

        val mediaControlBar = rule.onNode(
            hasContentDescription(MediaControlBarContentDescription)
        )
        mediaControlBar.performClick()

        mediaControlBar.assert(
            hasStateDescription(MediaControlBarStateDescriptionPartiallyExpanded)
        )
    }
}
