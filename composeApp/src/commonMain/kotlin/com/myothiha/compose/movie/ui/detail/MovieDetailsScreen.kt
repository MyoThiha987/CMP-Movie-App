package com.myothiha.compose.movie.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.myothiha.compose.movie.domain.models.MovieFullDetail
import com.myothiha.compose.movie.ui.home.LoadingView
import com.myothiha.compose.movie.ui.components.MovieDetailBodySheet
import com.myothiha.compose.movie.ui.components.MovieDetailHeader
import com.myothiha.compose.movie.ui.components.MovieTopAppBar
import com.myothiha.compose.movie.ui.components.rememberMovieDetailScreenScrollState
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import kotlin.math.roundToInt

/**
 * @Author Liam
 * Created at 10/Aug/2024
 */

@OptIn(KoinExperimentalAPI::class)
@Composable
fun MovieDetailScreen(
    navController: NavController,
    movieId: Int,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<MovieDetailViewModel>()
    val scope = rememberCoroutineScope()

    if (viewModel.movieId != movieId) {
        viewModel.movieId = movieId

        scope.launch {
            viewModel.retrieveMovieDetail()
        }
    }

    val uiState = viewModel.detailScreenUiState

    when (uiState) {
        is DetailScreenState.Loading -> {
            LoadingView()
        }

        is DetailScreenState.Success -> {
            val data = uiState.data
            data?.let {
                MovieDetailScreenContent(
                    navController = navController,
                    data = it,
                    onSearchClick = onSearchClick
                )
            }
        }

        is DetailScreenState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = uiState.errorMessage,
                    modifier = Modifier.align(alignment = Alignment.Center)
                )
            }
        }
    }

}

@Composable
fun MovieDetailScreenContent(
    navController: NavController,
    data: MovieFullDetail,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberMovieDetailScreenScrollState()

    Scaffold(
        modifier = modifier
            .nestedScroll(state.screenNestedScrollConnection),
        snackbarHost = {},
        topBar = {
            MovieTopAppBar(
                navController = navController,
                onSearchClick = onSearchClick
            )
        },
        containerColor = MaterialTheme.colorScheme.surface,
        contentWindowInsets = WindowInsets(0.dp),
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
        ) {
            MovieDetailHeader(
                data = data.movieDetail?.backdropPath.orEmpty(),
                state = state,
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        state.onHeaderPositioned(
                            coordinates.size.height.toFloat() - innerPadding.calculateTopPadding().value,
                        )
                    },
            )

            data.let {
                MovieDetailBodySheet(
                    data = it,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 190.dp)
                        .layout { measurable, constraints ->
                            val placeable = measurable.measure(
                                constraints.copy(maxHeight = constraints.maxHeight - state.sheetScrollOffset.roundToInt()),
                            )
                            layout(placeable.width, placeable.height) {
                                placeable.placeRelative(
                                    0,
                                    0 + (state.sheetScrollOffset / 2).roundToInt(),
                                )
                            }
                        },
                    timetableScreenScrollState = state,
                )
            }
        }
    }
}


@Composable
fun HorizontalTextView(text: String, subText: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            modifier = Modifier,
            fontSize = 16.sp,
        )
        Text(
            text = subText,
            modifier = Modifier,
            fontSize = 16.sp,
        )
    }

}