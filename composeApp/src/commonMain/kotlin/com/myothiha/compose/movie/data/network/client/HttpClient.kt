package com.myothiha.compose.movie.data.network.client

import com.myothiha.compose.movie.data.CLIENT_TOKEN
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
                CLIENT_TOKEN
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