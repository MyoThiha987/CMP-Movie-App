package com.myothiha.compose.movie.data.network.response

import com.myothiha.compose.movie.data.cache.entity.MovieEntity
import com.myothiha.compose.movie.domain.models.Movie
import com.myothiha.compose.movie.domain.extension.orFalse
import com.myothiha.compose.movie.domain.extension.orZero
import com.myothiha.compose.movie.domain.models.Cast
import com.myothiha.compose.movie.domain.models.Credit
import com.myothiha.compose.movie.domain.models.Genre
import com.myothiha.compose.movie.domain.models.MovieDetail
import com.myothiha.compose.movie.domain.models.ProductionCompany
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @Author Liam
 * Created at 09/Aug/2024
 */

@Serializable
data class MovieDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("original_title")
    val originalTitle: String?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("popularity")
    val popularity: Double?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("vote_count")
    val voteCount: Int?,
    val isLiked: Boolean?
)

fun MovieDto.toDomain(movieType: Int =0): Movie {
    this.apply {
        return Movie(
            id = id.orZero(),
            originalTitle = originalTitle.orEmpty(),
            overview = overview.orEmpty(),
            popularity = popularity.orZero(),
            backdropPath = backdropPath.orEmpty(),
            posterPath = posterPath.orEmpty(),
            releaseDate = releaseDate.orEmpty(),
            voteAverage = voteAverage.orZero(),
            voteCount = voteCount.orZero(),
            movieType = movieType,
            isLiked = isLiked.orFalse()
        )
    }
}

@Serializable
data class MovieDetailDto(
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("genres")
    val genres: List<GenreDto>,
    @SerialName("id")
    val id: Int,
    @SerialName("original_title")
    val originalTitle: String,
    @SerialName("overview")
    val overview: String,
    @SerialName("popularity")
    val popularity: Double,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyDto>,
    @SerialName("release_date")
    val releaseDate: String,
    @SerialName("revenue")
    val revenue: Int,
    @SerialName("runtime")
    val runtime: Int,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int
)

@Serializable
data class GenreDto(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)

@Serializable
data class ProductionCompanyDto(
    @SerialName("id")
    val id: Int,
    @SerialName("logo_path")
    val logoPath: String? = null,
    @SerialName("name")
    val name: String
)

fun ProductionCompanyDto.toDomain(): ProductionCompany {
    this.apply {
        return ProductionCompany(id = id, logoPath = logoPath.orEmpty(), name = name)
    }
}

fun GenreDto.toDomain(): Genre {
    this.apply {
        return Genre(id = id, name = name)
    }
}

fun MovieDetailDto.toDomain(): MovieDetail {
    this.apply {
        return MovieDetail(
            backdropPath = backdropPath,
            genres = genres.map { it.toDomain() },
            id = id,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = posterPath,
            productionCompanies = productionCompanies.map { it.toDomain() },
            releaseDate = releaseDate,
            revenue = revenue,
            runtime = runtime,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }
}

@Serializable
data class CastDto(
    @SerialName("id")
    val id: Int,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("profile_path")
    val profilePath: String? = null
)

@Serializable
data class CreditDto(
    @SerialName("cast")
    val cast: List<CastDto>,
    @SerialName("crew")
    val crew: List<CastDto>,
    @SerialName("id")
    val id: Int
)

fun CreditDto.toDomain(): Credit {
    this.apply {
        return Credit(cast = cast.map { it.toDomain() }, crew = crew.map { it.toDomain() }, id = id)
    }
}

fun CastDto.toDomain(): Cast {
    this.apply {
        return Cast(id = id, originalName = originalName, profilePath = profilePath.orEmpty())
    }
}

fun MovieDto.toEntity(movieCategory: Int): MovieEntity {
    return MovieEntity().apply {
        id = this@toEntity.id.orZero()
        originalTitle = this@toEntity.originalTitle.orEmpty()
        overview = this@toEntity.overview.orEmpty()
        popularity = this@toEntity.popularity.orZero()
        backdropPath = this@toEntity.backdropPath.orEmpty()
        posterPath = this@toEntity.posterPath.orEmpty()
        releaseDate = this@toEntity.releaseDate.orEmpty()
        voteAverage = this@toEntity.voteAverage.orZero()
        voteCount = this@toEntity.voteCount.orZero()
        movieType = movieCategory
        isLiked = this@toEntity.isLiked.orFalse()
    }
}

fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = id,
        originalTitle = originalTitle.orEmpty(),
        overview = overview.orEmpty(),
        popularity = popularity,
        backdropPath = backdropPath.orEmpty(),
        posterPath = posterPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage,
        voteCount = voteCount,
        movieType = movieType,
        isLiked = isLiked.orFalse()
    )
}
