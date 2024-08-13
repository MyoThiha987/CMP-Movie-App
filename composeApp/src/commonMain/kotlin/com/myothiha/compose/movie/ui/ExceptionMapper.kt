package com.myothiha.compose.movie.ui

/**
 * @Author Liam
 * Created at 11/Aug/2024
 */
interface ExceptionMapper : UnidirectionalMap<Throwable, String> {}

interface UnidirectionalMap<F, T> {
    fun map(item: F): T
}