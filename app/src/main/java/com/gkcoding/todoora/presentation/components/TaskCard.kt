package com.gkcoding.todoora.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gkcoding.todoora.domain.model.Priority
import com.gkcoding.todoora.domain.model.Task
import com.gkcoding.todoora.theme.TodooraTheme
import com.gkcoding.todoora.utils.DateUtils.formatDateTime
import java.util.UUID

@Composable
fun TaskCard(
    task: Task,
    onTaskClick: (Task) -> Unit,
    onCompleteClick: (Task) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onTaskClick(task) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
            hoveredElevation = 8.dp,
            focusedElevation = 8.dp,
            disabledElevation = 0.dp
        )
    )
    {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = task.isCompleted,
                onCheckedChange = { onCompleteClick(task) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            TaskContent(task)
        }
    }
}

@Composable
private fun TaskContent(task: Task) {
    Column {
        Text(text = task.title, style = MaterialTheme.typography.bodyLarge)
        Text(
            text = task.description ?: "",
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis
        )
        if (task.dueDate != null) {
            Text(
                text = "Due: ${task.dueDate.formatDateTime()}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTaskCard() {
    val sampleTask = Task(
        taskId = UUID.randomUUID(),
        title = "Sample com.codings.todoora.domain.model.Task",
        description = "This is a sample task description.",
        isCompleted = false,
        dueDate = System.currentTimeMillis() + 24 * 60 * 60 * 1000, // Due in 1 day
        priority = Priority.MEDIUM,
        createdAt = System.currentTimeMillis(),
        updatedAt = System.currentTimeMillis()
    )
    TodooraTheme {
        TaskCard(
            task = sampleTask,
            onTaskClick = { /* Handle task click */ },
            onCompleteClick = { /* Handle complete click */ }
        )
    }
}
