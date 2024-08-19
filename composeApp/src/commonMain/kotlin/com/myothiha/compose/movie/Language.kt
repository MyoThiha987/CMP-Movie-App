package com.myothiha.compose.movie

/**
 * @Author Liam
 * Created at 14/Aug/2024
 */
sealed class Language(val language: String) {
    data object English : Language(language = "en")
    data object Myanmar : Language(language = "my")
}