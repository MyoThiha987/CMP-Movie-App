package com.myothiha.compose.movie.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.myothiha.compose.movie.data.cache.datastore.createDatastore
import com.myothiha.compose.movie.data.cache.datastore.dataStoreFileName
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

/**
 * @Author Liam
 * Created at 14/Aug/2024
 */


@OptIn(ExperimentalForeignApi::class)
actual class LanguageDataStore {
    actual fun createDataStore(): DataStore<Preferences> = createDatastore {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        requireNotNull(documentDirectory).path + "/$dataStoreFileName"
    }
}
