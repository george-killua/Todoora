package  com.gkcoding.todoora.domain.repository

import androidx.paging.PagingData
import com.gkcoding.todoora.domain.model.CategorizedTasks
import com.gkcoding.todoora.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(taskId: String)
    suspend fun getTaskById(taskId: String): Task?
    fun getAllTasksPaged(): Flow<PagingData<CategorizedTasks>>
    fun getCompletedTasksPaged(): Flow<PagingData<CategorizedTasks>>
    fun getPendingTasksPaged(): Flow<PagingData<CategorizedTasks>>
    suspend fun syncTasks()
    suspend fun uploadTasks()
}
