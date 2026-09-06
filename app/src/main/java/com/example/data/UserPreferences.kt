package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences(private val context: Context) {
    companion object {
        val SESSION_COUNT = intPreferencesKey("session_count")
        val DIALOG_SHOWN = androidx.datastore.preferences.core.booleanPreferencesKey("dialog_shown")
    }

    val sessionCount = context.dataStore.data.map { preferences ->
        preferences[SESSION_COUNT] ?: 0
    }

    val dialogShown = context.dataStore.data.map { preferences ->
        preferences[DIALOG_SHOWN] ?: false
    }

    suspend fun incrementSessionCount() {
        context.dataStore.edit { preferences ->
            val current = preferences[SESSION_COUNT] ?: 0
            preferences[SESSION_COUNT] = current + 1
        }
    }

    suspend fun setDialogShown() {
        context.dataStore.edit { preferences ->
            preferences[DIALOG_SHOWN] = true
        }
    }
}
