package com.gkcoding.todoora.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.gkcoding.todoora.data.remote.models.TaskRemoteModel
import com.gkcoding.todoora.domain.model.Priority
import com.gkcoding.todoora.domain.model.Task
import java.util.UUID

@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["category_id"],
            childColumns = ["category_id"],
            onDelete = ForeignKey.CASCADE  // Cascade delete: when a category is deleted, its tasks will also be deleted
        )
    ],
    indices = [Index(value = ["task_id"], unique = true), Index(
        value = ["category_id"],
        unique = false
    )]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val entityId: Int = 0,
    @ColumnInfo(name = "task_id") val taskId: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(
        name = "category_id",
        defaultValue = "8a4b4d88-5116-4231-ae9b-f1ea96a95cde"
    ) val categoryId: String? = "8a4b4d88-5116-4231-ae9b-f1ea96a95cde",
    @ColumnInfo(name = "description") val description: String? = null,

    @ColumnInfo(name = "is_completed") val isCompleted: Boolean = false,

    @ColumnInfo(
        name = "priority",
        defaultValue = "0"
    ) val priority: Int = 0, // Can be 0 = Low, 1 = Medium, 2 = High

    @ColumnInfo(name = "due_date") val dueDate: Long? = null,

    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "updated_at", index = true) val updatedAt: Long = System.currentTimeMillis()
) {

    fun toDomainModel(): Task {
        return Task(taskId = UUID.fromString(taskId),
            title = title,
            description = description,
            dueDate = dueDate,
            priority = Priority.entries.find { it.ordinal == priority } ?: Priority.LOW,
            isCompleted = isCompleted,
            createdAt = createdAt,
            updatedAt = updatedAt)
    }

    fun toRemoteModel(): TaskRemoteModel {

        return TaskRemoteModel(
            id = taskId,
            title = title,
            description = description,
            dueDate = dueDate,
            isCompleted = isCompleted,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}

