package com.myothiha.compose.movie.data

/**
 * @Author Liam
 * Created at 09/Aug/2024
 */

typealias UpComing = Int
typealias NOW_PLAYING = Int
typealias TOP_RATED = Int
typealias POPULAR = Int

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/"
    const val GET_NOW_PLAYING = "/movie/now_playing"
    const val GET_TOP_RATED = "/movie/top_rated"
    const val GET_UPCOMING = "/movie/upcoming"
    const val GET_POPULAR = "/movie/popular"
    const val GET_SEARCH = "search/movie"
    const val GET_MOVIE_DETAIL = "/movie/"
    const val GET_CREDITS = "/credits"
    const val UPCOMING: UpComing = 1
    const val NOW_PLAYING: NOW_PLAYING = 2
    const val TOP_RATED: TOP_RATED = 3
    const val POPULAR: POPULAR = 4
}