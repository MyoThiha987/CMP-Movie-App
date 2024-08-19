package com.myothiha.compose.movie.data.cache.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath

/**
 * @Author Liam
 * Created at 14/Aug/2024
 */
internal const val dataStoreFileName = "language.preferences_pb"

fun createDatastore(
    producePath: () -> String,
): DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(
    corruptionHandler = null,
    migrations = emptyList(),
    produceFile = { producePath().toPath() }
)