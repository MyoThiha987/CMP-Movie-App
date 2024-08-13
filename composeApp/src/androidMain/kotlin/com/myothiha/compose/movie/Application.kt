package com.myothiha.compose.movie

import android.app.Application
import com.myothiha.compose.movie.di.KoinInitializer
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

/**
 * @Author Liam
 * Created at 10/Aug/2024
 */
class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())
        KoinInitializer(applicationContext).init()
        //MovieDatabaseConstructor(applicationContext).initialize()
        //DatabaseInitializer(applicationContext).init()
    }
}