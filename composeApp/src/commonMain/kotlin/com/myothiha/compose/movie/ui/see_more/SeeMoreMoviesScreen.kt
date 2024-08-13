package com.myothiha.compose.movie.ui.see_more

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import app.cash.paging.LoadStateLoading
import app.cash.paging.LoadStateNotLoading
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import com.myothiha.compose.movie.domain.models.Movie
import com.myothiha.compose.movie.ui.home.LoadingView
import com.myothiha.compose.movie.ui.home.MovieImageView
import com.myothiha.compose.movie.ui.home.bouncingClickable
import com.myothiha.compose.movie.ui.home.noRippleClickable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import moviecomposemultiplatform.composeapp.generated.resources.Res
import moviecomposemultiplatform.composeapp.generated.resources.ic_back_arrow
import moviecomposemultiplatform.composeapp.generated.resources.ic_favourite
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * @Author Liam
 * Created at 11/Aug/2024
 */
@Composable
fun SeeMoreMoviesScreen(
    movieType: Int,
    onClickDetail: (Int) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<SeeMoreMoviesScreenViewModel>()
    val scope = rememberCoroutineScope()

    if (viewModel.movieType != movieType) {
        viewModel.movieType = movieType

        scope.launch {
            viewModel.fetchSeeMorePagingMovies()
        }
    }

    val uiState = viewModel.seeMoreScreenUiState
    when (uiState) {
        is SeeMoreScreenState.Loading -> {
            LoadingView()
        }

        is SeeMoreScreenState.Success -> {
            val data = uiState.data
            data?.let {
                SeeMoreMoviesContent(
                    movieType = movieType,
                    data = it,
                    onClickDetail = {
                        onClickDetail(it)
                    },
                    onBackClick = onBackClick
                )
            }
        }

        is SeeMoreScreenState.Error -> {}
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeeMoreMoviesContent(
    movieType: Int,
    data: Flow<PagingData<Movie>>,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    onClickDetail: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    val data = data.collectAsLazyPagingItems()
    val title = when (movieType) {
        2 -> "Now Playing"
        3 -> "Top Rate"
        4 -> "Popular"
        else -> "Now Playing"
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets(16.dp, 16.dp, 16.dp, 16.dp),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Icon(
                            painter = painterResource(resource = Res.drawable.ic_back_arrow),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                title = { Text(text = title) })
        },
        contentColor = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.5f),
    ) {

        MoviesGridView(state = state, paddingValues = it, data = data) {
            MovieGridItemView(
                data = it,
                onClickDetail = {
                    onClickDetail(it)
                },
                onClickSave = { movieId, isLiked, movieType ->
                })
        }

    }

}

@Composable
fun <T : Any> MoviesGridView(
    state: LazyGridState,
    paddingValues: PaddingValues,
    data: LazyPagingItems<T>?,
    content: @Composable (T) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        state = state,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = paddingValues
    ) {

        data?.let {
            items(it.itemCount) { index ->
                val item = data[index]
                item?.let { content(it) }
            }

            it.loadState.apply {
                when {
                    refresh is LoadStateNotLoading && data.itemCount < 1 -> {
                        item(span = {
                            GridItemSpan(maxLineSpan)
                        }) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No Items",
                                    modifier = Modifier.align(Alignment.Center),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

//                    refresh is LoadStateLoading -> {
//                        item(span = {
//                            GridItemSpan(maxLineSpan)
//                        }) {
//                            Box(
//                                modifier = Modifier.fillMaxSize(),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                CircularProgressIndicator(
//                                    modifier = Modifier
//                                        .size(40.dp)
//
//                                )
//                            }
//                        }
//                    }

                    /*append is LoadStateNotLoading -> {
                        item(span = {
                            GridItemSpan(maxLineSpan)
                        }) {
                            ErrorMessage(
                                message = "No Internet Connection.",
                                onClickRetry = { data.retry() },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }*/

                    append is LoadStateLoading -> {
                        item(span = {
                            GridItemSpan(maxLineSpan)
                        }) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .padding(10.dp)
                                        .align(alignment = Alignment.Center)
                                )
                            }
                        }
                    }

//                    refresh is LoadStateError -> {
//                        item(
//                            span = {
//                                GridItemSpan(maxLineSpan)
//                            }
//                        ) {
//                            ErrorMessage(
//                                message = "No Internet Connection",
//                                onClickRetry = { data.retry() },
//                            )
//                        }
//                    }
//
//                    append is LoadStateError -> {
//                        item(span = {
//                            GridItemSpan(maxLineSpan)
//                        }) {
//                            ErrorMessage(
//                                message = "No Internet Connection",
//                                onClickRetry = { data.retry() },
//                            )
//                        }
//                    }
                }
            }
        }

    }
}


@Composable
fun MovieGridItemView(
    modifier: Modifier = Modifier,
    data: Movie,
    onClickDetail: (Int) -> Unit,
    onClickSave: (Int, Boolean, Int) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            Card(modifier = Modifier.bouncingClickable {
                onClickDetail(data.id)
            }) {
                MovieImageView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    data = data.backdropPath
                )
            }
            Text(
                modifier = Modifier
                    .padding(6.dp)
                    .width(180.dp),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                text = data.originalTitle,
                fontSize = 18.sp,
                maxLines = 1,
                lineHeight = 21.sp,
                fontWeight = FontWeight(200)
            )

        }
        val tintColor = if (data.isLiked) Color.Red else Color.White
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp)
                .align(alignment = Alignment.TopEnd)
                .noRippleClickable {
                    onClickSave(data.id, data.isLiked, data.movieType)
                },
            painter = painterResource(resource = Res.drawable.ic_favourite),
            contentDescription = null,
            tint = tintColor
        )
    }

}

@Composable
fun ErrorMessage(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = modifier.fillMaxSize().padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.weight(1f),
                maxLines = 2
            )
            OutlinedButton(onClick = onClickRetry) {
                Text(text = "Retry")
            }
        }
    }
}