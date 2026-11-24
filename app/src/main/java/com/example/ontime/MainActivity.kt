package com.example.ontime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.ontime.data.model.ThemeMode
import com.example.ontime.data.repository.SettingsRepository
import com.example.ontime.ui.navigation.OnTimeApp
import com.example.ontime.ui.theme.OnTimeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeMode by SettingsRepository.themeMode
                .collectAsState(initial = ThemeMode.SYSTEM)

            val darkTheme = when (themeMode) {
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
            }

            OnTimeTheme(darkTheme = darkTheme) {
                OnTimeApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnTimeAppPreview() {
    OnTimeTheme {
        OnTimeApp()
    }
}