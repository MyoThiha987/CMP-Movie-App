package com.myothiha.compose.movie.domain.repository

import androidx.paging.PagingData
import com.myothiha.compose.movie.domain.models.Movie
import com.myothiha.compose.movie.domain.models.MovieFullDetail
import kotlinx.coroutines.flow.Flow

/**
 * @Author Liam
 * Created at 09/Aug/2024
 */
interface MovieRepository {
    suspend fun syncMovies()
    fun retrieveMovies(): Flow<List<Movie>>
    suspend fun fetchUpcomingMovies(): List<Movie>
    suspend fun retrieveMovieDetail(movieId: Int): MovieFullDetail
    fun fetchPagingMovies(movieType: Int): Flow<PagingData<Movie>>
    fun searchMovies(query: String): Flow<PagingData<Movie>>
    suspend fun updateSavedMovie(movieId: Int, movieType: Int)

}