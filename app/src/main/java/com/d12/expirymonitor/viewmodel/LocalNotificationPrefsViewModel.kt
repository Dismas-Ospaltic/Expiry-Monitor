package com.d12.expirymonitor.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.d12.expirymonitor.data.datastore.LocalNotificationPreferences
import com.d12.expirymonitor.data.datastore.UserNotificationData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LocalNotificationPrefsViewModel(
    private val localNotificationPreferences: LocalNotificationPreferences
) : ViewModel() {

    // Expose user data as StateFlow (recommended for UI)
    val userNotificationData: StateFlow<UserNotificationData> = localNotificationPreferences.userNotificationData
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserNotificationData(false))

    // Save user data
    fun saveUserData() {
        viewModelScope.launch {
            localNotificationPreferences.saveUserData()
        }
    }

    // Clear user data (logout)
    fun clearUserData() {
        viewModelScope.launch {
            localNotificationPreferences.clearUserData()
        }
    }
}