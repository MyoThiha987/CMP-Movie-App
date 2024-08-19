package com.myothiha.compose.movie

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.myothiha.compose.movie.di.changeLang
import com.myothiha.compose.movie.ui.navigation.AppNavigation
import com.myothiha.compose.movie.ui.components.BottomNavigationBar
import com.myothiha.compose.movie.ui.home.HomeScreenState
import com.myothiha.compose.movie.ui.home.HomeScreenViewModel
import com.myothiha.compose.movie.ui.home.LoadingView
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App() {
    MaterialTheme {

        val viewModel = koinViewModel<HomeScreenViewModel>()

        val navController = rememberNavController()
        var lang by remember { mutableStateOf(viewModel.languageState.language) }


        lang = viewModel.languageState.language

        LocalizationApp(language = lang) {
            changeLang(lang)
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(navController = navController)

                    /*when (viewModel.homeScreenUiState) {
                        is HomeScreenState.Loading -> {
                            LoadingView()
                        }

                        is HomeScreenState.Success -> {
                            BottomNavigationBar(navController = navController)
                        }

                        is HomeScreenState.Error -> {}
                    }*/
                }) {
                AppNavigation(navController = navController)
            }
        }
    }
}

