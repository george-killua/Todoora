package com.gkcoding.todoora.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.gkcoding.todoora.domain.model.Category
import com.gkcoding.todoora.utils.UiUtils.toColor
import java.util.UUID

@Entity(
    tableName = "categories",
    indices = [Index(value = ["sort_index"], unique = false), Index(
        value = ["category_id"],
        unique = true
    )]  // Adds index on sort_index
)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "category_id")  // Default value should be handled in code or during insertion
    val categoryId: String = UUID.fromString("8a4b4d88-5116-4231-ae9b-f1ea96a95cde").toString(),

    @ColumnInfo(name = "name")
    val name: String = "Default",  // Default value handled in Kotlin code

    @ColumnInfo(name = "color")
    val color: String = "#FFFFFF",  // Default color in hex

    @ColumnInfo(name = "sort_index")
    val sortIndex: Int = 0  // Default value for sorting categories
) {
    // Converts CategoryEntity to Domain Model Category
    fun toDomainModel(): Category {
        return Category(
            categoryId = UUID.fromString(categoryId),
            name = name,
            color = color.toColor(),
            sortIndex = sortIndex
        )
    }
}
