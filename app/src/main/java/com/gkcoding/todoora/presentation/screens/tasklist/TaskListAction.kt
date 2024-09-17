package com.gkcoding.todoora.presentation.screens.tasklist

import com.gkcoding.todoora.domain.model.Task

sealed class TaskListAction {
    data class CompleteTask(val task: Task) : TaskListAction()
    data class DeleteTask(val taskId: String) : TaskListAction()
}