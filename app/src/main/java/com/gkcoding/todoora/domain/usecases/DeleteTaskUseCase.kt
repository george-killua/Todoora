package com.gkcoding.todoora.domain.usecases

import com.gkcoding.todoora.domain.model.Task
import com.gkcoding.todoora.domain.repository.TaskRepository

class DeleteTaskUseCase(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: Task) {
//        taskRepository.deleteTask(task.taskId)
    }

}