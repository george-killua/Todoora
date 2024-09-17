package com.gkcoding.todoora.presentation.screens.tasklist

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
class TaskListViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _state = MutableStateFlow(TaskListState(tasks = taskRepository.getAllTasksPaged()))
    val state: StateFlow<TaskListState> = _state


    fun onAction(action: TaskListAction) {
        when (action) {
            is TaskListAction.CompleteTask -> completeTask(action.task)
            is TaskListAction.DeleteTask -> deleteTask(action.taskId)
        }
    }


    private fun completeTask(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task.copy(isCompleted = !task.isCompleted))
        }
    }

    private fun deleteTask(taskId: String) {
        viewModelScope.launch {
            taskRepository.deleteTask(taskId)
        }
    }

}
