package com.lafi.lawyer.core.data.datasource

import com.lafi.lawyer.core.data.model.ApiResult
import com.lafi.lawyer.core.data.model.auth.SocialLogin
import com.lafi.lawyer.core.data.model.auth.request.SocialLoginRequest
import com.lafi.lawyer.core.data.model.auth.request.SmsVerifyRequest
import com.lafi.lawyer.core.data.model.auth.SmsVerifyRequestCode
import com.lafi.lawyer.core.data.model.auth.SocialSignup
import com.lafi.lawyer.core.data.model.auth.request.SmsVerifyRequestCodeRequest
import com.lafi.lawyer.core.data.model.auth.request.SocialSignupRequest

interface AuthRemoteDataSource {
    suspend fun postLoginSocial(requestBody: SocialLoginRequest): ApiResult<SocialLogin>
    suspend fun postSmsVerifyRequestCode(requestBody: SmsVerifyRequestCodeRequest): ApiResult<SmsVerifyRequestCode>
    suspend fun postSmsVerify(requestBody: SmsVerifyRequest): ApiResult<Unit>
    suspend fun postSocialSignup(requestBody: SocialSignupRequest): ApiResult<SocialSignup>
}