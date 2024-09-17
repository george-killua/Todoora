package com.gkcoding.todoora.data.remote

interface TaskRemoteDataSource {
    suspend fun syncTasks()
    suspend fun uploadTasks()
}

