@file:OptIn(ExperimentalMaterial3Api::class)

package com.gkcoding.todoora.presentation.screens.tasklist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.gkcoding.todoora.R
import com.gkcoding.todoora.domain.model.CategorizedTasks
import com.gkcoding.todoora.domain.model.Task
import com.gkcoding.todoora.presentation.components.TaskCard
import kotlinx.coroutines.flow.Flow

@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel = hiltViewModel(),
    onTaskClick: (Task) -> Unit,
    onAddTaskClick: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(text = stringResource(R.string.app_name))
        }, actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = stringResource(R.string.btn_action_settings)
                )
            }
        })
    }, floatingActionButtonPosition = FabPosition.End, floatingActionButton = {
        FloatingActionButton(onClick = onAddTaskClick) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = stringResource(R.string.add_task)
            )
        }
    }) { innerPadding ->
        when {
            state.isLoading -> {
                LoadingState()
            }

            state.errorMessage != null -> {
                ErrorState(state)
            }

            else -> {
                LazyColumnTasks(state.tasks, innerPadding, onTaskClick, viewModel)
            }
        }
    }
}

@Composable
private fun ErrorState(state: TaskListState) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = state.errorMessage!!)
    }
}

@Composable
private fun LoadingState() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun LazyColumnTasks(
    tasks: Flow<PagingData<CategorizedTasks>>,
    innerPadding: PaddingValues,
    onTaskClick: (Task) -> Unit,
    viewModel: TaskListViewModel
) {
    val lazyPagingItems = tasks.collectAsLazyPagingItems()
    LazyColumn(
        contentPadding = innerPadding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        lazyPagingItems.itemSnapshotList.items.forEach {
            stickyHeader { Text(text = it.category.name) }
            items(count = it.tasks.size) { index ->
                val item = it.tasks[index]
                TaskCard(
                    item, onTaskClick
                ) { it1 -> viewModel.onAction(TaskListAction.CompleteTask(it1)) }
            }
        }


        lazyPagerListStatusDisplay(lazyPagingItems)
    }
}

private fun LazyListScope.lazyPagerListStatusDisplay(lazyPagingItems: LazyPagingItems<CategorizedTasks>) {
    lazyPagingItems.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                item { LoadingView() }
            }

            loadState.append is LoadState.Loading -> {
                item { LoadingView() }
            }

            loadState.append is LoadState.Error -> {
                item { ErrorView() }
            }
        }
    }
}


@Composable
fun LoadingView() {
    CircularProgressIndicator()
}

@Composable
fun ErrorView() {
    Text(text = stringResource(R.string.txt_list_error))
}


