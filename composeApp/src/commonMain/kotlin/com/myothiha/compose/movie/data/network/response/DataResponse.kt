package com.myothiha.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @Author myothiha
 * Created 05/03/2024 at 9:52 PM.
 **/

@Serializable
data class DataResponse<T>(
    @SerialName(value ="page")
    val currentPage: Int?,

    @SerialName(value ="results")
    val data: List<T>?,

    @SerialName(value ="total_pages")
    val totalPages: Int?
)