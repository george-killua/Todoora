package com.gkcoding.todoora.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gkcoding.todoora.R
import com.gkcoding.todoora.theme.TodooraTheme

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun AddTaskButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedIconButton(
        onClick = onClick,
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add_task))
    }
}

@Preview(locale = "de", showSystemUi = true)
@Composable
fun ActionButtonPreview() {
    TodooraTheme {
        Column {
            ActionButton(text = stringResource(R.string.save_to_cloud), onClick = {})
            AddTaskButton(onClick = {})
        }
    }
}