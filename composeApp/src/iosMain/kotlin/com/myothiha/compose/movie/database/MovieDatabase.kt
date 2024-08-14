package com.myothiha.compose.movie.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.myothiha.compose.movie.data.cache.MovieDatabase
import platform.Foundation.NSHomeDirectory
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

/**
 * @Author Liam
 * Created at 12/Aug/2024
 */
fun movieDatabase(): MovieDatabase {
    val dbFilePath = documentDirectory() + "/movie.db"
    return Room.databaseBuilder<MovieDatabase>(
        name = dbFilePath
    )
        .setDriver(BundledSQLiteDriver())
        .fallbackToDestructiveMigration(true)
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}
