package com.myothiha.compose.movie.data.cache.datasource

import androidx.room.useWriterConnection
import com.myothiha.compose.movie.data.cache.MovieDatabase
import com.myothiha.compose.movie.data.cache.entity.MovieEntity
import com.myothiha.compose.movie.data.datasources.MovieCacheDataSource
import com.myothiha.compose.movie.data.network.response.toDomain
import com.myothiha.compose.movie.domain.models.Movie
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @Author Liam
 * Created at 12/Aug/2024
 */
class MoviesCacheDataSourceImpl(
    private val database: MovieDatabase
) : MovieCacheDataSource {
    override suspend fun saveMovies(data: List<MovieEntity>) {

        /*val likedMovies = database
            .dao()
            .retrieveBookmarkedMovies(isLiked = true, movieType = movieType)*/

        /*data.map {
            if (likedMovies.contains(it.id)) it.isLiked = true
        }*/

        database.useWriterConnection {
            database.dao().deleteCacheMovies()
            database.dao().saverMovies(data = data)
        }

        Napier.d("${database.dao().retrieveCacheMovies()}", tag = "db")
    }

    override fun retrieveCacheMovies(): Flow<List<Movie>> {
        return database.dao().retrieveCacheMovies().map { movieEntityList ->
            movieEntityList.map {
                it.toDomain()
            }
        }
    }

    override suspend fun updateSaveMovie(movieId: Int, isLiked: Boolean, movieType: Int) {
        database.dao()
            .updateSaveMovie(movieId = movieId, isLiked = isLiked, movieType = movieType)
    }

    override suspend fun retrieveBookmarkCacheMovies(): Flow<List<Movie>> {
        return database.dao().retrieveBookmarkCacheMovies().map { movieEntityList ->
            movieEntityList.map {
                it.toDomain()
            }
        }
    }
}