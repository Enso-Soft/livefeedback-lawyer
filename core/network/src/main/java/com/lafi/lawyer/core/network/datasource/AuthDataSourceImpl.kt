package com.lafi.lawyer.core.network.datasource

import com.lafi.lawyer.core.data.datasource.AuthRemoteDataSource
import com.lafi.lawyer.core.data.model.ApiResult
import com.lafi.lawyer.core.data.model.auth.AuthLoginSocial
import com.lafi.lawyer.core.data.model.auth.AuthLoginSocialRequest
import com.lafi.lawyer.core.data.model.auth.SmsVerifyRequest
import com.lafi.lawyer.core.data.model.auth.SmsVerifyRequestCode
import com.lafi.lawyer.core.data.model.auth.SmsVerifyRequestCodeRequest
import com.lafi.lawyer.core.network.retrofit.lafi.api.AuthApi
import com.lafi.lawyer.core.network.retrofit.safeApiCall
import kotlinx.serialization.json.Json
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authApi: AuthApi,
    private val networkJson: Json
) : AuthRemoteDataSource {
    override suspend fun postLoginSocial(requestBody: AuthLoginSocialRequest): ApiResult<AuthLoginSocial> {
        return safeApiCall(networkJson) { authApi.postAuthLoginSocial(requestBody).toData() }
    }

    override suspend fun postSmsVerifyRequestCode(requestBody: SmsVerifyRequestCodeRequest): ApiResult<SmsVerifyRequestCode> {
        return safeApiCall(networkJson) { authApi.postSmsVerifyRequest(requestBody).toData() }
    }

    override suspend fun postSmsVerify(requestBody: SmsVerifyRequest): ApiResult<Nothing> {
        return safeApiCall(networkJson) { authApi.postSmsVerify(requestBody) }
    }
}