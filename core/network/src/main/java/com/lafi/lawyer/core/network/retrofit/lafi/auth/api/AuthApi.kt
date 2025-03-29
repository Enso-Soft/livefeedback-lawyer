package com.lafi.lawyer.core.network.retrofit.lafi.auth.api

import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.AuthLoginSocialRequest
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.AuthLoginSocialResponse
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.SmsVerifyCodeRequest
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.SmsVerifyCodeResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login/social")
    suspend fun postAuthLoginSocial(
        @Body requestBody: AuthLoginSocialRequest
    ): AuthLoginSocialResponse

    @POST("/auth/sms/verify/code")
    suspend fun postSmsVerifyCode(
        @Body requestBody: SmsVerifyCodeRequest
    ): SmsVerifyCodeResponse
}