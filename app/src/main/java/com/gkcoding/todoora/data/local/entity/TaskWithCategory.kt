package com.gkcoding.todoora.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

class TaskWithCategory(
    @Embedded
    val category: CategoryEntity,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "category_id"
    )
    val taskEntity: List<TaskEntity>
)