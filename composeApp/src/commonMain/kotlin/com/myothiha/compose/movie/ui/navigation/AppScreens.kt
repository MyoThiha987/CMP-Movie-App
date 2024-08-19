package com.myothiha.compose.movie.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.myothiha.compose.movie.domain.extension.orZero
import com.myothiha.compose.movie.ui.favourite.FavouriteMovieScreen
import com.myothiha.compose.movie.ui.home.HomeScreen
import com.myothiha.compose.movie.ui.search.SearchMoviesScreen
import com.myothiha.compose.movie.ui.see_more.SeeMoreMoviesScreen

/**
 * @Author Liam
 * Created at 18/Aug/2024
 */

sealed class AppScreens(val route: String) {
    data object HomeScreen : AppScreens(route = AppRoute.HOME)
    data object MovieDetailScreen : AppScreens(route = AppRoute.DETAIL)
    data object SeeMoreMovieScreen : AppScreens(route = AppRoute.VIEW_ALL)
    data object SearchMovieScreen : AppScreens(route = AppRoute.SEARCH)
    data object FavouriteMovieScreen : AppScreens(route = AppRoute.FAVOURITE)
}

fun NavGraphBuilder.homeNavGraph(
    navHostController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation(startDestination = AppScreens.HomeScreen.route, route = AppGraph.HOME) {
        composable(AppScreens.HomeScreen.route) {
            HomeScreen(
                padding = paddingValues,
                onClickSearch = {
                    navHostController.navigate(AppRoute.SEARCH)
                },
                onClickDetail = {
                    navHostController.navigate("${AppRoute.DETAIL}/$it")
                },
                onClickSeeMore = {
                    navHostController.navigate("${AppRoute.VIEW_ALL}/$it")
                }
            )
        }

        composable(
            route = AppScreens.SearchMovieScreen.route
        ) {
            SearchMoviesScreen(
                onBackClick = { navHostController.navigateUp() },
                onClickDetail = {
                    navHostController.navigate("${AppRoute.DETAIL}/$it")
                }
            )
        }

        composable(
            "${AppScreens.SeeMoreMovieScreen.route}/{movieType}",
            arguments = listOf(navArgument("movieType") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val movieType = backStackEntry.arguments?.getInt("movieType")

            SeeMoreMoviesScreen(
                movieType = movieType.orZero(),
                onBackClick = {
                    navHostController.navigateUp()
                },
                onClickDetail = {
                    navHostController.navigate("${AppRoute.DETAIL}/$it")
                }
            )
        }
    }
}

fun NavGraphBuilder.favouriteNavGraph(
    navHostController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation(
        startDestination = AppScreens.FavouriteMovieScreen.route,
        route = AppGraph.FAVOURITE
    ) {
        composable(route = AppScreens.FavouriteMovieScreen.route) {
            FavouriteMovieScreen(
                onDetailClick = {
                    navHostController.navigate("${AppRoute.DETAIL}/$it")
                }
            )
        }
    }
}