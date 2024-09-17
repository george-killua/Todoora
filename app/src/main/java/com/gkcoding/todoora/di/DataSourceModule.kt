package com.gkcoding.todoora.di

import com.gkcoding.todoora.data.local.TaskLocalDataSource
import com.gkcoding.todoora.data.local.TaskLocalDataSourceImpl
import com.gkcoding.todoora.data.remote.TaskRemoteDataSource
import com.gkcoding.todoora.data.remote.TaskRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    @Provides
    fun provideTaskLocalDataSource(taskLocalDataSourceImpl: TaskLocalDataSourceImpl): TaskLocalDataSource {
        return taskLocalDataSourceImpl
    }

    @Provides
    fun provideTaskRemoteDataSource(taskRemoteDataSourceImpl: TaskRemoteDataSourceImpl): TaskRemoteDataSource {
        return taskRemoteDataSourceImpl
    }
}


