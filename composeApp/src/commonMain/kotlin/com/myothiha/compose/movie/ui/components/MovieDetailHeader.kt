package com.myothiha.compose.movie.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.myothiha.compose.movie.ui.home.MovieImageView
import com.myothiha.compose.movie.ui.home.noRippleClickable
import moviecomposemultiplatform.composeapp.generated.resources.Res
import moviecomposemultiplatform.composeapp.generated.resources.ic_back_arrow
import org.jetbrains.compose.resources.painterResource

/**
 * @Author Liam
 * Created at 10/Aug/2024
 */

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MovieTopAppBar(
    navController: NavController = rememberNavController(),
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Icon(
                modifier = Modifier.padding(end = 16.dp).noRippleClickable {
                    navController.popBackStack()
                },
                painter = painterResource(Res.drawable.ic_back_arrow),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        actions = {
            /*IconButton(
                onClick = { },
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_favourite),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }*/
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    )
}

@Composable
fun MovieDetailHeader(
    data: String,
    modifier: Modifier = Modifier,
    state: MovieDetailScreenScrollState = rememberMovieDetailScreenScrollState()
) {
    AnimatedVisibility(
        visible = state.isSheetScrolled.not(),
        enter = fadeIn(
            animationSpec = tween(
                state.sheetScrollOffset.toInt(),
                easing = EaseIn
            )
        ),
        exit = fadeOut(
            animationSpec = tween(
                1000,
                easing = EaseOut
            )
        )
    ) {
        Row(
            modifier = modifier
                .height(225.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            MovieImageView(
                modifier = Modifier.fillMaxWidth(),
                data = data,
            )
        }
    }
}