package com.myothiha.compose.movie.domain.usecase

import com.myothiha.compose.movie.domain.models.Movie
import com.myothiha.compose.movie.domain.coroutine.CoroutineUseCase
import com.myothiha.compose.movie.domain.coroutine.DispatcherProvider
import com.myothiha.compose.movie.domain.repository.MovieRepository

/**
 * @Author Liam
 * Created at 09/Aug/2024
 */
class FetchMovieUseCase(
    private val repository: MovieRepository,
    dispatcherProvider: DispatcherProvider
) : CoroutineUseCase<Unit, List<Movie>>(dispatcherProvider = dispatcherProvider) {
    override suspend fun provide(params: Unit): List<Movie> {
        return repository.fetchUpcomingMovies()
    }

}