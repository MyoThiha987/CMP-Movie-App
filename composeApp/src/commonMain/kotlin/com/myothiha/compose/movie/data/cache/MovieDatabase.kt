package com.myothiha.compose.movie.data.cache

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import com.myothiha.compose.movie.data.cache.dao.MovieDao
import com.myothiha.compose.movie.data.cache.entity.MovieEntity
import com.myothiha.compose.movie.di.MovieDatabaseConstructor

/**
 * @Author Liam
 * Created at 12/Aug/2024
 */

@Database(
    entities = [MovieEntity::class],
    version = 1
)
@ConstructedBy(MovieDatabaseConstructor::class)
abstract class MovieDatabase : RoomDatabase(), DB {
    abstract fun dao(): MovieDao
    override fun clearAllTables(): Unit {
        //super.clearAllTables()
    }
}

interface DB {
    fun clearAllTables(): Unit {}
}