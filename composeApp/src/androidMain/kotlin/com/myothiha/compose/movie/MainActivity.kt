package com.myothiha.compose.movie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.myothiha.compose.movie.di.appModule
import com.myothiha.compose.movie.di.viewModelModule
import com.myothiha.compose.movie.domain.models.Movie
import org.koin.compose.KoinApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppMovieItemPreview() {
    KoinApplication(application = { modules(appModule) }) {
        App()
    }
}
