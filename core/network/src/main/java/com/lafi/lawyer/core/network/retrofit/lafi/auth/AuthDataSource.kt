package com.lafi.lawyer.core.network.retrofit.lafi.auth

import com.lafi.lawyer.core.model.common.NetworkResult
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.AuthLoginSocialRequest
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.AuthLoginSocialResponse
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.SmsVerifyCodeRequest
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.SmsVerifyCodeResponse

interface AuthDataSource {
    suspend fun postLoginSocial(requestBody: AuthLoginSocialRequest): NetworkResult<AuthLoginSocialResponse>
    suspend fun postSmsVerifyCode(requestBody: SmsVerifyCodeRequest): NetworkResult<SmsVerifyCodeResponse>
}