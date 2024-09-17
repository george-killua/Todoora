package com.gkcoding.todoora.presentation.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gkcoding.todoora.data.local.DataStoreManager
import com.gkcoding.todoora.domain.usecases.SyncTasksUseCase
import com.gkcoding.todoora.domain.usecases.UploadTasksUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val syncTasksUseCase: SyncTasksUseCase,
    private val uploadTasksUseCase: UploadTasksUseCase,
    private val auth: FirebaseAuth
) : ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state: StateFlow<SettingState> = _state

    init {
        loadSettings()
    }

    fun onAction(action: SettingAction) {
        when (action) {
            is SettingAction.ToggleDarkMode -> toggleDarkMode(action.enabled)
            is SettingAction.ToggleSystemMode -> toggleSystemMode(action.enabled)
            is SettingAction.LoginWithGoogle -> loginWithGoogle()
            is SettingAction.SyncTasks -> syncTasks()
            is SettingAction.UploadTasks -> uploadTasks()
        }
    }

    private fun loadSettings() {
        // Collect DataStore values and update state
        viewModelScope.launch {
            dataStoreManager.darkModeFlow.collectLatest { darkModeEnabled ->
                _state.value = _state.value.copy(useDarkMode = darkModeEnabled)
            }
        }

        viewModelScope.launch {
            dataStoreManager.systemModeFlow.collectLatest { systemModeEnabled ->
                _state.value = _state.value.copy(useSystemMode = systemModeEnabled)
            }
        }
    }


    private fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            dataStoreManager.setDarkMode(enabled)
            _state.value = _state.value.copy(useDarkMode = enabled)
        }
    }

    private fun toggleSystemMode(enabled: Boolean) {
        viewModelScope.launch {
            dataStoreManager.setSystemMode(enabled)
            _state.value = _state.value.copy(useSystemMode = enabled)
            if (enabled) {
                _state.value = _state.value.copy(useDarkMode = false)
                dataStoreManager.setDarkMode(false)
            }
        }
    }

    private fun loginWithGoogle() {
        viewModelScope.launch {
            // Firebase Authentication logic here
            val currentUser = auth.currentUser
            if (currentUser == null) {
                // Example of login (in a real app, you would use the Google sign-in flow)
                // You need to handle the Google sign-in intent in your Activity and pass the result
                // For simplicity, this example assumes you have a Google ID token
                val googleIdToken = "your-google-id-token" // Replace with actual token
                val credential = GoogleAuthProvider.getCredential(googleIdToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _state.value = _state.value.copy(isAuthenticated = true)
                    }
                }
            } else {
                _state.value = _state.value.copy(isAuthenticated = true)
            }
        }
    }

    private fun syncTasks() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isSyncing = true, syncError = null)
            try {
                syncTasksUseCase()
                _state.value = _state.value.copy(isSyncing = false, syncSuccess = true)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isSyncing = false, syncError = e.message)
            }
        }
    }

    private fun uploadTasks() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isSyncing = true, syncError = null)
            try {
                uploadTasksUseCase()
                _state.value = _state.value.copy(isSyncing = false, syncSuccess = true)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isSyncing = false, syncError = e.message)
            }
        }
    }
}
