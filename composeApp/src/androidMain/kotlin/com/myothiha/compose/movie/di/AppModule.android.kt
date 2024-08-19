package com.myothiha.compose.movie.di

import com.myothiha.compose.movie.data.cache.database.MovieDatabase
import com.myothiha.compose.movie.data.cache.datasource.MoviesCacheDataSourceImpl
import com.myothiha.compose.movie.data.datasources.MovieCacheDataSource
import com.myothiha.compose.movie.data.network.client.createHttpClient
import com.myothiha.compose.movie.data.datasources.MovieNetworkDataSource
import com.myothiha.compose.movie.data.network.datasources.MovieNetworkDataSourceImpl
import com.myothiha.compose.movie.data.repository.MoviesRepositoryImpl
import com.myothiha.compose.movie.domain.repository.MovieRepository
import com.myothiha.compose.movie.data.repository.LanguageSettingRepositoryImpl
import com.myothiha.compose.movie.domain.repository.LanguageSettingRepository
import com.myothiha.compose.movie.ui.ExceptionMapper
import com.myothiha.compose.movie.ui.ExceptionMapperImpl
import com.myothiha.compose.movie.ui.home.HomeScreenViewModel
import com.myothiha.compose.movie.ui.detail.MovieDetailViewModel
import com.myothiha.compose.movie.ui.favourite.FavouriteMovieViewModel
import com.myothiha.compose.movie.ui.search.SearchMovieViewModel
import com.myothiha.compose.movie.ui.see_more.SeeMoreMoviesScreenViewModel
import io.ktor.client.engine.okhttp.*
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


/**
 * @Author Liam
 * Created at 09/Aug/2024
 */
actual val appModule = module {
    single { MovieDatabase() }
    single { createHttpClient(engine = OkHttp.create()) }
    singleOf(::ExceptionMapperImpl).bind(ExceptionMapper::class)
}

val repositoryModule = module {
    singleOf(::MoviesRepositoryImpl).bind(MovieRepository::class)
    singleOf(::LanguageSettingRepositoryImpl).bind(LanguageSettingRepository::class)
}

val datasourceModule = module {
    singleOf(::MoviesCacheDataSourceImpl).bind(MovieCacheDataSource::class)
    singleOf(::MovieNetworkDataSourceImpl).bind(MovieNetworkDataSource::class)
}

val dataStoreModule = module {
    single { LanguageDataStore(get()) }
    single { get<LanguageDataStore>().createDataStore() }

}

val viewModelModule = module {
    factory { HomeScreenViewModel(get(),get()) }
    factory { MovieDetailViewModel(get()) }
    factory { SeeMoreMoviesScreenViewModel(get()) }
    factory { SearchMovieViewModel(get()) }
    factory { FavouriteMovieViewModel(get()) }
}
 