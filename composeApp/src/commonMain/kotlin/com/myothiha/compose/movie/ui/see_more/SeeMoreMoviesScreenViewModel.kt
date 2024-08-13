package com.myothiha.compose.movie.ui.see_more

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.myothiha.compose.movie.domain.models.Movie
import com.myothiha.compose.movie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * @Author Liam
 * Created at 11/Aug/2024
 */

class SeeMoreMoviesScreenViewModel(private val repository: MovieRepository) : ViewModel() {
    var movieType by mutableIntStateOf(0)

    var uiState by mutableStateOf(SeeMoreState())
        private set

    var seeMoreScreenUiState by mutableStateOf<SeeMoreScreenState>(SeeMoreScreenState.Loading)
        private set

    fun fetchSeeMorePagingMovies() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            runCatching {
                val data = repository.fetchPagingMovies(movieType = movieType)
                uiState = uiState.copy(
                    isLoading = false,
                    data = data
                )
            }.getOrElse {
                uiState = uiState.copy(isLoading = false, errorMessage = "")

            }
            seeMoreScreenUiState = uiState.toUiState()
        }
    }
}

sealed class SeeMoreScreenState {
    data object Loading : SeeMoreScreenState()
    data class Error(val errorMessage: String) : SeeMoreScreenState()
    data class Success(val data: Flow<PagingData<Movie>>?) : SeeMoreScreenState()
}

data class SeeMoreState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val data: Flow<PagingData<Movie>>? = null
) {
    fun toUiState(): SeeMoreScreenState {
        return when {
            isLoading -> {
                SeeMoreScreenState.Loading
            }

            errorMessage?.isNotEmpty() == true -> {
                SeeMoreScreenState.Error(errorMessage = errorMessage)
            }

            else -> {
                SeeMoreScreenState.Success(data = data)
            }
        }
    }
}