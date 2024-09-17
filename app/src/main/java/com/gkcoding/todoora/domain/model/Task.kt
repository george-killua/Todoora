package com.gkcoding.todoora.domain.model

import androidx.compose.runtime.Immutable
import com.gkcoding.todoora.data.local.entity.TaskEntity
import com.gkcoding.todoora.data.remote.models.TaskRemoteModel
import java.util.UUID

@Immutable
data class Task(
    val taskId: UUID = UUID.randomUUID(), // Unique ID for the task,
    val title: String,           // Title of the task
    val description: String? = null,// Optional description for the task
    val category: Category = Category(),
    val dueDate: Long? = null,   // Due date as a timestamp (null if no due date)
    val priority: Priority = Priority.MEDIUM, // Enum for priority (low, medium, high)
    val isCompleted: Boolean = false, // Boolean to mark whether the task is completed
    val createdAt: Long = System.currentTimeMillis(), // Timestamp when the task was created
    val updatedAt: Long = System.currentTimeMillis()  // Timestamp when the task was last updated
) {
    fun toEntity(): TaskEntity {
        return TaskEntity(
            taskId = taskId.toString(),
            title = title,
            description = description,
            categoryId = category.categoryId.toString(),
            dueDate = dueDate,
            priority = priority.ordinal,
            isCompleted = isCompleted,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    fun toRemoteModel(): TaskRemoteModel {
        return TaskRemoteModel(
            id = taskId.toString(),
            title = title,
            description = description,
            categoryId = category.categoryId.toString(),
            dueDate = dueDate,
            isCompleted = isCompleted,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}
