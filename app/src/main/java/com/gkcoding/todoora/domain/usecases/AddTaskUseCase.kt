package com.gkcoding.todoora.domain.usecases

import com.gkcoding.todoora.domain.model.Task
import com.gkcoding.todoora.domain.repository.TaskRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(task: Task) {
        taskRepository.addTask(task.copy(updatedAt = System.currentTimeMillis()))
    }
}