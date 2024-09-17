package com.gkcoding.todoora.data.local

import androidx.paging.PagingSource
import com.gkcoding.todoora.data.local.dao.TaskDao
import com.gkcoding.todoora.data.local.entity.TaskWithCategory
import com.gkcoding.todoora.domain.model.Task
import javax.inject.Inject

class TaskLocalDataSourceImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskLocalDataSource {

    override suspend fun addTask(task: Task) {
        taskDao.insertTask(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.updateTask(
            taskId = task.taskId.toString(),
            title = task.title,
            categoryId = task.category.categoryId.toString(),
            description = task.description,
            isCompleted = task.isCompleted,
            priority = task.priority.ordinal,
            dueDate = task.dueDate,
            updatedAt = System.currentTimeMillis()
        )
    }

    override suspend fun deleteTask(taskId: String) {
        taskDao.deleteTaskById(taskId)
    }

    override suspend fun getTaskById(taskId: String): Task? {
        return taskDao.getTaskById(taskId)?.toDomainModel()
    }

    override fun getAllTasks(): PagingSource<Int, TaskWithCategory> {
        return taskDao.getTasksWithCategoryPaged()
    }

    override fun getCompletedTasks(): PagingSource<Int, TaskWithCategory> {
        return taskDao.getCompletedTasksByCategory()
    }

    override fun getPendingTasks(): PagingSource<Int, TaskWithCategory> {
        return taskDao.getUncompletedTasksByCategory()
    }
}
