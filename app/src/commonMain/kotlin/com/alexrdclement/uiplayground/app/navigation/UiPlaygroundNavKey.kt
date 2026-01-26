package com.alexrdclement.uiplayground.app.navigation

import kotlin.jvm.JvmInline

interface UiPlaygroundNavKey {
    companion object
    val pathSegment: PathSegment
    val isDialog: Boolean
        get() = false
}

@JvmInline
value class PathSegment(val value: String) {
    companion object {
        val Wildcard = PathSegment("*")
    }
}

fun String.toPathSegment() = PathSegment(
    value = this.replace(Regex("([a-z])([A-Z])"), "$1-$2").lowercase(),
)
