package com.lafi.lawyer.core.data.datasource

import com.lafi.lawyer.core.data.model.ApiResult
import com.lafi.lawyer.core.data.model.auth.AuthLoginSocial
import com.lafi.lawyer.core.data.model.auth.AuthLoginSocialRequest
import com.lafi.lawyer.core.data.model.auth.SmsVerifyCode
import com.lafi.lawyer.core.data.model.auth.SmsVerifyCodeRequest

interface AuthRemoteDataSource {
    suspend fun postLoginSocial(requestBody: AuthLoginSocialRequest): ApiResult<AuthLoginSocial>
    suspend fun postSmsVerifyCode(requestBody: SmsVerifyCodeRequest): ApiResult<SmsVerifyCode>
}