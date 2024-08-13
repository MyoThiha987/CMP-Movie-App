package com.myothiha.compose.movie.di

import com.myothiha.compose.movie.data.cache.MovieDatabase
import com.myothiha.compose.movie.data.cache.datasource.MoviesCacheDataSourceImpl
import com.myothiha.compose.movie.data.datasources.MovieCacheDataSource
import com.myothiha.compose.movie.data.network.client.createHttpClient
import com.myothiha.compose.movie.data.datasources.MovieNetworkDataSource
import com.myothiha.compose.movie.data.network.datasources.MovieNetworkDataSourceImpl
import com.myothiha.compose.movie.data.repository.MoviesRepositoryImpl
import com.myothiha.compose.movie.domain.repository.MovieRepository
import com.myothiha.compose.movie.ui.ExceptionMapper
import com.myothiha.compose.movie.ui.ExceptionMapperImpl
import com.myothiha.compose.movie.ui.home.HomeScreenViewModel
import com.myothiha.compose.movie.ui.detail.MovieDetailViewModel
import com.myothiha.compose.movie.ui.see_more.SeeMoreMoviesScreenViewModel
import io.ktor.client.engine.okhttp.*
import org.koin.dsl.module


/**
 * @Author Liam
 * Created at 09/Aug/2024
 */
actual val appModule = module {
    single { MovieDatabase() }
    single<MovieCacheDataSource> { MoviesCacheDataSourceImpl(get()) }
    single { createHttpClient(engine = OkHttp.create()) }
    single<MovieNetworkDataSource> { MovieNetworkDataSourceImpl(get()) }
    single<MovieRepository> { MoviesRepositoryImpl(get(), get()) }
    single<ExceptionMapper> { ExceptionMapperImpl() }
    single { HomeScreenViewModel(get()) }
    factory { MovieDetailViewModel(get()) }
    factory { SeeMoreMoviesScreenViewModel(get()) }
}
 