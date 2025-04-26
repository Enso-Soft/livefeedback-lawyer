package com.lafi.lawyer.core.network.retrofit

import com.lafi.lawyer.core.data.model.ApiResult
import com.lafi.lawyer.core.model.common.data.ErrorData
import com.lafi.lawyer.core.network.retrofit.lafi.ErrorResponse
import kotlinx.serialization.json.Json
import retrofit2.HttpException

internal suspend fun <T> safeApiCall(
    networkJson: Json,
    apiCall: suspend () -> T
): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall())
    } catch (e: HttpException) {
        val errorBody = try {
            val errorString = e.response()?.errorBody()?.string()
            networkJson.decodeFromString<ErrorResponse>(errorString ?: "")
        } catch (e: Exception) {
            null
        }

        ApiResult.Error(
            error = ErrorData(
                code = errorBody?.detail?.error?.code ?: e.code(),
                message = errorBody?.detail?.error?.message ?: e.message(),
                errorType = errorBody?.detail?.error?.type ?: ""
            )
        )
    } catch (e: Exception) {
        ApiResult.Error(
            error = ErrorData(
                code = -1,
                message = e.message ?: "",
            )
        )
    }
}