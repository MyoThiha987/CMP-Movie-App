package com.myothiha.compose.movie.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.myothiha.compose.movie.domain.extension.orZero
import com.myothiha.compose.movie.ui.detail.MovieDetailScreen

/**
 * @Author Liam
 * Created at 18/Aug/2024
 */

@Composable
fun AppNavigation(
    navController: NavHostController,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    NavHost(
        navController = navController,
        startDestination = AppGraph.HOME,
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
        homeNavGraph(navHostController = navController, paddingValues = paddingValues)
        favouriteNavGraph(navHostController = navController, paddingValues = paddingValues)
        composable(
            "${AppScreens.MovieDetailScreen.route}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->

            val movieId = backStackEntry.arguments?.getInt("movieId")
            MovieDetailScreen(
                navController = navController,
                movieId = movieId.orZero(),
                onSearchClick = {})
        }


    }
}
