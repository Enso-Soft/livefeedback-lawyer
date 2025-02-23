package com.lafi.lawyer.core.network.retrofit.lafi

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val detail: Detail
) {
    @Serializable
    data class Detail(
        val error: Error
    ) {
        @Serializable
        data class Error(
            val code: Int,
            val message: String,
            val type: String
        )
    }
}
