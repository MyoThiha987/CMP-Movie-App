package com.myothiha.compose.movie.ui.favourite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myothiha.compose.movie.domain.models.Movie
import com.myothiha.compose.movie.ui.home.LoadingView
import com.myothiha.compose.movie.ui.see_more.MovieGridItemView
import moviecomposemultiplatform.composeapp.generated.resources.Res
import moviecomposemultiplatform.composeapp.generated.resources.lbl_favourite
import moviecomposemultiplatform.composeapp.generated.resources.lbl_no_favourite_data
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

/**
 * @Author Liam
 * Created at 19/Aug/2024
 */

@OptIn(KoinExperimentalAPI::class)
@Composable
fun FavouriteMovieScreen(
    onDetailClick: (Int) -> Unit
) {

    val viewModel = koinViewModel<FavouriteMovieViewModel>()
    val uiEvent = viewModel::onEvent
    val uiState = viewModel.favouriteScreenUiState

    FavouriteMovieContent(
        onDetailClick = { onDetailClick(it) },
        uiEvent = uiEvent,
        uiState = uiState
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouriteMovieContent(
    onDetailClick: (Int) -> Unit,
    uiEvent: (ScreenUiEvent) -> Unit,
    uiState: FavouriteScreenState
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets(16.dp, 16.dp, 16.dp, 90.dp),
        topBar = {
            androidx.compose.material.TopAppBar(
                elevation = 1.dp,
                backgroundColor = Color.White,
                title = {
                    Text(
                        text = stringResource(resource = Res.string.lbl_favourite),
                        color = Color.Black
                    )
                })
        },
        contentColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f),
    ) { padding ->
        when (uiState) {
            is FavouriteScreenState.Loading -> {
                LoadingView()
            }

            is FavouriteScreenState.Success -> {
                if (uiState.data.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = stringResource(resource = Res.string.lbl_no_favourite_data),

                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface
                            ),
                            fontSize = 18.sp,
                            maxLines = 1,
                        )
                    }
                }
                if (uiState.data.isNotEmpty()) {
                    FavouriteMovieGridLayout(
                        padding = padding,
                        data = uiState.data,
                        onDetailClick = onDetailClick,
                        uiEvent = uiEvent
                    )
                }
            }
        }

    }
}

@Composable
fun FavouriteMovieGridLayout(
    data: List<Movie>,
    padding: PaddingValues = PaddingValues(0.dp),
    onDetailClick: (Int) -> Unit,
    uiEvent: (ScreenUiEvent) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize().padding(vertical = 16.dp),
        state = rememberLazyGridState(),
        columns = GridCells.Fixed(2),
        contentPadding = padding,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = data) {
            MovieGridItemView(
                data = it,
                onClickDetail = { movieId ->
                    onDetailClick(movieId)
                },
                onClickSave = { id, isLiked, movieType ->
                    uiEvent(
                        ScreenUiEvent.onSaveMovie(
                            movieId = id,
                            movieType = movieType
                        )
                    )

                }
            )
        }

    }
}