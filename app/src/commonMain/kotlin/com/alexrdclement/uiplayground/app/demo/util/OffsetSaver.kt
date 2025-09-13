package com.alexrdclement.uiplayground.app.demo.util

import androidx.compose.runtime.saveable.listSaver
import androidx.compose.ui.geometry.Offset

val OffsetSaver = listSaver(
    save = { listOf(it.x, it.y) },
    restore = {
        if (it.size != 2) {
            null
        } else {
            Offset(it[0], it[1])
        }
    }
)
