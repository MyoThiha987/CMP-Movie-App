package com.myothiha.compose.movie.data.cache.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author Liam
 * Created at 12/Aug/2024
 */
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val autoId: Int? = null,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val backdropPath: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val movieType: Int,
    var isLiked: Boolean
)