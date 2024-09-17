package com.gkcoding.todoora.data.local

import androidx.paging.PagingSource
import com.gkcoding.todoora.data.local.entity.TaskWithCategory
import com.gkcoding.todoora.domain.model.Task

interface TaskLocalDataSource {
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(taskId: String)
    suspend fun getTaskById(taskId: String): Task?
    fun getAllTasks(): PagingSource<Int, TaskWithCategory>
    fun getCompletedTasks(): PagingSource<Int, TaskWithCategory>
    fun getPendingTasks(): PagingSource<Int, TaskWithCategory>
}