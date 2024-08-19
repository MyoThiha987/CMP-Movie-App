package com.myothiha.compose.movie.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.myothiha.compose.movie.Language
import com.myothiha.compose.movie.domain.repository.LanguageSettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @Author Liam
 * Created at 14/Aug/2024
 */
val LANGUAGE = stringPreferencesKey("language")

class LanguageSettingRepositoryImpl(private val dataStore: DataStore<Preferences>) :
    LanguageSettingRepository {
    override suspend fun saveLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE] = language
        }
    }

    override fun readLanguage(): Flow<String> {
        return dataStore.data
            .map { preferences ->
                preferences[LANGUAGE] ?: Language.English.language
            }
    }
}