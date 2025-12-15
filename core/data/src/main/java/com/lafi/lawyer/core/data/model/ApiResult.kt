package com.lafi.lawyer.core.data.model

import com.lafi.lawyer.core.domain.model.DataResult
import com.lafi.lawyer.core.model.common.data.ErrorData

sealed class ApiResult<out T> {
    data class Success<T>(val data: T): ApiResult<T>()
    data class Error(val error: ErrorData): ApiResult<Nothing>()
}

fun <T, R> ApiResult<T>.map(transform: (T) -> R): DataResult<R> {
    return when (this) {
        is ApiResult.Success -> DataResult.Success(transform(this.data))
        is ApiResult.Error -> DataResult.Error(this.error)
    }
}

fun <T> ApiResult<T>.map(): DataResult<T> {
    return when (this) {
        is ApiResult.Success -> DataResult.Success(this.data)
        is ApiResult.Error -> DataResult.Error(this.error)
    }
}