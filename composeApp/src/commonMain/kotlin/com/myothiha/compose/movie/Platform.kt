package com.myothiha.compose.movie

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform