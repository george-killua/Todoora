package com.gkcoding.todoora.di

import com.gkcoding.todoora.domain.repository.TaskRepository
import com.gkcoding.todoora.domain.usecases.SyncTasksUseCase
import com.gkcoding.todoora.domain.usecases.UploadTasksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideSyncTasksUseCase(repository: TaskRepository): SyncTasksUseCase {
        return SyncTasksUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUploadTasksUseCase(repository: TaskRepository): UploadTasksUseCase {
        return UploadTasksUseCase(repository)
    }
}

