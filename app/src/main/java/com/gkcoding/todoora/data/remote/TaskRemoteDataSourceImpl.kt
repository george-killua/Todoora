package com.gkcoding.todoora.data.remote

import com.gkcoding.todoora.data.local.dao.TaskDao
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRemoteDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val taskDao: TaskDao
) : TaskRemoteDataSource {
    override suspend fun syncTasks() {
        val userID = FirebaseAuth.getInstance().currentUser?.uid ?: return

        // Get a reference to the user's tasks collection
        val tasksCollectionRef =
            firestore.collection(COLLECTION_USER).document(userID).collection(COLLECTION_NAME)

        // Clear existing tasks
        tasksCollectionRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                document.reference.delete()
            }
        }.await()

        // Add local tasks to Firestore
        taskDao.getAllTasks().forEach { task ->
            tasksCollectionRef.add(task.toRemoteModel())
        }
    }

    override suspend fun uploadTasks() {
        val userID = FirebaseAuth.getInstance().currentUser?.uid ?: return

        // Get a reference to the user's tasks collection
        val tasksCollectionRef =
            firestore.collection(COLLECTION_USER).document(userID).collection(COLLECTION_NAME)

        // Add tasks from local database to FireStore
        taskDao.getAllTasks().forEach { task ->
            tasksCollectionRef.add(task.toRemoteModel())
        }
    }

    companion object {
        private const val COLLECTION_NAME = "tasks"
        private const val COLLECTION_USER = "users"
    }
}
