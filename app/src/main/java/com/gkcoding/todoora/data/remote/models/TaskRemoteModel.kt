package com.gkcoding.todoora.data.remote.models

import com.gkcoding.todoora.domain.model.Task
import java.util.UUID


data class TaskRemoteModel(
    val id: String = "",
    val title: String = "",
    val description: String? = null,
    val categoryId: String = "",
    val dueDate: Long? = null,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
) {
    fun toDomainModel(): Task {
        return Task(
            taskId = UUID.fromString(id),
            title = title,
            description = description,
            dueDate = dueDate,
            isCompleted = isCompleted,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

}
