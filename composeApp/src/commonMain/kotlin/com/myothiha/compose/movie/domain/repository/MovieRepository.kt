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
    /*suspend fun fetchNowPlayingMovies(page: Int): List<Movie>
    suspend fun fetchTopRatedMovies(page: Int): List<Movie>
    suspend fun fetchPopularMovies(page: Int): List<Movie>*/
    suspend fun syncMovies()
    fun retrieveMovies(): Flow<List<Movie>>
    suspend fun fetchUpcomingMovies(): List<Movie>
    suspend fun retrieveMovieDetail(movieId: Int): MovieFullDetail
    fun fetchPagingMovies(movieType: Int): Flow<PagingData<Movie>>

}