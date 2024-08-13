package com.myothiha.compose.movie.ui

import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive


/**
 * @Author Liam
 * Created at 11/Aug/2024
 */

private const val ERROR_CODE_400 = 400
private const val ERROR_CODE_401 = 401
private const val ERROR_CODE_422 = 422
private const val ERROR_CODE_403 = 403
private const val ERROR_CODE_404 = 404
private const val ERROR_CODE_500 = 500

class ExceptionMapperImpl : ExceptionMapper {
    override fun map(item: Throwable): String {
        return when (item) {
            is UnresolvedAddressException -> "No Internet Connection"
            is SocketTimeoutException -> "Timeout"
            is SerializationException -> "Serialize Error"
            is ConnectTimeoutException -> "No Internet Connection"
            is NetworkException -> parseNetworkError(item)
            else -> {
                "No Internet Connection"
            }
        }
    }

    private fun parseNetworkError(exception: NetworkException): String {
        when (exception.errorCode) {
            ERROR_CODE_400 -> return exception.errorBody?.let { parseMessageFromErrorBody(it) }
                ?: "Bad Request"

            ERROR_CODE_401 -> return exception.errorBody?.let { parseMessageFromErrorBody(it) }
                ?: "Unauthorized"

            ERROR_CODE_422 -> return exception.errorBody?.let { parseMessageFromErrorBody(it) }
                ?: "Unprocessable Entity"

            ERROR_CODE_403 -> return exception.errorBody?.let { parseMessageFromErrorBody(it) }
                ?: "Forbidden"

            ERROR_CODE_404 -> return "Not Found"
            ERROR_CODE_500 -> return "Internal Server Error"
        }

        return "Something went wrong"
    }

    private fun parseMessageFromErrorBody(errorBody: String): String {
        try {
            val jsonObject = Json.parseToJsonElement(errorBody).jsonObject
            return jsonObject["errors"]?.jsonPrimitive?.content ?: ""
        } catch (exception: Exception) {
        }
        return "Something went wrong"
    }
}