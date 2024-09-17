package com.gkcoding.todoora.presentation.screens.addtask

import com.gkcoding.todoora.domain.model.Priority

data class AddTaskState(
    val title: String = "",
    val description: String = "",
    val dueDate: Long? = null,
    val priority: Priority = Priority.MEDIUM,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)