package com.lafi.lawyer.core.network.retrofit.lafi

import com.lafi.lawyer.core.model.common.ApiError
import com.lafi.lawyer.core.model.common.NetworkResult
import kotlinx.serialization.json.Json
import retrofit2.HttpException

internal suspend fun <T> safeApiCall(
    networkJson: Json,
    apiCall: suspend () -> T
): NetworkResult<T> {
    return try {
        NetworkResult.Success(apiCall())
    } catch (e: HttpException) {
        val errorBody = try {
            val errorString = e.response()?.errorBody()?.string()
            networkJson.decodeFromString<ErrorResponse>(errorString ?: "")
        } catch (e: Exception) {
            null
        }

        NetworkResult.Error(
            error = ApiError(
                code = errorBody?.detail?.error?.code ?: e.code(),
                message = errorBody?.detail?.error?.message ?: e.message(),
                errorType = errorBody?.detail?.error?.type ?: ""
            )
        )
    } catch (e: Exception) {
        NetworkResult.Error(
            error = ApiError(
                code = -1,
                message = e.message ?: "",
            )
        )
    }
}