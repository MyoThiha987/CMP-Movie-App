package com.myothiha.compose.movie.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.myothiha.compose.movie.data.cache.MovieDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

/**
 * @Author Liam
 * Created at 12/Aug/2024
 */

fun movieDatabase(context: Context): MovieDatabase {
    val dbFilePath = context.getDatabasePath("movie.db")
    return Room.databaseBuilder<MovieDatabase>(context = context, name = dbFilePath.absolutePath)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}