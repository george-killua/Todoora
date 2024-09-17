package com.gkcoding.todoora.presentation.screens.tasklist

import androidx.paging.PagingData
import com.gkcoding.todoora.domain.model.CategorizedTasks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class TaskListState(
    val tasks: Flow<PagingData<CategorizedTasks>> = emptyFlow(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)