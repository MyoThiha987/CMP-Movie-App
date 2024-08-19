package com.myothiha.compose.movie.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.myothiha.compose.movie.data.cache.datastore.createDatastore
import com.myothiha.compose.movie.data.cache.datastore.dataStoreFileName

/**
 * @Author Liam
 * Created at 14/Aug/2024
 */


actual class LanguageDataStore(private val context: Context) {
    actual fun createDataStore(): DataStore<Preferences> =
        createDatastore { context.filesDir.resolve(dataStoreFileName).absolutePath }
}
