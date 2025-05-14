package com.lafi.lawyer.core.network.retrofit.lafi.api

import com.lafi.lawyer.core.data.model.auth.request.AuthLoginSocialRequest
import com.lafi.lawyer.core.data.model.auth.request.SmsVerifyRequest
import com.lafi.lawyer.core.data.model.auth.request.SmsVerifyRequestCodeRequest
import com.lafi.lawyer.core.network.retrofit.lafi.model.auth.AuthLoginSocialResponse
import com.lafi.lawyer.core.network.retrofit.lafi.model.auth.SmsVerifyRequestResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login/social")
    suspend fun postAuthLoginSocial(
        @Body requestBody: AuthLoginSocialRequest
    ): AuthLoginSocialResponse

    /** 휴대폰 인증 코드 요청 */
    @POST("/auth/sms/verify/request")
    suspend fun postSmsVerifyRequest(
        @Body requestBody: SmsVerifyRequestCodeRequest
    ): SmsVerifyRequestResponse

    /** 휴대폰 인증 코드 검증 */
    @POST("/auth/sms/verify")
    suspend fun postSmsVerify(
        @Body requestBody: SmsVerifyRequest
    )
}