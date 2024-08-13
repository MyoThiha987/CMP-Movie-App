package com.myothiha.compose.movie.domain.models

/**
 * @Author Liam
 * Created at 09/Aug/2024
 */
data class Movie(
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
    val isLiked: Boolean
)
data class MovieDetail(
    val backdropPath: String,
    val genres: List<Genre>,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<ProductionCompany>,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val voteAverage: Double,
    val voteCount: Int
)

data class MovieFullDetail(
    val movieDetail: MovieDetail?,
    val credit: Credit?
)

data class Genre(
    val id: Int,
    val name: String
)

data class ProductionCompany(
    val id: Int,
    val logoPath: String,
    val name: String
)

data class Cast(
    val id: Int,
    val originalName: String,
    val profilePath: String
)
data class Credit(
    val cast: List<Cast>,
    val crew: List<Cast>,
    val id: Int
)
