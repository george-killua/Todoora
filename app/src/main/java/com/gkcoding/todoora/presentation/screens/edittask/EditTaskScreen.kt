package com.gkcoding.todoora.presentation.screens.edittask

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gkcoding.todoora.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(
    viewModel: EditTaskViewModel = hiltViewModel(),
    taskId: String,
    onTaskUpdated: () -> Unit,
    onBackClick: () -> Unit
) {
    // Load the task when the screen is first opened
    LaunchedEffect(taskId) {
        viewModel.loadTask(taskId)
    }

    val state by viewModel.state.collectAsState()

    if (state.isSuccess) {
        onTaskUpdated()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.edit_task_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                R.string.btn_back
                            )
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        EditTaskContent(
            state = state,
            innerPadding = innerPadding,
            onTitleChange = { viewModel.onAction(EditTaskAction.TitleChanged(it)) },
            onDescriptionChange = { viewModel.onAction(EditTaskAction.DescriptionChanged(it)) },
            onSubmitClick = { viewModel.onAction(EditTaskAction.SubmitTask) }
        )
    }
}

@Composable
fun EditTaskContent(
    state: EditTaskState,
    innerPadding: PaddingValues,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
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
        if (state.isLoading) {
            CircularProgressIndicator()
        } else {
            EditTaskFields(
                title = state.title,
                description = state.description,
                onTitleChange = onTitleChange,
                onDescriptionChange = onDescriptionChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            SubmitButton(
                isLoading = state.isLoading,
                onSubmitClick = onSubmitClick
            )

            state.errorMessage?.let { error ->
                ErrorMessage(error = error)
            }
        }
    }
}

@Composable
fun EditTaskFields(
    title: String,
    description: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit
) {
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
                    stringResource(R.string.txt_field_task_title),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
            }
            innerTextField()
        }
    )

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
                    stringResource(R.string.txt_field_task_description),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                )
            }
            innerTextField()
        }
    )
}

@Composable
fun SubmitButton(
    isLoading: Boolean,
    onSubmitClick: () -> Unit
) {
    Button(
        onClick = onSubmitClick,
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(20.dp))
        } else {
            Text(stringResource(R.string.btn_update_task))
        }
    }
}

@Composable
fun ErrorMessage(error: String) {
    Text(
        text = error,
        color = MaterialTheme.colorScheme.error,
        modifier = Modifier.padding(top = 8.dp)
    )
}
