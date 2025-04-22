package com.lafi.lawyer.core.model.common.data

sealed class ApiResult<out T> {
    data class Success<T>(val data: T): ApiResult<T>()
    data class Error(val error: ErrorData): ApiResult<Nothing>()
}