package com.myothiha.compose.movie.domain.extension
fun Int?.orZero() = this ?: 0
fun Long?.orZero() = this ?: 0L
fun Double?.orZero() = this ?: 0.0
fun Boolean?.orTrue() = this ?: true
fun Boolean?.orFalse() = this ?: false