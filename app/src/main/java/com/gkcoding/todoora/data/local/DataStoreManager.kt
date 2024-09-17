package com.gkcoding.todoora.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("settings")

class DataStoreManager(private val context: Context) {

    companion object {
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        val SYSTEM_MODE_KEY = booleanPreferencesKey("system_mode")
    }

    val darkModeFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[DARK_MODE_KEY] ?: false
        }

    // Get the current system mode setting
    val systemModeFlow: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[SYSTEM_MODE_KEY] ?: false
        }

    // Save dark mode setting
    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[DARK_MODE_KEY] = enabled
        }
    }

    // Save system mode setting
    suspend fun setSystemMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SYSTEM_MODE_KEY] = enabled
        }
    }
}
