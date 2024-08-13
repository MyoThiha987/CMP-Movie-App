package com.myothiha.compose.movie

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.myothiha.compose.movie.domain.extension.orZero
import com.myothiha.compose.movie.ui.navigation.AppRoute
import com.myothiha.compose.movie.ui.home.HomeScreen
import com.myothiha.compose.movie.ui.detail.MovieDetailScreen
import com.myothiha.compose.movie.ui.see_more.SeeMoreMoviesScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = AppRoute.HOME,
            /*enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(100)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(100)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(100)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(100)
                )
            }*/
        ) {
            composable(AppRoute.HOME) {
                HomeScreen(padding = PaddingValues(0.dp), onClickDetail = {
                    navController.navigate("${AppRoute.DETAIL}/$it")
                }, onClickSeeMore = {
                    navController.navigate("${AppRoute.VIEW_ALL}/$it")
                })
            }
            composable(
                "${AppRoute.DETAIL}/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStackEntry ->

                val movieId = backStackEntry.arguments?.getInt("movieId")
                MovieDetailScreen(
                    navController = navController,
                    movieId = movieId.orZero(),
                    onSearchClick = {})
            }
            composable(
                "${AppRoute.VIEW_ALL}/{movieType}",
                arguments = listOf(navArgument("movieType") {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                val movieType = backStackEntry.arguments?.getInt("movieType")

                SeeMoreMoviesScreen(
                    movieType = movieType.orZero(),
                    onBackClick = {
                        navController.navigateUp()
                    },
                    onClickDetail = {
                        navController.navigate("${AppRoute.DETAIL}/$it")
                    }
                )
            }
        }
    }
}
