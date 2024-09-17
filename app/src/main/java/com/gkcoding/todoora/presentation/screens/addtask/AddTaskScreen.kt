package com.gkcoding.todoora.presentation.screens.addtask

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gkcoding.todoora.R
import com.gkcoding.todoora.domain.model.Priority
import com.gkcoding.todoora.utils.DateUtils.formatDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    viewModel: AddTaskViewModel = hiltViewModel(),
    onTaskAdded: () -> Unit,
    onBackClick: () -> Unit,
    datePickerDialogShow: (Long) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    if (state.isSuccess) {
        onTaskAdded()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.add_task_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.btn_back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        AddTaskContent(
            state = state,
            innerPadding = innerPadding,
            onTitleChange = { viewModel.onAction(AddTaskAction.TitleChanged(it)) },
            onDescriptionChange = { viewModel.onAction(AddTaskAction.DescriptionChanged(it)) },
            datePickerDialogShow = datePickerDialogShow,
            onPriorityChange = { viewModel.onAction(AddTaskAction.PriorityChanged(it)) },
            onSubmitClick = { viewModel.onAction(AddTaskAction.SubmitTask) }
        )
    }
}

@Composable
fun AddTaskContent(
    state: AddTaskState,
    innerPadding: PaddingValues,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    datePickerDialogShow: (Long) -> Unit,
    onPriorityChange: (Priority) -> Unit,
    onSubmitClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TaskTitleField(title = state.title, onTitleChange = onTitleChange)
        TaskDescriptionField(
            description = state.description,
            onDescriptionChange = onDescriptionChange
        )
        Button(
            onClick = { datePickerDialogShow.invoke(state.dueDate ?: System.currentTimeMillis()) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (state.dueDate != null) {
                    // Format the date for display
                    state.dueDate.formatDateTime()
                } else {
                    stringResource(R.string.btn_select_due_date)
                }
            )
        }
        TaskPriorityPicker(priority = state.priority, onPriorityChange = onPriorityChange)

        Spacer(modifier = Modifier.height(16.dp))
        AddTaskButton(isLoading = state.isLoading, onSubmitClick = onSubmitClick)
        ErrorMessage(error = state.errorMessage)
    }
}

@Composable
fun TaskTitleField(title: String, onTitleChange: (String) -> Unit) {
    BasicTextField(
        value = title,
        onValueChange = onTitleChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        textStyle = MaterialTheme.typography.bodyLarge,
        decorationBox = { innerTextField ->
            if (title.isEmpty()) {
                Text(
                    stringResource(R.string.txt_field_enter_task_title),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
            }
            innerTextField()
        }
    )
}

@Composable
fun TaskDescriptionField(description: String, onDescriptionChange: (String) -> Unit) {
    BasicTextField(
        value = description,
        onValueChange = onDescriptionChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        textStyle = MaterialTheme.typography.bodyMedium,
        decorationBox = { innerTextField ->
            if (description.isEmpty()) {
                Text(
                    stringResource(R.string.txt_field_enter_task_description),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
            }
            innerTextField()
        }
    )
}


@Composable
fun TaskPriorityPicker(
    priority: Priority,
    onPriorityChange: (Priority) -> Unit
) {
    // State to control the dropdown menu expansion
    var expanded by remember { mutableStateOf(false) }

    // List of priorities
    val priorities = Priority.entries.toTypedArray()

    // Button to display the current selected priority and expand dropdown
    Button(
        onClick = { expanded = !expanded },  // Toggle the expansion state
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(text = priority.name)
    }

    // Dropdown menu that shows priority options
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false } // Close the menu when clicked outside
    ) {
        priorities.forEach { item ->
            DropdownMenuItem(
                onClick = {
                    onPriorityChange(item)  // Trigger priority change action
                    expanded = false        // Close the menu after selection
                },
                text = {
                    Text(text = item.name)
                })
        }
    }
}

@Composable
fun AddTaskButton(isLoading: Boolean, onSubmitClick: () -> Unit) {
    Button(
        onClick = onSubmitClick,
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(20.dp))
        } else {
            Text(stringResource(R.string.btn_add_task))
        }
    }
}

@Composable
fun ErrorMessage(error: String?) {
    error?.let {
        Text(
            text = it,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
