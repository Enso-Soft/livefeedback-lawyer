package com.lafi.lawyer.core.network.retrofit.lafi.api

import com.lafi.lawyer.core.data.model.auth.request.SocialLoginRequest
import com.lafi.lawyer.core.data.model.auth.request.SmsVerifyRequest
import com.lafi.lawyer.core.data.model.auth.request.SmsVerifyRequestCodeRequest
import com.lafi.lawyer.core.data.model.auth.request.SocialSignupRequest
import com.lafi.lawyer.core.network.retrofit.lafi.model.auth.SocialLoginResponse
import com.lafi.lawyer.core.network.retrofit.lafi.model.auth.SmsVerifyRequestResponse
import com.lafi.lawyer.core.network.retrofit.lafi.model.auth.SocialSignupResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    /** 소셜 로그인 */
    @POST("/auth/login/social")
    suspend fun postAuthLoginSocial(
        @Body requestBody: SocialLoginRequest
    ): SocialLoginResponse

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

    /** 소셜 회원가입 */
    @POST("/auth/signup/social")
    suspend fun postSocialSignup(
        @Body requestBody: SocialSignupRequest
    ): SocialSignupResponse
}