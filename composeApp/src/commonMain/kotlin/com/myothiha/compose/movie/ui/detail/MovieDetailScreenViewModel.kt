package com.myothiha.compose.movie.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myothiha.compose.movie.domain.models.MovieFullDetail
import com.myothiha.compose.movie.domain.repository.MovieRepository
import com.myothiha.compose.movie.ui.ExceptionMapper
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @Author Liam
 * Created at 10/Aug/2024
 */

class MovieDetailViewModel(
    private val repository: MovieRepository
) : ViewModel() ,KoinComponent{
    private val exception: ExceptionMapper by inject()

    var movieId by mutableIntStateOf(0)

    var uiState by mutableStateOf(DetailState())
        private set

    var detailScreenUiState by mutableStateOf<DetailScreenState>(DetailScreenState.Loading)
        private set

    fun retrieveMovieDetail() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            runCatching {
                val data = repository.retrieveMovieDetail(movieId = movieId)
                uiState = uiState.copy(
                    isLoading = false,
                    data = data
                )
            }.getOrElse {
                uiState = uiState.copy(isLoading = false, errorMessage = exception.map(it))

            }
            detailScreenUiState = uiState.toUiState()
        }
    }

}

sealed class DetailScreenState {
    data object Loading : DetailScreenState()
    data class Error(val errorMessage: String) : DetailScreenState()
    data class Success(
        val data: MovieFullDetail?
    ) : DetailScreenState()
}

data class DetailState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val data: MovieFullDetail? = null
) {
    fun toUiState(): DetailScreenState {
        return when {
            isLoading -> {
                DetailScreenState.Loading
            }

            errorMessage?.isNotEmpty() == true -> {
                DetailScreenState.Error(errorMessage = errorMessage)
            }

            else -> {
                DetailScreenState.Success(data = data)
            }
        }
    }
}
