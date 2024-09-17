package com.gkcoding.todoora.presentation.screens.settings

sealed class SettingAction {
    data class ToggleDarkMode(val enabled: Boolean) : SettingAction()
    data class ToggleSystemMode(val enabled: Boolean) : SettingAction()
    data object LoginWithGoogle : SettingAction()
    data object SyncTasks : SettingAction()
    object UploadTasks : SettingAction()
}

