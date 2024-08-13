package com.myothiha.compose.movie.data.cache

import com.myothiha.compose.movie.data.cache.entity.MovieEntity
import io.github.aakira.napier.Napier
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.delete
import io.realm.kotlin.ext.query
import kotlinx.coroutines.coroutineScope
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
            delete<MovieEntity>()
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

    suspend fun deleteMovies() {
        realm!!.write {
            delete<MovieEntity>()
        }
        /*coroutineScope {

        }*/
    }
}