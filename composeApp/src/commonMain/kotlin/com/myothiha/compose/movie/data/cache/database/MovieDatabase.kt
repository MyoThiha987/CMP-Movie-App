package com.myothiha.compose.movie.data.cache.database

import com.myothiha.compose.movie.data.cache.entity.MovieEntity
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @Author Liam
 * Created at 12/Aug/2024
 */

class MovieDatabase() {

    private var realm: Realm? = null

    init {
        initializeRealmDatabase()
    }

    private fun initializeRealmDatabase() {
        if (realm == null || realm!!.isClosed()) {
            val configuration =
                RealmConfiguration
                    .Builder(setOf(MovieEntity::class))
                    .compactOnLaunch()
                    .build()

            realm = Realm.open(configuration = configuration)
        }
    }

    suspend fun saveMovies(data: List<MovieEntity>) {
        realm!!.write {

            val query = this.query<MovieEntity>()
            delete(query)

            data.forEach {
                copyToRealm(it)
            }
        }
    }

    fun retrieveMovies(): Flow<List<MovieEntity>> {
        return realm!!
            .query<MovieEntity>()
            .find()
            .asFlow()
            .map { it.list.toList() }
    }

    suspend fun favouriteMovie(movieId: Int, movieType: Int) {
        realm!!.write {
            val query =
                query<MovieEntity>("id == $0 AND movieType == $1", movieId, movieType).find()
                    .first()

            query.isLiked = query.isLiked.not()
        }
    }

    /*suspend fun deleteMovies(){
        realm!!.write {
            val query = this.query<MovieEntity>()
            delete(query)
        }
    }*/
}