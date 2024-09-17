package com.gkcoding.todoora.presentation.components

import android.util.Log
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.gkcoding.todoora.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(onDismissRequest: () -> Unit, onConfirm: (Long) -> Unit) {
    val datePickerState = rememberDatePickerState()

    // Confirm button is only enabled if a valid date is selected
    val confirmEnabled by remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }

    androidx.compose.material3.DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = {
                    // Ensure a valid date is selected before invoking the onConfirm callback
                    datePickerState.selectedDateMillis?.let { selectedDate ->
                        Log.d("DatePickerDialog", "Selected Date: $selectedDate")
                        onConfirm.invoke(selectedDate)
                    }
                },
                enabled = confirmEnabled // Only enable if a date is selected
            ) {
                Text(stringResource(R.string.accept))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text(stringResource(R.string.cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
