package com.d12.expirymonitor.data.datastore


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


// Extension property for DataStore
private val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "local_notify_prefs")

class LocalNotificationPreferences(private val context: Context) {

    companion object {
        val IS_LOCAL_NOTIFY_ENABLED = booleanPreferencesKey("is_local_notify_enabled")
    }

    // Save full user data (on login)
    suspend fun saveUserData() {
        context.userDataStore.edit { preferences ->
            preferences[IS_LOCAL_NOTIFY_ENABLED] = true
        }
    }



    // Observe user data (Flow)
    val userNotificationData: Flow<UserNotificationData> = context.userDataStore.data.map { preferences ->
        UserNotificationData(
            isNotificationEnabled = preferences[IS_LOCAL_NOTIFY_ENABLED] ?: false
        )
    }

    // Clear all data
    suspend fun clearUserData() {
        context.userDataStore.edit { it.clear() }
    }
}

// Data class for structured access
data class UserNotificationData(
    val isNotificationEnabled: Boolean
)