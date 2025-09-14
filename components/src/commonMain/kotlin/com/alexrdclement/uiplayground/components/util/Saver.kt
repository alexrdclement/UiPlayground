package com.alexrdclement.uiplayground.components.util

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.runtime.saveable.mapSaver

fun <T> mapSaverSafe(
    save: SaverScope.(value: T) -> Map<String, Any?>,
    restore: (Map<String, Any?>) -> T?,
    onFailure: (Throwable) -> T? = { null },
) = mapSaver(
    save = save,
    restore = { map -> runCatching { restore(map) }.getOrElse(onFailure) },
)

// Copied from androidx.compose.ui.text

fun <T : Saver<Original, Saveable>, Original, Saveable> save(
    value: Original?,
    saver: T,
    scope: SaverScope
): Any {
    return value?.let { with(saver) { scope.save(value) } } ?: false
}

inline fun <T : Saver<Original, Saveable>, Original, Saveable, reified Result> restore(
    value: Saveable?,
    saver: T
): Result? {
    // Most of the types we save are nullable. However, value classes are usually not but instead
    // have a special Unspecified value. In that case we delegate handling of the "false"
    // value restoration to the corresponding saver that will restore "false" as an Unspecified
    // of the corresponding type.
    if (value == false && saver !is NonNullValueClassSaver<*, *>) return null
    return value?.let { with(saver) { restore(value) } as Result }
}

internal interface NonNullValueClassSaver<Original, Saveable : Any> : Saver<Original, Saveable>
