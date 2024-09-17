package com.gkcoding.todoora.utils

import androidx.compose.ui.graphics.Color

data object UiUtils {
    fun Color.toHexString(): String {
        return String.format(
            "#%02X%02X%02X",
            (red * 255).toInt(),
            (green * 255).toInt(),
            (blue * 255).toInt()
        )
    }

    fun String.toColor(): Color {
        return try {
            // Remove the leading '#' if present and parse the hex value to Int
            val colorInt = android.graphics.Color.parseColor(this)
            Color(colorInt)
        } catch (e: IllegalArgumentException) {
            Color.Black  // Default to black if the string is not a valid color
        }
    }
}