package com.d12.expirymonitor.di


import com.d12.expirymonitor.data.datastore.LocalNotificationPreferences
import com.d12.expirymonitor.data.localData.AppDatabase
import com.d12.expirymonitor.repository.ItemRepository
import com.d12.expirymonitor.viewmodel.ItemViewModel
import com.d12.expirymonitor.viewmodel.LocalNotificationPrefsViewModel
import com.d12.expirymonitor.viewmodel.NotificationViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


val appModule = module {
//  Define ViewModel injection

    viewModel { NotificationViewModel() }



    single { LocalNotificationPreferences(get()) }
    viewModel { LocalNotificationPrefsViewModel(get()) }

single{ AppDatabase.getDatabase(get()).itemDao() }
    single { ItemRepository(get()) }
    viewModel { ItemViewModel(get()) }



}