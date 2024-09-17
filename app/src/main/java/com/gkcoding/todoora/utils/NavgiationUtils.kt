package com.gkcoding.todoora.utils

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.gkcoding.todoora.presentation.components.DatePickerDialog
import com.gkcoding.todoora.presentation.screens.addtask.AddTaskScreen
import com.gkcoding.todoora.presentation.screens.edittask.EditTaskScreen
import com.gkcoding.todoora.presentation.screens.settings.SettingScreen
import com.gkcoding.todoora.presentation.screens.tasklist.TaskListScreen
import kotlinx.serialization.Serializable

const val SELECTED_DATE_ARGUMENT = "selectedDate"

@Serializable
data object AddTaskNavigation

@Serializable
data class DatePickerDialogNavigation(val selectedDate: Long? = null)

@Serializable
data object TasksListNavigation

@Serializable
data object SettingNavigation

@Serializable
data class EditTaskNavigation(val taskId: String)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = TasksListNavigation
    ) {
        // Task List Screen
        navTaskListScreen(navController)

        // Add Task Screen
        navAddTaskScreen(navController)

        // Edit Task Screen
        navEditScreen(navController)

        // Settings Screen
        navSettingScreen(navController)

        // Date Picker Screen
        datePickerDialogView(navController)
    }
}

private fun NavGraphBuilder.datePickerDialogView(navController: NavHostController) {
    dialog<DatePickerDialogNavigation>(
        dialogProperties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = true
        )
    ) {

        DatePickerDialog(onDismissRequest = { navController.navigateUp() }, onConfirm = {
            navController.previousBackStackEntry?.savedStateHandle?.set(SELECTED_DATE_ARGUMENT, it)
            Log.d("Navigation", "Selected Date: $it")
            navController.popBackStack()
        })
    }
}


private fun NavGraphBuilder.navAddTaskScreen(navController: NavHostController) {
    composable<AddTaskNavigation> {
        AddTaskScreen(
            onTaskAdded = {
                navController.navigateUp() // Navigate back to TaskListScreen after adding a task
            },
            onBackClick = {
                navController.navigateUp() // Navigate back to TaskListScreen when back button is clicked
            },
            datePickerDialogShow = { selectedDate ->
                navController.navigate(DatePickerDialogNavigation(selectedDate))
            }
        )
    }
}

private fun NavGraphBuilder.navEditScreen(navController: NavHostController) {
    composable<EditTaskNavigation> { backStackEntry ->
        val editTaskNavigation = backStackEntry.toRoute<EditTaskNavigation>()
        EditTaskScreen(
            taskId = editTaskNavigation.taskId,
            onTaskUpdated = {
                navController.navigateUp() // Navigate back to TaskListScreen after editing a task
            },
            onBackClick = {
                navController.navigateUp() // Navigate back to TaskListScreen when back button is clicked
            }
        )
    }
}

private fun NavGraphBuilder.navSettingScreen(navController: NavHostController) {
    composable<SettingNavigation> {
        SettingScreen(
            onBackClick = {
                navController.navigateUp() // Navigate back to TaskListScreen when back button is clicked
            })
    }
}


private fun NavGraphBuilder.navTaskListScreen(navController: NavHostController) {
    composable<TasksListNavigation> {
        TaskListScreen(
            onTaskClick = { taskId ->
                navController.navigate(EditTaskNavigation(taskId.taskId.toString()))
            },
            onAddTaskClick = {
                navController.navigate(AddTaskNavigation)
            },
            onSettingsClick = {
                navController.navigate(SettingNavigation)
            }
        )
    }
}