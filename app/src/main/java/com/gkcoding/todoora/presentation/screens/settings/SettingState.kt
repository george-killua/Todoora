package com.gkcoding.todoora.presentation.screens.settings

data class SettingState(
    val useDarkMode: Boolean = false,
    val useSystemMode: Boolean = false,
    val isAuthenticated: Boolean = false,
    val isSyncing: Boolean = false,
    val syncSuccess: Boolean = false,
    val syncError: String? = null
)
