package com.gkcoding.todoora.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import androidx.room.Transaction
import com.gkcoding.todoora.data.local.entity.TaskEntity
import com.gkcoding.todoora.data.local.entity.TaskWithCategory
import com.gkcoding.todoora.domain.model.Priority

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Query(
        """
        UPDATE tasks SET 
        title = :title,
        description = :description,
        is_completed = :isCompleted,
        priority = :priority,
        due_date = :dueDate,
        updated_at = :updatedAt,
        category_id = :categoryId
        WHERE task_id = :taskId
    """
    )
    suspend fun updateTask(
        taskId: String,
        title: String,
        description: String?,
        categoryId: String,
        isCompleted: Boolean,
        priority: Int = Priority.LOW.ordinal,
        dueDate: Long?,
        updatedAt: Long = System.currentTimeMillis()
    )

    @Query("DELETE FROM tasks WHERE task_id = :taskId")
    suspend fun deleteTaskById(taskId: String)

    @Query("SELECT * FROM tasks WHERE task_id = :taskId LIMIT 1")
    suspend fun getTaskById(taskId: String): TaskEntity?

    @Query("SELECT * FROM tasks ORDER BY due_date ASC")
    fun getAllTasks(): List<TaskEntity>

    // Updated methods to use PagingSource
    @RewriteQueriesToDropUnusedColumns
    @Transaction
    @Query(
        """
        SELECT * FROM categories
            INNER JOIN tasks ON tasks.category_id = categories.category_id
        ORDER BY tasks.due_date ASC, tasks.updated_at DESC, tasks.priority ASC
    """
    )
    fun getTasksWithCategoryPaged(): PagingSource<Int, TaskWithCategory>

    @RewriteQueriesToDropUnusedColumns
    @Transaction
    @Query(
        """
    SELECT * FROM categories
    INNER JOIN tasks ON tasks.category_id = categories.category_id
    WHERE tasks.is_completed = 1
    """
    )
    fun getCompletedTasksByCategory(): PagingSource<Int, TaskWithCategory>

    @RewriteQueriesToDropUnusedColumns
    @Transaction
    @Query(
        """
    SELECT * FROM categories
    INNER JOIN tasks ON tasks.category_id = categories.category_id
    WHERE tasks.is_completed = 0
    """
    )
    fun getUncompletedTasksByCategory(): PagingSource<Int, TaskWithCategory>

}