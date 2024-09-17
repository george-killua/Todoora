package com.gkcoding.todoora.presentation.screens.edittask

import com.gkcoding.todoora.domain.model.Priority
import java.util.UUID

data class EditTaskState(
    val taskId: UUID = UUID.randomUUID(),
    val title: String = "",
    val description: String = "",
    val dueDate: Long? = null,
    val priority: Priority = Priority.MEDIUM,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)
