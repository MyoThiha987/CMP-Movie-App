package com.myothiha.compose.movie.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import app.cash.paging.compose.collectAsLazyPagingItems
import com.myothiha.compose.movie.domain.models.Movie
import com.myothiha.compose.movie.ui.home.LoadingView
import com.myothiha.compose.movie.ui.see_more.MovieGridItemView
import com.myothiha.compose.movie.ui.see_more.MoviesGridView
import com.myothiha.compose.movie.ui.see_more.SeeMoreMoviesContent
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import moviecomposemultiplatform.composeapp.generated.resources.Res
import moviecomposemultiplatform.composeapp.generated.resources.greeting
import moviecomposemultiplatform.composeapp.generated.resources.ic_back_arrow
import moviecomposemultiplatform.composeapp.generated.resources.lbl_search
import moviecomposemultiplatform.composeapp.generated.resources.lbl_search_hint
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

/**
 * @Author myothiha
 * Created 24/03/2024 at 4:09 PM.
 **/
@OptIn(KoinExperimentalAPI::class)
@Composable
fun SearchMoviesScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onClickDetail: (Int) -> Unit
) {
    val viewModel = koinViewModel<SearchMovieViewModel>()

    val uiState = viewModel.searchUiState

    val uiEvent = viewModel::onEvent

    SearchMoviesContent(
        uiState = uiState,
        uiEvent = uiEvent,
        onBackClick = onBackClick,
        onClickDetail = {
            onClickDetail(it)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMoviesContent(
    uiState: SearchState,
    uiEvent: (SearchUiEvent) -> Unit,
    modifier: Modifier = Modifier,
    state: LazyGridState = rememberLazyGridState(),
    onClickDetail: (Int) -> Unit,
    onBackClick: () -> Unit
) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = WindowInsets(16.dp, 16.dp, 16.dp, 16.dp),

        topBar = {
            androidx.compose.material.TopAppBar(
                elevation = 1.dp,
                backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Icon(
                            painter = painterResource(resource = Res.drawable.ic_back_arrow),
                            contentDescription = "back_action",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                title = { Text(text = stringResource(resource = Res.string.lbl_search),color = Color.Black) })
        },
        containerColor = MaterialTheme.colorScheme.background,
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            SearchMovieSection(
                query = uiState.query.orEmpty(),
                modifier = Modifier.padding(padding),
                onQueryChange = {
                    uiEvent(SearchUiEvent.OnQueryChange(it))
                },
                onClickSearch = {
                    uiEvent(SearchUiEvent.OnSearchMovie(query = it))

                }
            )

            MoviesGridView(
                state = state,
                paddingValues = PaddingValues(horizontal = 16.dp),
                data = uiState.data?.collectAsLazyPagingItems()
            ) {
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

}

@Composable
fun SearchMovieSection(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onClickSearch: (String) -> Unit
) {

    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(12.dp))
            .background(color = Color.Gray.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(12.dp),
        textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = Color.Black,
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
        ),
        value = query,
        onValueChange = onQueryChange,
        keyboardOptions = KeyboardOptions(
            imeAction = androidx.compose.ui.text.input.ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onClickSearch(query)
                focusManager.clearFocus()
            }
        ),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color.Gray
            )
        },
        placeholder = {
            Text(
                text = stringResource(resource = Res.string.lbl_search_hint),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                    fontSize = 16.sp
                ),
            )
        }
    )

}