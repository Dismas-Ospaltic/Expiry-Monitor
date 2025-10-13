package com.d12.expirymonitor.di


import com.d12.expirymonitor.data.localData.AppDatabase
import com.d12.expirymonitor.repository.ItemRepository
import com.d12.expirymonitor.viewmodel.ItemViewModel
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel


val appModule = module {
//  Define ViewModel injection


single{ AppDatabase.getDatabase(get()).itemDao() }
    single { ItemRepository(get()) }
    viewModel { ItemViewModel(get()) }



}