package com.gkcoding.todoora.domain.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.gkcoding.todoora.utils.UiUtils.toColor
import java.util.UUID

@Immutable
data class Category(
    val categoryId: UUID = UUID.fromString("8a4b4d88-5116-4231-ae9b-f1ea96a95cde"),  // Unique ID for the category
    val name: String = "Default",  // Default name for the category
    val color: Color = "#FFFFFF".toColor(),  // Default color (white)
    val sortIndex: Int = 0
)