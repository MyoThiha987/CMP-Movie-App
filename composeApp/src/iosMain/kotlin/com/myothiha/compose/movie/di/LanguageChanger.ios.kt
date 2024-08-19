package com.myothiha.compose.movie.di

import androidx.compose.runtime.Composable
import platform.Foundation.NSUserDefaults

/**
 * @Author Liam
 * Created at 14/Aug/2024
 */

actual fun changeLang(lang: String) {
    NSUserDefaults.standardUserDefaults.setObject(arrayListOf(lang), "AppleLanguages")
}