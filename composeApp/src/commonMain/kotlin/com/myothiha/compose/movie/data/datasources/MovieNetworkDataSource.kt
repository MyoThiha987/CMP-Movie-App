package com.myothiha.compose.movie.data.datasources

import androidx.paging.PagingData
import com.myothiha.compose.movie.data.network.response.CreditDto
import com.myothiha.compose.movie.data.network.response.MovieDetailDto
import com.myothiha.compose.movie.data.network.response.MovieDto
import kotlinx.coroutines.flow.Flow

/**
 * @Author Liam
 * Created at 09/Aug/2024
 */
interface MovieNetworkDataSource {
    suspend fun fetchNowPlayingMovies(page: Int): List<MovieDto>
    suspend fun fetchTopRatedMovies(page: Int): List<MovieDto>
    suspend fun fetchPopularMovies(page: Int): List<MovieDto>
    suspend fun fetchUpcomingMovies(): List<MovieDto>
    suspend fun fetchMovieDetail(movieId: Int): MovieDetailDto
    suspend fun fetchCredits(movieId: Int): CreditDto
    fun fetchPagingMovies(movieType: Int): Flow<PagingData<MovieDto>>

}