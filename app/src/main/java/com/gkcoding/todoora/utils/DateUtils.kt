package com.gkcoding.todoora.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data object DateUtils {
    fun Long.formatDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date(this))
    }

    fun Long.formatTime(): String {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return timeFormat.format(Date(this))
    }

    fun Long.formatDateTime(): String {
        val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        return dateTimeFormat.format(Date(this))
    }

    fun isTaskOverdue(dueDate: Long): Boolean {
        return dueDate < System.currentTimeMillis()
    }
}
