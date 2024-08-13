package com.myothiha.compose.movie.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myothiha.compose.movie.domain.models.Movie
import com.myothiha.compose.movie.domain.repository.MovieRepository
import com.myothiha.compose.movie.ui.ExceptionMapper
import io.github.aakira.napier.Napier
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.compose.getKoin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @Author Liam
 * Created at 09/Aug/2024
 */
class HomeScreenViewModel(
    private val movieRepository: MovieRepository
) : ViewModel(), KoinComponent {


    private val exception: ExceptionMapper by inject()
    var uiState by mutableStateOf(HomeState())
        private set

    var homeScreenUiState by mutableStateOf<HomeScreenState>(HomeScreenState.Loading)
        private set

    init {
        syncMovies()
    }

    /*private fun fetchMovies() {
        uiState = uiState.copy(isLoading = true)
        viewModelScope.launch {
            coroutineScope {
                runCatching {
                    val data = movieRepository.fetchUpcomingMovies()
                    *//*val upcomingMovies = async { movieRepository.fetchUpcomingMovies() }.await()
                    val nowPlayingMovies =
                        async { movieRepository.fetchNowPlayingMovies(page = 1) }.await()
                    val popularMovies =
                        async { movieRepository.fetchPopularMovies(page = 1) }.await()
                    val topRatedMovies =
                        async { movieRepository.fetchTopRatedMovies(page = 1) }.await()
                    val result = upcomingMovies + nowPlayingMovies + popularMovies + topRatedMovies*//*
                    uiState = uiState.copy(isLoading = false, data = data)

                }.getOrElse {
                    uiState = uiState.copy(isLoading = false, errorMessage = exception.map(it))
                }

                homeScreenUiState = uiState.toUiState()
            }
        }

    }*/

    private fun syncMovies() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            runCatching {
                movieRepository.syncMovies()
                retrieveMovies()
            }.getOrElse {
               // uiState = uiState.copy(isLoading = false, errorMessage = exception.map(it))
                retrieveMovies()
            }
        }
    }

    private fun retrieveMovies() {
        viewModelScope.launch {
            movieRepository.retrieveMovies()
                .collectLatest {
                    //Napier.d("${it}", tag = "Movie")
                    uiState = uiState.copy(isLoading = false, data = it)
                    homeScreenUiState = uiState.toUiState()
                }
        }
    }
}

private suspend fun fetchMoviesSafely(fetchOperation: suspend () -> List<Movie>): List<Movie> {
    return runCatching {
        fetchOperation()
    }.getOrElse {
        throw it // Rethrow the exception to handle it in the main fetchMovies method
    }
}

sealed class HomeScreenState {
    data object Loading : HomeScreenState()
    data class Error(val errorMessage: String) : HomeScreenState()
    data class Success(
        val data: List<Movie>
    ) : HomeScreenState()
}

data class HomeState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val data: List<Movie> = emptyList()
) {
    fun toUiState(): HomeScreenState {
        return when {
            isLoading -> {
                HomeScreenState.Loading
            }

            errorMessage?.isNotEmpty() == true -> {
                HomeScreenState.Error(errorMessage = errorMessage)
            }

            else -> {
                HomeScreenState.Success(data = data)
            }
        }
    }
}