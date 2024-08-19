package com.myothiha.compose.movie.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.myothiha.compose.movie.domain.models.Movie
import com.myothiha.compose.movie.domain.repository.LanguageSettingRepository
import com.myothiha.compose.movie.domain.repository.MovieRepository
import com.myothiha.compose.movie.ui.ExceptionMapper
import io.github.aakira.napier.Napier
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.compose.getKoin
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @Author Liam
 * Created at 09/Aug/2024
 */
class HomeScreenViewModel(
    private val movieRepository: MovieRepository,
    private val languageSettingRepository: LanguageSettingRepository
) : ViewModel(), KoinComponent {

    private val exception: ExceptionMapper by inject()

    var uiState by mutableStateOf(HomeState())
        private set

    var languageState by mutableStateOf(LanguageState())
        private set

    var homeScreenUiState by mutableStateOf<HomeScreenState>(HomeScreenState.Loading)
        private set

    init {
        loadSelectedLanguage()
        syncMovies()
        retrieveMovies()
    }

    private fun syncMovies() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            runCatching {
                movieRepository.syncMovies()
                //retrieveMovies()
            }.getOrElse {
                retrieveMovies()
            }
        }
    }

    private fun retrieveMovies() {
        viewModelScope.launch {
            movieRepository.retrieveMovies()
                .collectLatest {
                    uiState = uiState.copy(isLoading = false, data = it)
                    homeScreenUiState = uiState.toUiState()
                }
        }
    }

    private fun updateSaveMovie(movieId: Int, movieType: Int) {
        viewModelScope.launch {
            movieRepository.updateSavedMovie(movieId = movieId, movieType = movieType)
        }
    }

    fun onEvent(event: ScreenUiEvent) {
        when (event) {
            is ScreenUiEvent.onSelectLanguage -> saveSelectedLanguage(
                language = event.language
            )

            is ScreenUiEvent.onSaveMovie -> {
                updateSaveMovie(movieId = event.movieId, movieType = event.movieType)
            }
        }
    }

    private fun saveSelectedLanguage(language: String) {
        viewModelScope.launch {
            languageSettingRepository.saveLanguage(language = language)
        }
    }

    private fun loadSelectedLanguage() {
        viewModelScope.launch {
            languageSettingRepository.readLanguage().collectLatest {
                languageState =
                    languageState.copy(language = it)
            }
        }
    }
}

sealed class ScreenUiEvent {
    data class onSelectLanguage(val language: String) : ScreenUiEvent()
    data class onSaveMovie(val movieId: Int, val movieType: Int) : ScreenUiEvent()
}

sealed class HomeScreenState {
    data object Loading : HomeScreenState()
    data class Error(val errorMessage: String) : HomeScreenState()
    data class Success(val data: List<Movie>) : HomeScreenState()
}

data class LanguageState(
    val language: String = "en"
)

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