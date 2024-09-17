package com.gkcoding.todoora.domain.usecases

import com.gkcoding.todoora.domain.repository.TaskRepository
import javax.inject.Inject

class UploadTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke() {
        repository.uploadTasks()
    }
}