package com.lafi.lawyer.core.network.retrofit

import com.lafi.lawyer.core.model.common.NetworkResult
import com.lafi.lawyer.core.network.retrofit.lafi.auth.AuthDataSource
import com.lafi.lawyer.core.network.retrofit.lafi.auth.api.AuthApi
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.AuthLoginSocialRequest
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.AuthLoginSocialResponse
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.SmsVerifyCodeRequest
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.SmsVerifyCodeResponse
import com.lafi.lawyer.core.network.retrofit.lafi.safeApiCall
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitLafiAuth @Inject constructor(
    private val authApi: AuthApi,
    private val networkJson: Json
): AuthDataSource {
    override suspend fun postLoginSocial(
        requestBody: AuthLoginSocialRequest
    ): NetworkResult<AuthLoginSocialResponse> {
        return safeApiCall(networkJson) {
            authApi.postAuthLoginSocial(requestBody)
        }
    }

    override suspend fun postSmsVerifyCode(
        requestBody: SmsVerifyCodeRequest
    ): NetworkResult<SmsVerifyCodeResponse> {
        return safeApiCall(networkJson) {
            authApi.postSmsVerifyCode(requestBody)
        }
    }
}