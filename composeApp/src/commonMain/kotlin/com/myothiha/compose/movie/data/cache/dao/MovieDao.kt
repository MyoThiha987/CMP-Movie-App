package com.myothiha.compose.movie.data.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myothiha.compose.movie.data.cache.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

/**
 * @Author Liam
 * Created at 12/Aug/2024
 */

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saverMovies(data: List<MovieEntity>)

    @Query("SELECT * from movies")
    fun retrieveCacheMovies(): Flow<List<MovieEntity>>

    @Query("UPDATE movies SET isLiked=:isLiked WHERE movieType=:movieType and id=:movieId")
    suspend fun updateSaveMovie(movieId: Int, isLiked: Boolean, movieType: Int)

    @Query("DELETE from movies")
    suspend fun deleteCacheMovies()

    @Query("SELECT * from movies WHERE isLiked=:isLiked")
    fun retrieveBookmarkCacheMovies(isLiked: Boolean = true): Flow<List<MovieEntity>>

    @Query("SELECT id from movies WHERE isLiked=:isLiked AND movieType=:movieType ")
    fun retrieveBookmarkedMovies(isLiked: Boolean = true,movieType: Int): List<Int>

}