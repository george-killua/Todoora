package com.gkcoding.todoora.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gkcoding.todoora.data.local.dao.TaskDao
import com.gkcoding.todoora.data.local.entity.CategoryEntity
import com.gkcoding.todoora.data.local.entity.TaskEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [TaskEntity::class, CategoryEntity::class], // You can add other entities if needed
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    // Define abstract methods for each DAO you have in your database
    abstract fun taskDao(): TaskDao

    companion object {
        const val DATABASE_NAME = "todoora_db"
        val databaseCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Insert the default category when the database is created
                CoroutineScope(Dispatchers.IO).launch {
                    db.execSQL(
                        """
                INSERT INTO categories (category_id, name, color, sort_index) 
                VALUES ('8a4b4d88-5116-4231-ae9b-f1ea96a95cde', 'Default', '#FFFFFF', 0)
            """
                    )
                }
            }
        }
    }
}