package com.lafi.lawyer.core.network.datasource

import com.lafi.lawyer.core.data.datasource.AuthRemoteDataSource
import com.lafi.lawyer.core.data.model.auth.AuthLoginSocial
import com.lafi.lawyer.core.data.model.auth.AuthLoginSocialRequest
import com.lafi.lawyer.core.data.model.auth.SmsVerifyCode
import com.lafi.lawyer.core.data.model.auth.SmsVerifyCodeRequest
import com.lafi.lawyer.core.model.common.data.ApiResult
import com.lafi.lawyer.core.network.retrofit.lafi.auth.api.AuthApi
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

    override suspend fun postSmsVerifyCode(requestBody: SmsVerifyCodeRequest): ApiResult<SmsVerifyCode> {
        return safeApiCall(networkJson) { authApi.postSmsVerifyCode(requestBody).toData() }
    }
}