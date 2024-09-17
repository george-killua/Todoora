package com.gkcoding.todoora.di

import com.gkcoding.todoora.data.local.TaskLocalDataSource
import com.gkcoding.todoora.data.remote.TaskRemoteDataSource
import com.gkcoding.todoora.data.repository.TaskRepositoryImpl
import com.gkcoding.todoora.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun bindTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository {
        return impl
    }

    companion object {
        @Provides
        fun provideTaskRepositoryImpl(
            taskLocalDataSource: TaskLocalDataSource,
            taskRemoteDataSource: TaskRemoteDataSource
        ): TaskRepositoryImpl {
            return TaskRepositoryImpl(taskLocalDataSource, taskRemoteDataSource)
        }
    }
}
