package com.myothiha.compose.movie.data.network.datasources

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.myothiha.compose.movie.data.Constants
import com.myothiha.compose.movie.data.Constants.GET_POPULAR
import com.myothiha.compose.movie.data.network.response.CreditDto
import com.myothiha.compose.movie.data.network.response.MovieDetailDto
import com.myothiha.compose.movie.data.network.response.MovieDto
import com.myothiha.compose.movie.data.datasources.MovieNetworkDataSource
import com.myothiha.compose.movie.data.network.mediator.MoviesPagingSource
import com.myothiha.compose.movie.data.network.client.pathUrl
import com.myothiha.data.network.response.DataResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow

/**
 * @Author Liam
 * Created at 09/Aug/2024
 */
class MovieNetworkDataSourceImpl(private val client: HttpClient) : MovieNetworkDataSource {
    override suspend fun fetchNowPlayingMovies(page: Int): List<MovieDto> {
        val raw = client.get {
            pathUrl(Constants.GET_NOW_PLAYING, page = 1)
        }.body<DataResponse<MovieDto>>()
        return raw.data.orEmpty()
    }

    override suspend fun fetchTopRatedMovies(page: Int): List<MovieDto> {
        val raw = client.get {
            pathUrl(Constants.GET_TOP_RATED, page = 1)
        }.body<DataResponse<MovieDto>>()
        return raw.data.orEmpty()
    }

    override suspend fun fetchPopularMovies(page: Int): List<MovieDto> {
        val raw = client.get {
            pathUrl(Constants.GET_POPULAR, page = 1)
        }.body<DataResponse<MovieDto>>()
        return raw.data.orEmpty()
    }

    override suspend fun fetchUpcomingMovies(): List<MovieDto> {
        val raw = client.get {
            pathUrl(Constants.GET_UPCOMING, page = 1)
        }.body<DataResponse<MovieDto>>()
        return raw.data.orEmpty()
    }

    override suspend fun fetchMovieDetail(movieId: Int): MovieDetailDto {
        val data = client.get {
            pathUrl(Constants.GET_MOVIE_DETAIL + movieId)
        }.body<MovieDetailDto>()
        return data
    }

    override suspend fun fetchCredits(movieId: Int): CreditDto {
        val data = client.get {
            pathUrl("movie/${movieId}${Constants.GET_CREDITS}")
        }.body<CreditDto>()
        return data
    }

    override fun fetchPagingMovies(movieType: Int): Flow<PagingData<MovieDto>> {
        val movieType = when (movieType) {
            2 -> Constants.GET_NOW_PLAYING
            3 -> Constants.GET_TOP_RATED
            4 -> GET_POPULAR
            else -> ""
        }
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                enablePlaceholders = false
            ), pagingSourceFactory = {

                MoviesPagingSource { page, pageSize ->
                    client.get {
                        pathUrl(movieType, page = page)
                    }.body<DataResponse<MovieDto>>()
                }
            }).flow
    }

}