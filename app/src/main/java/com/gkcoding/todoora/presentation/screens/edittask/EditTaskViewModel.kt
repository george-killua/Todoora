package com.gkcoding.todoora.presentation.screens.edittask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gkcoding.todoora.domain.model.Task
import com.gkcoding.todoora.domain.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EditTaskState())
    val state: StateFlow<EditTaskState> = _state

    fun onAction(action: EditTaskAction) {
        when (action) {
            is EditTaskAction.TitleChanged -> _state.value = _state.value.copy(title = action.title)
            is EditTaskAction.DescriptionChanged -> _state.value =
                _state.value.copy(description = action.description)

            is EditTaskAction.DueDateChanged -> _state.value =
                _state.value.copy(dueDate = action.dueDate)

            is EditTaskAction.PriorityChanged -> _state.value =
                _state.value.copy(priority = action.priority)

            is EditTaskAction.SubmitTask -> submitTask()
            is EditTaskAction.LoadTask -> loadTask(_state.value.taskId.toString())
        }
    }

    fun loadTask(taskId: String) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val task = taskRepository.getTaskById(taskId)
                task?.let {
                    _state.value = _state.value.copy(
                        taskId = it.taskId,
                        title = it.title,
                        description = it.description ?: "",
                        dueDate = it.dueDate,
                        priority = it.priority,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }

    private fun submitTask() {
        if (_state.value.title.isEmpty()) {
            _state.value = _state.value.copy(errorMessage = "Title cannot be empty")
            return
        }

        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val updatedTask = Task(
                    taskId = _state.value.taskId,
                    title = _state.value.title,
                    description = _state.value.description,
                    dueDate = _state.value.dueDate,
                    priority = _state.value.priority,
                    isCompleted = false, // You can adjust this logic if needed
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
                taskRepository.updateTask(updatedTask)
                _state.value = _state.value.copy(isSuccess = true, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }
}