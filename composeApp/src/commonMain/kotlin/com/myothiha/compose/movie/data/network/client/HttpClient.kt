package com.myothiha.compose.movie.data.network.client

import com.myothiha.compose.movie.data.Constants
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.json.Json


fun createHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine = engine) {
        val converter = KotlinxSerializationConverter(Json {
            prettyPrint = true
            ignoreUnknownKeys = true
            explicitNulls = false
            coerceInputValues = true

        })
        install(ContentNegotiation) {
            register(
                ContentType.Application.Json, converter
            )
        }

        /*install(ContentNegotiation) {
            json(
                json = Json { ignoreUnknownKeys = true }
            )
        }*/

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header(
                "Authorization",
                "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2MWEzMWQzNWUwYTMyNzg1ZjJlNGM0NDk5ZjA0M2FlOCIsInN1YiI6IjVkYzM5NDBjOWQ4OTM5MDAxODM0YjVlZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Eve0FD4yriTnRWsCD0P2bTXplUlUObIIfs1Q5ChAdgc"
            )
        }

    }

}

fun HttpRequestBuilder.pathUrl(path: String, page: Int = 1) {
    url {
        takeFrom(Constants.BASE_URL)
        path("3", path)
        parameter("language", "en")
        parameter("page", page)
    }
}