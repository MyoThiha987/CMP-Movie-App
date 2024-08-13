package com.myothiha.compose.movie.di

import android.content.Context
import androidx.room.RoomDatabaseConstructor
import com.myothiha.compose.movie.data.cache.MovieDatabase
import com.myothiha.compose.movie.database.movieDatabase

/**
 * @Author Liam
 * Created at 12/Aug/2024
 */
/*
actual class MovieDatabaseConstructor(private val context: Context) : RoomDatabaseConstructor<MovieDatabase> {
    override fun initialize(): MovieDatabase {
        return movieDatabase(context = context)
    }
}*/
