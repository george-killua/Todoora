package com.gkcoding.todoora.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.gkcoding.todoora.data.local.TaskLocalDataSource
import com.gkcoding.todoora.data.remote.TaskRemoteDataSource
import com.gkcoding.todoora.domain.model.CategorizedTasks
import com.gkcoding.todoora.domain.model.Task
import com.gkcoding.todoora.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val localDataSource: TaskLocalDataSource,
    private val remoteDataSource: TaskRemoteDataSource
) : TaskRepository {

    override suspend fun addTask(task: Task) {
        localDataSource.addTask(task)
    }

    override suspend fun updateTask(task: Task) {
        localDataSource.updateTask(task)
    }

    override suspend fun deleteTask(taskId: String) {
        localDataSource.deleteTask(taskId)
    }

    override suspend fun getTaskById(taskId: String): Task? {
        return localDataSource.getTaskById(taskId)
    }

    override fun getAllTasksPaged(): Flow<PagingData<CategorizedTasks>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { localDataSource.getAllTasks() }).flow.map { pagingData ->
            pagingData.map { entity ->
                CategorizedTasks(entity.category.toDomainModel(), entity.taskEntity.map {
                    it.toDomainModel()
                })

            }
        }
    }

    override fun getCompletedTasksPaged(): Flow<PagingData<CategorizedTasks>> {
        return Pager(config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { localDataSource.getCompletedTasks() }).flow.map { pagingData ->
            pagingData.map { entity ->
                CategorizedTasks(entity.category.toDomainModel(), entity.taskEntity.map {
                    it.toDomainModel()
                })
            }
        }
    }


    override fun getPendingTasksPaged(): Flow<PagingData<CategorizedTasks>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { localDataSource.getPendingTasks() }
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                CategorizedTasks(entity.category.toDomainModel(), entity.taskEntity.map {
                    it.toDomainModel()
                })
            }
        }
    }


    override suspend fun syncTasks() {
        remoteDataSource.syncTasks()
    }

    override suspend fun uploadTasks() {
        remoteDataSource.uploadTasks()
    }
}
