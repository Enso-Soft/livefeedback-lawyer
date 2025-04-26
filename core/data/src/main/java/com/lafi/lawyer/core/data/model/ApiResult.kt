package com.lafi.lawyer.core.data.model

import com.lafi.lawyer.core.model.common.data.ErrorData

sealed class ApiResult<out T> {
    data class Success<T>(val data: T): ApiResult<T>()
    data class Error(val error: ErrorData): ApiResult<Nothing>()
}