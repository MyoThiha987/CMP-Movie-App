package com.myothiha.compose.movie.ui.components

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myothiha.compose.movie.domain.extension.orZero
import com.myothiha.compose.movie.domain.models.Cast
import com.myothiha.compose.movie.domain.models.Genre
import com.myothiha.compose.movie.domain.models.MovieFullDetail
import com.myothiha.compose.movie.domain.models.ProductionCompany
import com.myothiha.compose.movie.ui.home.HorizontalItemView
import com.myothiha.compose.movie.ui.home.MovieImageView
import com.myothiha.compose.movie.ui.detail.HorizontalTextView

/**
 * @Author Liam
 * Created at 10/Aug/2024
 */

@Composable
fun MovieDetailBodySheet(
    data: MovieFullDetail,
    timetableScreenScrollState: MovieDetailScreenScrollState,
    modifier: Modifier = Modifier,
) {
    val corner by animateIntAsState(
        if (timetableScreenScrollState.isScreenLayoutCalculating || timetableScreenScrollState.isSheetExpandable) 24 else 0,
        label = "Timetable corner state",
    )
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(topStart = corner.dp, topEnd = corner.dp),
        tonalElevation = 1.dp,
        color = MaterialTheme.colorScheme.surfaceContainer
    ) {
        MovieDetailSheetContent(data = data)
    }
}

@Composable
fun MovieDetailSheetContent(data: MovieFullDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = data.movieDetail?.originalTitle.orEmpty(),
            modifier = Modifier
                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        HorizontalItemView(
            modifier = Modifier
                .fillMaxWidth(),
            // .padding(start = 16.dp, end = 16.dp),
            arrangement = Arrangement.Center,
            alignment = Alignment.CenterVertically,
            data = data.movieDetail?.genres.orEmpty()
        ) {
            GenreItemView(data = it)
        }

        //VerticalGrid
        MovieGridView(data = data)

    }
}

@Composable
fun MovieGridView(data: MovieFullDetail) {
    LazyVerticalGrid(
        state = rememberLazyGridState(),
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(top = 12.dp, bottom = 16.dp),
        columns = GridCells.Fixed(count = 2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy((-16).dp)
    ) {
        //MovieInfo
        movieInfo(data = data)

        //StoryLine
        storyLineItem(data = data)
        //Cast Title
        castTitle()

        //CrewItemView
        castAndCrewItems(data = data)

        //Crew ItemView
        crewItem(data = data)

        //CompanyItemView
        companyItem(data = data)

    }
}

fun LazyGridScope.movieInfo(data: MovieFullDetail) {
    item(span = {
        GridItemSpan(maxLineSpan)
    }) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 1.dp,
                    color = Color.Black.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            HorizontalTextView("⭐  Review", "${data.movieDetail?.voteAverage.orZero()}")
            HorizontalTextView(
                "🗓  Release Date",
                data.movieDetail?.releaseDate.orEmpty()
            )
            HorizontalTextView(
                "🕓  Duration",
                " ${data.movieDetail?.runtime.orZero()} mins"
            )
        }
    }
}

fun LazyGridScope.storyLineItem(data: MovieFullDetail) {
    item(
        span = {
            GridItemSpan(maxLineSpan)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Story Line",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 6.dp)
                    .fillMaxWidth(),
                fontSize = 22.sp,
                lineHeight = 30.sp,
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = data.movieDetail?.overview.orEmpty(),
                fontSize = 16.sp,
                lineHeight = 20.sp
            )
        }
    }
}

fun LazyGridScope.castAndCrewItems(data: MovieFullDetail) {
    items(items = data.credit?.cast.orEmpty(),
        key = { cast -> cast.id }
    ) {
        CastAndCrewItemView(data = it, modifier = Modifier.padding(horizontal = 16.dp))
    }
}

fun LazyGridScope.companyItem(data: MovieFullDetail) {
    item(
        span = {
            GridItemSpan(maxLineSpan)
        }
    ) {
        TitleAndContent(
            title = "Company",
            content = {
                HorizontalItemView(
                    data = data.movieDetail?.productionCompanies.orEmpty(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    arrangement = Arrangement.spacedBy(12.dp),
                    alignment = Alignment.CenterVertically,
                    content = { CompanyItemView(data = it) }
                )
            }
        )

    }
}

fun LazyGridScope.crewItem(data: MovieFullDetail) {
    item(
        span = {
            GridItemSpan(maxLineSpan)
        }
    ) {
        TitleAndContent(
            title = "Crew",
            content = {
                HorizontalItemView(
                    data = data.credit?.crew.orEmpty(),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    arrangement = Arrangement.spacedBy(12.dp),
                    alignment = Alignment.CenterVertically,
                    content = { CastAndCrewItemView(data = it) }
                )
            }
        )

    }
}

fun LazyGridScope.castTitle() {
    item(
        span = {
            GridItemSpan(maxLineSpan)
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Cast",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                fontSize = 22.sp,
                lineHeight = 30.sp,
            )
        }
    }
}

@Composable
fun GenreItemView(data: Genre) {
    Text(
        text = data.name,
        modifier = Modifier
            .padding(start = 8.dp)
            .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = Color.DarkGray, shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp),
        fontSize = 10.sp,
        color = Color.DarkGray,
    )
}

@Composable
fun CastAndCrewItemView(modifier: Modifier = Modifier, data: Cast) {
    Card(
        modifier = modifier
            .fillMaxHeight(),
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp

    ) {
        Row(
            modifier = Modifier
                .width(170.dp)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            MovieImageView(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape),
                data = data.profilePath
            )
            Column {
                Text(
                    modifier = Modifier,
                    text = data.originalName,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    maxLines = 2
                )
            }
        }
    }

}

@Composable
fun CompanyItemView(modifier: Modifier = Modifier, data: ProductionCompany) {
    Card(
        contentColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier.fillMaxHeight(),
        shape = RoundedCornerShape(12.dp),
        elevation = 0.dp

    ) {
        Row(
            modifier = Modifier
                .width(170.dp)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            MovieImageView(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape),
                data = data.logoPath
            )
            Column {
                Text(
                    modifier = Modifier,
                    text = data.name,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    maxLines = 2
                )
            }

        }

    }

}

@Composable
fun TitleAndContent(
    title: String,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(12.dp),
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = verticalArrangement
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .paddingFromBaseline(top = 16.dp)
                    .padding(horizontal = 16.dp)
            )
        }

        content()
    }
}