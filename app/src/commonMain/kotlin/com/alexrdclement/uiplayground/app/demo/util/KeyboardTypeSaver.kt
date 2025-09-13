package com.alexrdclement.uiplayground.app.demo.util

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.SaverScope
import androidx.compose.ui.text.input.KeyboardType

private enum class KeyboardTypeEnum(
    val keyboardType: KeyboardType,
) {
    Text(KeyboardType.Text),
    Ascii(KeyboardType.Ascii),
    Number(KeyboardType.Number),
    Phone(KeyboardType.Phone),
    Uri(KeyboardType.Uri),
    Email(KeyboardType.Email),
    Password(KeyboardType.Password),
    NumberPassword(KeyboardType.NumberPassword),
}

private fun KeyboardType.toEnum() = KeyboardTypeEnum.entries
    .find { it.keyboardType == this } ?: KeyboardTypeEnum.Text

val KeyboardTypeSaver = object : Saver<KeyboardType, Any> {
    override fun SaverScope.save(value: KeyboardType): Any? {
        return value.toEnum().name
    }

    override fun restore(value: Any): KeyboardType? {
        return try {
            if (value is String) {
                KeyboardTypeEnum.valueOf(value).keyboardType
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
