package com.myothiha.compose.movie.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * @Author Liam
 * Created at 14/Aug/2024
 */
interface LanguageSettingRepository {
    suspend fun saveLanguage(language: String)
    fun readLanguage(): Flow<String>
}