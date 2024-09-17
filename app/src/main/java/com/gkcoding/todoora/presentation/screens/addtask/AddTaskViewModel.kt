package com.gkcoding.todoora.presentation.screens.addtask

import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gkcoding.todoora.domain.model.Task
import com.gkcoding.todoora.domain.repository.TaskRepository
import com.gkcoding.todoora.utils.SELECTED_DATE_ARGUMENT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

fun <T> SavedStateHandle.getStateFlow(
    key: String,
    scope: CoroutineScope,
    initialValue: T? = get(key)
): MutableStateFlow<T?> = this.let { handle ->
    val liveData = handle.getLiveData<T?>(key, initialValue).also { liveData ->
        if (liveData.value === initialValue) {
            liveData.value = initialValue
        }
    }
    val mutableStateFlow = MutableStateFlow(liveData.value)

    val observer: Observer<T?> = Observer { value ->
        if (value != mutableStateFlow.value) {
            mutableStateFlow.value = value
        }
    }
    liveData.observeForever(observer)

    scope.launch {
        mutableStateFlow.also { flow ->
            flow.onCompletion {
                withContext(Dispatchers.Main.immediate) {
                    liveData.removeObserver(observer)
                }
            }.collect { value ->
                withContext(Dispatchers.Main.immediate) {
                    if (liveData.value != value) {
                        liveData.value = value
                    }
                }
            }
        }
    }
    mutableStateFlow
}

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository
) : ViewModel() {
    private val stateChanged =
        savedStateHandle.getStateFlow(
            SELECTED_DATE_ARGUMENT,
            viewModelScope,
            System.currentTimeMillis()
        )
    private val _state = MutableStateFlow(AddTaskState())
    val state: StateFlow<AddTaskState> = _state.asStateFlow()

    init {
        // Observe changes to selectedDate and update the state
        viewModelScope.launch {
            savedStateHandle.getStateFlow(SELECTED_DATE_ARGUMENT, System.currentTimeMillis())
                .collect { date ->
                    Log.d("AddTaskViewModel", "Selected Date: $date")
                    _state.value = _state.value.copy(dueDate = date)
                }
        }
    }

    fun onAction(action: AddTaskAction) {
        when (action) {
            is AddTaskAction.TitleChanged -> _state.value = _state.value.copy(title = action.title)
            is AddTaskAction.DescriptionChanged -> _state.value =
                _state.value.copy(description = action.description)

            is AddTaskAction.DueDateChanged -> _state.value =
                _state.value.copy(dueDate = action.dueDate)

            is AddTaskAction.PriorityChanged -> _state.value =
                _state.value.copy(priority = action.priority)

            is AddTaskAction.SubmitTask -> submitTask()
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
                val newTask = Task(

                    title = _state.value.title,
                    description = _state.value.description,
                    dueDate = _state.value.dueDate,
                    priority = _state.value.priority,
                    isCompleted = false,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis()
                )
                taskRepository.addTask(newTask)
                _state.value = _state.value.copy(isSuccess = true, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, errorMessage = e.message)
            }
        }
    }
}