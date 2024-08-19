package com.myothiha.compose.movie.di

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Locale
import android.content.res.Configuration
import androidx.compose.ui.platform.LocalConfiguration
import com.myothiha.compose.movie.LocalLocalization


/**
 * @Author Liam
 * Created at 14/Aug/2024
 */
actual fun changeLang(lang: String) {
    val locale = Locale(lang)
    Locale.setDefault(locale)

}