package com.gkcoding.todoora.presentation.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.setting_title)) },
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            UIModeToggle(state, viewModel)

            Spacer(modifier = Modifier.height(24.dp))

            FireBaseControl(state, viewModel)
        }
    }
}

@Composable
private fun FireBaseControl(
    state: SettingState,
    viewModel: SettingViewModel
) {
    if (state.isAuthenticated) {
        SyncAndUploadButtons(
            isSyncing = state.isSyncing,
            onUploadClick = { viewModel.onAction(SettingAction.UploadTasks) },
            onSyncClick = { viewModel.onAction(SettingAction.SyncTasks) }
        )
    } else {
        LoginButton(onClick = { viewModel.onAction(SettingAction.LoginWithGoogle) })
    }

    SyncMessages(
        syncSuccess = state.syncSuccess,
        syncError = state.syncError
    )
}

@Composable
private fun UIModeToggle(
    state: SettingState,
    viewModel: SettingViewModel
) {
    SystemModeCheckbox(
        useSystemMode = state.useSystemMode,
        onCheckedChange = { checked ->
            viewModel.onAction(SettingAction.ToggleSystemMode(checked))
        }
    )
    DarkModeSwitch(
        useDarkMode = state.useDarkMode,
        isSystemModeEnabled = state.useSystemMode,
        onCheckedChange = { checked ->
            viewModel.onAction(SettingAction.ToggleDarkMode(checked))
        }
    )
}

@Composable
fun SystemModeCheckbox(
    useSystemMode: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(stringResource(R.string.header_use_system_mode))
        Spacer(modifier = Modifier.width(8.dp))
        Checkbox(
            checked = useSystemMode,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun DarkModeSwitch(
    useDarkMode: Boolean,
    isSystemModeEnabled: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(stringResource(R.string.header_use_dark_mode))
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = useDarkMode,
            onCheckedChange = onCheckedChange,
            enabled = !isSystemModeEnabled
        )
    }
}

@Composable
fun SyncAndUploadButtons(
    isSyncing: Boolean,
    onUploadClick: () -> Unit,
    onSyncClick: () -> Unit
) {
    Button(
        onClick = onUploadClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = !isSyncing
    ) {
        if (isSyncing) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text(stringResource(R.string.btn_upload_tasks_to_firebase))
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

    Button(
        onClick = onSyncClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = !isSyncing
    ) {
        if (isSyncing) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp
            )
        } else {
            Text(stringResource(R.string.btn_sync_tasks_from_firebase))
        }
    }
}

@Composable
fun LoginButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(stringResource(R.string.btn_login_with_google))
    }
}

@Composable
fun SyncMessages(syncSuccess: Boolean?, syncError: String?) {
    syncSuccess?.let { success ->
        if (success) {
            Text(
                text = stringResource(R.string.header_sync_completed_successfully),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }

    syncError?.let { error ->
        Text(
            text = stringResource(R.string.txt_error, error),
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}
