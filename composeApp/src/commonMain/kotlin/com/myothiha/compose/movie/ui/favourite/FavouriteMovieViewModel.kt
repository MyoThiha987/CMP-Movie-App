package com.myothiha.compose.movie.ui.favourite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myothiha.compose.movie.domain.models.Movie
import com.myothiha.compose.movie.domain.repository.MovieRepository
import com.myothiha.compose.movie.ui.home.HomeScreenState
import com.myothiha.compose.movie.ui.home.HomeState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

/**
 * @Author Liam
 * Created at 19/Aug/2024
 */

class FavouriteMovieViewModel(private val repository: MovieRepository) : ViewModel() {

    var uiState by mutableStateOf(FavouriteState())
        private set

    var favouriteScreenUiState by mutableStateOf<FavouriteScreenState>(FavouriteScreenState.Loading)
        private set

    init {
        retrieveMovies()
    }

    fun onEvent(event: ScreenUiEvent) {
        when (event) {
            is ScreenUiEvent.onSaveMovie -> {
                updateSaveMovie(movieId = event.movieId, movieType = event.movieType)
            }
        }
    }

    private fun retrieveMovies() {
        viewModelScope.launch {
            repository.retrieveMovies()
                .collectLatest {
                    uiState = uiState.copy(
                        isLoading = false,
                        data = it.filter { it.isLiked && it.movieType == 2 }.toList()
                    )
                    favouriteScreenUiState = uiState.toUiState()
                }
        }
    }

    private fun updateSaveMovie(movieId: Int, movieType: Int) {
        viewModelScope.launch {
            repository.updateSavedMovie(movieId = movieId, movieType = movieType)
        }
    }
}

sealed class ScreenUiEvent {
    data class onSaveMovie(val movieId: Int, val movieType: Int) : ScreenUiEvent()
}

sealed class FavouriteScreenState {
    data object Loading : FavouriteScreenState()
    data class Success(val data: List<Movie>) : FavouriteScreenState()
}

data class FavouriteState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val data: List<Movie> = emptyList()
) {
    fun toUiState(): FavouriteScreenState {
        return when {
            isLoading -> {
                FavouriteScreenState.Loading
            }

            else -> {
                FavouriteScreenState.Success(data = data)
            }
        }
    }
}