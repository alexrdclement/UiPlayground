package com.alexrdclement.uiplayground.components

import com.alexrdclement.uiplayground.components.model.Artist
import com.alexrdclement.uiplayground.components.model.MediaItem

val testMediaItem = MediaItem(
    artworkThumbnailUrl = null,
    artworkLargeUrl = null,
    title = "Title",
    artists = listOf(Artist("Artist 1"), Artist("Artist 2")),
)
