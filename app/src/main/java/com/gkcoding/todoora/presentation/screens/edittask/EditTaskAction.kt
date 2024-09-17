package com.gkcoding.todoora.presentation.screens.edittask

import com.gkcoding.todoora.domain.model.Priority

sealed class EditTaskAction {
    data class TitleChanged(val title: String) : EditTaskAction()
    data class DescriptionChanged(val description: String) : EditTaskAction()
    data class DueDateChanged(val dueDate: Long) : EditTaskAction()
    data class PriorityChanged(val priority: Priority) : EditTaskAction()
    data object SubmitTask : EditTaskAction()
    data object LoadTask : EditTaskAction()
}

