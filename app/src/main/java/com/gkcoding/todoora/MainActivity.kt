package com.gkcoding.todoora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.gkcoding.todoora.data.local.DataStoreManager
import com.gkcoding.todoora.theme.TodooraTheme
import com.gkcoding.todoora.utils.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var dataStoreManager: DataStoreManager // Inject DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            setContent {
                val darkTheme = dataStoreManager.darkModeFlow.collectAsState(initial = false).value
                val useSystemMode =
                    dataStoreManager.systemModeFlow.collectAsState(initial = false).value

                TodooraTheme(
                    darkTheme = if (useSystemMode) isSystemInDarkTheme() else darkTheme,
                    dynamicColor = true
                ) {
                    AppNavigation()
                }
            }
        }
    }
}
