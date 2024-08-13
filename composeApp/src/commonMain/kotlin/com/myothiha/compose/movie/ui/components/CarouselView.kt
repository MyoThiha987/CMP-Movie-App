package com.myothiha.compose.movie.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.myothiha.compose.movie.domain.models.Movie

/**
 * @Author Liam
 * Created at 10/Aug/2024
 */


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselMovieView(
    modifier: Modifier = Modifier,
    data: List<Movie>,
    pagerState: PagerState = rememberPagerState(initialPage = 0, pageCount = { data.size })
) {
    val images by remember {
        mutableStateOf(data.map { it.backdropPath })
    }
    Column(modifier = modifier.padding(top = 0.dp)) {
        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            state = pagerState,
            pageSpacing = 10.dp,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) { currentPage ->
            val isCurrentPage = currentPage == pagerState.currentPage
            val context = LocalPlatformContext.current
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .graphicsLayer {
                        lerp(start = 0.95f, stop = 1f, fraction = 1f - pagerState.currentPageOffsetFraction.coerceIn(0f, 1f)).also {
                            scaleX = it
                            scaleY = it
                        }
                        alpha = lerp(start = 0.5f, stop = 1f, fraction = 1f - pagerState.currentPageOffsetFraction.coerceIn(0f, 1f))
                    }
                    .aspectRatio(if (isCurrentPage) 16f / 9f else 16f / 8.5f)
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(if (isCurrentPage) 16f / 9f else 16f / 8.5f),
                    contentScale = ContentScale.Crop,
                    model = ImageRequest.Builder(context)
                        .data("https://image.tmdb.org/t/p/original/${images[currentPage]}")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    loading = {
                        CircularProgressIndicator(
                            modifier = Modifier.wrapContentSize()
                        )
                    }
                )
            }

        }
    }
}

/*
@Composable
private fun CarouselMovieItem(
    currentPage: Int,
    images: List<String>,
    isCurrentPage: Boolean
) {
    val context = LocalPlatformContext.current
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .graphicsLayer {
                val pageOffset = calculateCurrentOffsetForPage(currentPage).absoluteValue
                lerp(start = 0.95f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)).also {
                    scaleX = it
                    scaleY = it
                }
                alpha = lerp(start = 0.5f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f))
            }
            .aspectRatio(if (isCurrentPage) 16f / 9f else 16f / 8.5f)
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(if (isCurrentPage) 16f / 9f else 16f / 8.5f),
            contentScale = ContentScale.Crop,
            model = ImageRequest.Builder(context)
                .data("https://image.tmdb.org/t/p/original/${images[currentPage]}")
                .crossfade(true)
                .build(),
            contentDescription = null,
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier.wrapContentSize()
                )
            }
        )
    }
}*/
