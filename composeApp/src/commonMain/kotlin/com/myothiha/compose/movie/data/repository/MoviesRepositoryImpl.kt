package com.myothiha.compose.movie.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.myothiha.compose.movie.data.Constants.NOW_PLAYING
import com.myothiha.compose.movie.data.Constants.POPULAR
import com.myothiha.compose.movie.data.Constants.TOP_RATED
import com.myothiha.compose.movie.data.Constants.UPCOMING
import com.myothiha.compose.movie.data.datasources.MovieCacheDataSource
import com.myothiha.compose.movie.data.datasources.MovieNetworkDataSource
import com.myothiha.compose.movie.data.network.response.toDomain
import com.myothiha.compose.movie.data.network.response.toEntity
import com.myothiha.compose.movie.domain.models.Movie
import com.myothiha.compose.movie.domain.models.MovieFullDetail
import com.myothiha.compose.movie.domain.repository.MovieRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @Author Liam
 * Created at 09/Aug/2024
 */
class MoviesRepositoryImpl(
    private val dataSource: MovieNetworkDataSource,
    private val cacheDataSource: MovieCacheDataSource
) : MovieRepository {
    override suspend fun syncMovies() {
        coroutineScope {
            val upComing = async {
                dataSource.fetchUpcomingMovies().map { it.toEntity(movieCategory = UPCOMING) }
            }.await()

            val popular = async {
                dataSource.fetchPopularMovies(page = 1).map { it.toEntity(movieCategory = POPULAR) }
            }.await()

            val topRated = async {
                dataSource.fetchTopRatedMovies(page = 1)
                    .map { it.toEntity(movieCategory = TOP_RATED) }
            }.await()

            val nowPlaying = async {
                dataSource.fetchNowPlayingMovies(page = 1)
                    .map { it.toEntity(movieCategory = NOW_PLAYING) }
            }.await()

            val data = upComing + popular + topRated + nowPlaying
            cacheDataSource.saveMovies(data)
        }
    }

    override fun retrieveMovies(): Flow<List<Movie>> {
        return cacheDataSource.retrieveCacheMovies()
    }

    override suspend fun fetchUpcomingMovies(): List<Movie> {
        return coroutineScope {
            val upComing = async {
                dataSource.fetchUpcomingMovies().map { it.toDomain(movieType = UPCOMING) }
            }.await()

            val popular = async {
                dataSource.fetchPopularMovies(page = 1).map { it.toDomain(movieType = POPULAR) }
            }.await()

            val topRated = async {
                dataSource.fetchTopRatedMovies(page = 1).map { it.toDomain(movieType = TOP_RATED) }
            }.await()

            val nowPlaying = async {
                dataSource.fetchNowPlayingMovies(page = 1)
                    .map { it.toDomain(movieType = NOW_PLAYING) }
            }.await()

            upComing + popular + topRated + nowPlaying
        }
    }

    override suspend fun retrieveMovieDetail(movieId: Int): MovieFullDetail {
        return coroutineScope {
            val detail = async { dataSource.fetchMovieDetail(movieId = movieId).toDomain() }.await()
            val credit = async { dataSource.fetchCredits(movieId = movieId).toDomain() }.await()
            MovieFullDetail(
                movieDetail = detail,
                credit = credit
            )
        }
    }

    override fun fetchPagingMovies(movieType: Int): Flow<PagingData<Movie>> {
        return dataSource.fetchPagingMovies(movieType = movieType)
            .map { pagingData ->
                pagingData.map { it.toDomain(movieType = movieType) }
            }
    }

    override fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return dataSource.searchMovies(query = query)
            .map { pagingData ->
                pagingData.map { it.toDomain() }
            }
    }

    override suspend fun updateSavedMovie(movieId: Int, movieType: Int) {
        cacheDataSource.updateSavedMovie(movieId = movieId, movieType = movieType)
    }
}