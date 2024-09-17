package com.gkcoding.todoora.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gkcoding.todoora.domain.model.Category
import com.gkcoding.todoora.utils.UiUtils.toColor
import java.util.UUID

@Composable
fun TaskCategoryPicker(
    categories: List<Category>,
    selectedCategory: Category?,
    onCategoryChange: (Category) -> Unit,
    onCreateCategory: (String) -> Unit // Callback to create a new category if it doesn't exist
) {
    var text by remember { mutableStateOf(selectedCategory?.name ?: "default") }
    var expanded by remember { mutableStateOf(false) }

    // Filter existing categories based on the input
    val filteredCategories = categories.filter {
        it.name.contains(text, ignoreCase = true) && text.isNotBlank()
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = text,
            onValueChange = { newValue ->
                text = newValue
                expanded = filteredCategories.isNotEmpty() // Show dropdown if there are suggestions
            },
            label = { Text("Select or create category") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        // Dropdown menu for category suggestions
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            filteredCategories.forEach { category ->
                DropdownMenuItem(onClick = {
                    text = category.name // Set the text field to the selected category
                    onCategoryChange(category) // Pass the selected category to the ViewModel or state
                    expanded = false
                }, text = {
                    Text(text = category.name)
                })
            }

            // Option to create a new category if no matching categories exist
            if (filteredCategories.isEmpty() && text.isNotBlank()) {
                DropdownMenuItem(onClick = {
                    onCreateCategory(text) // Create the new category with the input
                    expanded = false
                }, text = {
                    Text(text = "Create \"$text\" category")
                })
            }
        }
    }
}

@Preview
@Composable
fun PreviewTaskCategoryPicker() {
    TaskCategoryPicker(
        categories = listOf(
            Category(UUID.randomUUID(), "Work", "#FF5733".toColor(), 1),
            Category(UUID.randomUUID(), "Personal", "#33FF57".toColor(), 2),
            Category(UUID.randomUUID(), "Travel", "#3357FF".toColor(), 3)
        ),
        selectedCategory = null,
        onCategoryChange = {},
        onCreateCategory = {}
    )
}
