package com.myothiha.compose.movie.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences


/**
 * @Author Liam
 * Created at 14/Aug/2024
 */


expect class LanguageDataStore {
    fun createDataStore(): DataStore<Preferences>
}