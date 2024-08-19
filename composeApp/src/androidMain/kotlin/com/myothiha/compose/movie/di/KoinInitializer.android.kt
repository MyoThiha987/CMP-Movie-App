package com.myothiha.compose.movie.di

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

actual class KoinInitializer(private val context: Context) {
    actual fun init() {
        startKoin {
            androidContext(context)
            modules(appModule, dataStoreModule, datasourceModule, repositoryModule, viewModelModule)
        }
    }
}