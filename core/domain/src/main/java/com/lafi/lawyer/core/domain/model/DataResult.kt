package com.lafi.lawyer.core.domain.model

import com.lafi.lawyer.core.model.common.data.ErrorData

/** Data to Domain 일 때 dababase/network 데이터들을 필요 시 DataResult 반환 */
sealed class DataResult<out T> {
    data class Success<T>(val data: T): DataResult<T>()
    data class Error(val error: ErrorData): DataResult<Nothing>()
}