package com.myothiha.compose.movie.ui.home

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.myothiha.compose.movie.domain.models.Movie
import com.myothiha.compose.movie.ui.components.CarouselMovieView
import io.github.aakira.napier.Napier
import moviecomposemultiplatform.composeapp.generated.resources.Res
import moviecomposemultiplatform.composeapp.generated.resources.ic_favourite
import moviecomposemultiplatform.composeapp.generated.resources.ic_notification
import moviecomposemultiplatform.composeapp.generated.resources.ic_search
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

/**
 * @Author Liam
 * Created at 09/Aug/2024
 */

@OptIn(KoinExperimentalAPI::class)
@Preview
@Composable
fun HomeScreen(
    padding: PaddingValues,
    onClickDetail: (Int) -> Unit,
    onClickSeeMore: (Int) -> Unit
) {
    val viewModel = koinViewModel<HomeScreenViewModel>()
val uiState = viewModel.homeScreenUiState
    when (uiState) {
        is HomeScreenState.Loading -> LoadingView()
        is HomeScreenState.Success -> {
            MovieListView(
                data = uiState.data,
                padding = padding,
                onClickSeeMore = {
                    onClickSeeMore(it)
                },
                onClickDetail = {
                    onClickDetail(it)
                }
            )
        }

        is HomeScreenState.Error -> {
            Text(uiState.errorMessage)
        }
    }

}

@Composable
fun LoadingView() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = Color.Black)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieListView(
    padding: PaddingValues,
    data: List<Movie>,
    onClickSeeMore: (Int) -> Unit,
    onClickSave: (Int, Boolean, Int) -> Unit = { _, _, _ -> },
    onClickDetail: (Int) -> Unit

) {

    val upcomingMovies by remember {
        mutableStateOf(data.filter { it.movieType == 1 }.toMutableList())
    }

    val nowPlayingMovies by remember {
        mutableStateOf(data.filter { it.movieType == 2 }.toMutableList())
    }

    val popularMovies by remember {
        mutableStateOf(data.filter { it.movieType == 3 }.toMutableList())
    }

    val topRatedMovies by remember {
        mutableStateOf(data.filter { it.movieType == 4 }.toMutableList())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = Color.White,
                title = {
                    androidx.compose.material3.Text(
                        text = "Hi, Myo Thiha",
                        color = Color.Black
                    )
                }, actions = {
                    Icon(
                        modifier = Modifier
                            .size(32.dp),
                        painter = painterResource(resource = Res.drawable.ic_search),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(resource = Res.drawable.ic_notification),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                })
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(it)
                .background(color = Color.Gray.copy(alpha = 0.1f))
        ) {
            item {
                CarouselMovieView(
                    data = upcomingMovies,
                    modifier = Modifier.padding(vertical = 12.dp)
                )
                if (nowPlayingMovies.isNotEmpty()) CategoryAndContent(
                    text = "Now Playing",
                    movieType = 2,
                    onClickSeeMore = {
                        onClickSeeMore(it)
                    }, content = {
                        HorizontalItemView(
                            arrangement = Arrangement.spacedBy(12.dp),
                            data = nowPlayingMovies,
                            content = {
                                MovieItemSmallView(
                                    data = it,
                                    onClickDetail = {
                                        onClickDetail(it)
                                    },
                                    onClickSave = onClickSave

                                )
                            }
                        )
                    }
                )

                if (topRatedMovies.isNotEmpty()) CategoryAndContent(
                    text = "Top Rate",
                    movieType = 3,
                    onClickSeeMore = {
                        onClickSeeMore(it)
                    },
                    content = {
                        HorizontalLargeItemView(
                            topRatedMovies,
                            isFling = true,
                            onClickDetail = {
                                onClickDetail(it)
                            }
                        )
                    }
                )

                if (popularMovies.isNotEmpty()) {
                    CategoryAndContent(
                        text = "Popular",
                        movieType = 4,
                        onClickSeeMore = {
                            onClickSeeMore(it)
                        },
                        content = {
                            HorizontalItemView(
                                arrangement = Arrangement.spacedBy(16.dp),
                                data = popularMovies,
                                content = {
                                    MovieItemMediumView(
                                        data = it,
                                        onClickDetail = {
                                            onClickDetail(it)
                                        })
                                }
                            )
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun CategoryAndContent(
    text: String,
    movieType: Int,
    onClickSeeMore: (Int) -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            androidx.compose.material3.Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp),
                modifier = Modifier
                    .paddingFromBaseline(top = 16.dp)
                    .padding(horizontal = 16.dp)
            )
            Text(
                text = "View all",
                style = MaterialTheme.typography.titleMedium.copy(fontSize = 12.sp),
                modifier = Modifier
                    .noRippleClickable { onClickSeeMore(movieType) }
                    .paddingFromBaseline(top = 16.dp)
                    .padding(horizontal = 16.dp)
            )
        }

        content()
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalStdlibApi::class)
@Composable
fun <T : Any> HorizontalItemView(
    data: List<T>,
    arrangement: Arrangement.HorizontalOrVertical,
    alignment: Alignment.Vertical = Alignment.Top,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState(),
    content: @Composable (T) -> Unit,
) {

    LazyRow(
        modifier = modifier,
        state = state,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
        horizontalArrangement = arrangement,
        verticalAlignment = alignment,
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {

        itemsIndexed(
            items = data
        ) { index, it ->
            content(it)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalStdlibApi::class)
@Composable
fun HorizontalLargeItemView(
    data: List<Movie>,
    isFling: Boolean,
    state: LazyListState = rememberLazyListState(),
    onClickDetail: (Int) -> Unit
) {
    LazyRow(
        state = state,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        flingBehavior = if (isFling) rememberSnapFlingBehavior(lazyListState = state) else ScrollableDefaults.flingBehavior()
    ) {
        itemsIndexed(
            items = data
        ) { index, movie ->
            MovieItemLargeView(
                onClickDetail = { onClickDetail(movie.id) },
                data = movie
            )
        }
    }
}

@Composable
inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit
): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}


@Composable
fun <T> MovieImageView(modifier: Modifier = Modifier, data: T) {
    val context = LocalPlatformContext.current
    SubcomposeAsyncImage(
        modifier = modifier,
        contentScale = ContentScale.Crop,
        model = ImageRequest.Builder(context)
            .data("https://image.tmdb.org/t/p/original/${data}")
            .crossfade(true).build(),
        contentDescription = null,
        loading = {
            androidx.compose.material3.CircularProgressIndicator(
                modifier = Modifier.wrapContentSize()
            )
        }
    )
}

@Composable
fun LazyItemScope.MovieItemLargeView(
    modifier: Modifier = Modifier,
    data: Movie,
    onClickDetail: (Int) -> Unit

) {
    Column(modifier = modifier) {
        Card(
            modifier = Modifier.bouncingClickable {
                onClickDetail(data.id)
            },
            shape = RoundedCornerShape(12.dp)
        ) {
            MovieImageView(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .aspectRatio(16f / 9f),
                data = data.backdropPath
            )
        }
    }
}

@Composable
fun Modifier.bouncingClickable(
    onClick: () -> Unit
) = composed {

    val interactionSource = remember { MutableInteractionSource() }


    val isPressed by interactionSource.collectIsPressedAsState()

    val animationTransition = updateTransition(targetState = isPressed, label = "press")

    val scale by animationTransition.animateFloat(
        targetValueByState = { pressed -> if (pressed) 0.94f else 1f },
        label = "scale animation"
    )

    val opactiy by animationTransition.animateFloat(
        targetValueByState = { pressed -> if (pressed) 0.7f else 1f },
        label = "opactiy animation"
    )

    this
        .graphicsLayer {
            this.scaleX = scale
            this.scaleY = scale
            this.alpha = opactiy
        }
        .clickable(
            interactionSource = interactionSource,
            indication = null,
            enabled = true,
            onClick = onClick
        )
}


@Composable
fun MovieItemSmallView(
    modifier: Modifier = Modifier,
    data: Movie,
    onClickDetail: (Int) -> Unit,
    onClickSave: (Int, Boolean, Int) -> Unit
) {
    Box(modifier = modifier.width(180.dp)) {
        Column {
            Card(modifier = Modifier
                .bouncingClickable {
                    onClickDetail(data.id)
                }) {
                MovieImageView(
                    modifier = Modifier
                        .width(180.dp)
                        .height(180.dp),
                    data = data.backdropPath
                )
            }
            Text(
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .padding(horizontal = 4.dp),
                text = data.originalTitle,
                fontSize = 16.sp,
                maxLines = 1,
                lineHeight = 18.sp,
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
            painter = painterResource(Res.drawable.ic_favourite),
            contentDescription = null,
            tint = tintColor
        )
    }

}

@Composable
fun MovieItemMediumView(
    modifier: Modifier = Modifier,
    data: Movie,
    onClickDetail: (Int) -> Unit

) {
    Column(modifier = modifier) {
        Card(modifier = Modifier.bouncingClickable {
            onClickDetail(data.id)
        }) {
            MovieImageView(
                modifier = Modifier
                    .width(250.dp)
                    .height(190.dp),
                data = data.backdropPath
            )
        }
        Text(
            modifier = Modifier
                .padding(vertical = 6.dp)
                .padding(horizontal = 4.dp)
                .width(239.dp)
                .wrapContentHeight(),
            text = data.originalTitle,
            maxLines = 1,
            fontSize = 16.sp,
            lineHeight = 18.sp
        )
    }
}
