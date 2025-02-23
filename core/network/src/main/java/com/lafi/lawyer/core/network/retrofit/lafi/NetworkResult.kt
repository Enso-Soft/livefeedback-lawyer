package com.lafi.lawyer.core.network.retrofit.lafi

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T): NetworkResult<T>()
    data class Error(
        val code: Int,
        val message: String,
        val errorType: String = ""
    ): NetworkResult<Nothing>()
}