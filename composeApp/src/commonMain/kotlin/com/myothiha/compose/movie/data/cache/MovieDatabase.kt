package com.myothiha.compose.movie.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myothiha.compose.movie.data.cache.dao.MovieDao
import com.myothiha.compose.movie.data.cache.entity.MovieEntity

/**
 * @Author Liam
 * Created at 12/Aug/2024
 */

@Database(
    entities = [MovieEntity::class],
    version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun dao(): MovieDao
}