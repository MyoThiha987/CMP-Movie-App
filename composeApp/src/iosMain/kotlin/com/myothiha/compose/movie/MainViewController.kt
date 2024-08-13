package com.myothiha.compose.movie

import androidx.compose.ui.window.ComposeUIViewController
import com.myothiha.compose.movie.di.KoinInitializer
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun MainViewController() = ComposeUIViewController(configure = {
    KoinInitializer().init()
    //MovieDatabaseConstructor().initialize()
    Napier.base(DebugAntilog())

    //DatabaseInitializer().init()
}) {
    App()
}