package com.myothiha.compose.movie.data.datasources

import com.myothiha.compose.movie.data.cache.entity.MovieEntity
import com.myothiha.compose.movie.domain.models.Movie
import kotlinx.coroutines.flow.Flow

/**
 * @Author Liam
 * Created at 12/Aug/2024
 */
interface MovieCacheDataSource {
    suspend fun saveMovies(data: List<MovieEntity>)
    fun retrieveCacheMovies(): Flow<List<Movie>>
    /*suspend fun updateSaveMovie(movieId: Int, isLiked: Boolean, movieType: Int)
    suspend fun retrieveBookmarkCacheMovies(): Flow<List<Movie>>*/
}