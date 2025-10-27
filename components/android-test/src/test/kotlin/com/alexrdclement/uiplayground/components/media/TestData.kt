package com.alexrdclement.uiplayground.components.media

import com.alexrdclement.uiplayground.components.media.model.Artist
import com.alexrdclement.uiplayground.components.media.model.MediaItem

val testMediaItem = MediaItem(
    artworkThumbnailUrl = null,
    artworkLargeUrl = null,
    title = "Title",
    artists = listOf(Artist("Artist 1"), Artist("Artist 2")),
)
