package com.myothiha.compose.movie

import androidx.compose.ui.window.ComposeUIViewController
import com.myothiha.compose.movie.di.KoinInitializer
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

fun MainViewController() = ComposeUIViewController(configure = {
    KoinInitializer().init()
    Napier.base(DebugAntilog())
}) {
    App()
}