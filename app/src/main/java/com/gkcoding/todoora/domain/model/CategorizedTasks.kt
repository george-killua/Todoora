package com.gkcoding.todoora.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class CategorizedTasks(
    val category: Category, val tasks: List<Task> = listOf()
)
