package com.example.ontime.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.ontime.data.model.ThemeMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

object SettingsRepository {

    private val THEME_MODE = stringPreferencesKey("theme_mode")
    private lateinit var dataStore: DataStore<Preferences>

    fun init(context: Context) {
        dataStore = context.applicationContext.settingsDataStore
    }

    val themeMode: Flow<ThemeMode>
        get() = dataStore.data.map { preferences ->
            val saved = preferences[THEME_MODE]
            ThemeMode.entries.find { it.name == saved } ?: ThemeMode.SYSTEM
        }

    suspend fun setThemeMode(mode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE] = mode.name
        }
    }
}