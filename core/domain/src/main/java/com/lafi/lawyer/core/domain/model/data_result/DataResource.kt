package com.lafi.lawyer.core.domain.model.data_result

import com.lafi.lawyer.core.model.common.data.ErrorData

sealed class DataResource<out T> {
    data class Success<T>(val data: T): DataResource<T>()
    data class Error(val error: ErrorData): DataResource<Nothing>()
}