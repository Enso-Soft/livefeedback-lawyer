package com.lafi.lawyer.core.model.common

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T): NetworkResult<T>()
    data class Error(val error: ApiError): NetworkResult<Nothing>()
}

data class ApiError(val code: Int, val message: String, val errorType: String = "")