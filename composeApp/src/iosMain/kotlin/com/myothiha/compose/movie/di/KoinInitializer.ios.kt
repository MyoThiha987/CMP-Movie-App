package com.myothiha.compose.movie.di

import org.koin.core.context.startKoin

actual class KoinInitializer {
    actual fun init() {
        startKoin {
            modules(appModule, dataStoreModule, datasourceModule, repositoryModule, viewModelModule)
        }
    }
}