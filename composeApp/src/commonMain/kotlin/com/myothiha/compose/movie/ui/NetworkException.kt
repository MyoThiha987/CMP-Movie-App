package com.myothiha.compose.movie.ui

import okio.IOException


/**
 * @Author Liam
 * Created at 11/Aug/2024
 */

data class NetworkException(
    val errorBody: String? = null,
    var errorCode: Int = 0
) : IOException()