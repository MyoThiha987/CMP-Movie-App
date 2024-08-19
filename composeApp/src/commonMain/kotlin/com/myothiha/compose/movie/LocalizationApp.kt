package com.myothiha.compose.movie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * @Author Liam
 * Created at 14/Aug/2024
 */
val LocalLocalization = staticCompositionLocalOf { Language.English.language }

@Composable
fun LocalizationApp(language: String , content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalLocalization provides language,
        content = content
    )
}