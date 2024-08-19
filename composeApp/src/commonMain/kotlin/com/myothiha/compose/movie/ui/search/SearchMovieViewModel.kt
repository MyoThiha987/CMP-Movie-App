package com.myothiha.compose.movie.ui.search

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.cash.paging.map
import com.myothiha.compose.movie.domain.models.Movie
import com.myothiha.compose.movie.domain.repository.MovieRepository
import com.myothiha.compose.movie.ui.see_more.SeeMoreScreenState
import com.myothiha.compose.movie.ui.see_more.SeeMoreState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @Author myothiha
 * Created 24/03/2024 at 11:15 PM.
 **/

class SearchMovieViewModel(
    private val repository: MovieRepository
) : ViewModel() {


    var searchUiState by mutableStateOf(SearchState())
        private set

    fun onEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.OnSearchMovie -> searchMovies(query = event.query)
            is SearchUiEvent.OnQueryChange -> updateQuery(newQuery = event.newQuery)
        }
    }

    private fun searchMovies(query: String) {
        viewModelScope.launch {
            runCatching {
                val data = repository.searchMovies(query = query)
                searchUiState = searchUiState.copy(
                    idle = false,
                    data = data,
                )

            }.getOrElse {
                searchUiState = searchUiState.copy(idle = false)
            }
        }

    }

    fun updateQuery(newQuery: String) {
        searchUiState = searchUiState.copy(query = newQuery)
    }
}

data class SearchState(
    val idle: Boolean = true,
    val data: Flow<PagingData<Movie>>? = null,
    val query: String? = null
)

sealed class SearchUiEvent {
    data class OnSearchMovie(val query: String) : SearchUiEvent()
    data class OnQueryChange(val newQuery: String) : SearchUiEvent()
}