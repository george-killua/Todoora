package com.gkcoding.todoora.presentation.screens.addtask

import com.gkcoding.todoora.domain.model.Priority

sealed class AddTaskAction {
    data class TitleChanged(val title: String) : AddTaskAction()
    data class DescriptionChanged(val description: String) : AddTaskAction()
    data class DueDateChanged(val dueDate: Long) : AddTaskAction()
    data class PriorityChanged(val priority: Priority) : AddTaskAction()
    data object SubmitTask : AddTaskAction()
}
