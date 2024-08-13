package com.myothiha.compose.movie.data.cache.entity

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId


/**
 * @Author Liam
 * Created at 12/Aug/2024
 */
class MovieEntity : RealmObject {
    @PrimaryKey
    var autoId: ObjectId = ObjectId()
    var id: Int = 0
    var originalTitle: String? = null
    var overview: String? = null
    var popularity: Double = 0.0
    var backdropPath: String ?= null
    var posterPath: String ?= null
    var releaseDate: String? = null
    var voteAverage: Double = 0.0
    var voteCount: Int = 0
    var movieType: Int = 0
    var isLiked: Boolean = false
}