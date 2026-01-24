package com.alexrdclement.uiplayground.app.navigation

import kotlin.jvm.JvmInline

interface UiPlaygroundNavKey {
    val pathSegment: PathSegment
    val isDialog: Boolean
        get() = false
}

@JvmInline
value class PathSegment(val value: String)

fun String.toPathSegment() = PathSegment(
    value = this.replace(Regex("([a-z])([A-Z])"), "$1-$2").lowercase(),
)
