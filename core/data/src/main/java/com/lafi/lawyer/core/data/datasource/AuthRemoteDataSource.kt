package com.lafi.lawyer.core.data.datasource

import com.lafi.lawyer.core.data.model.auth.AuthLoginSocial
import com.lafi.lawyer.core.data.model.auth.AuthLoginSocialRequest
import com.lafi.lawyer.core.data.model.auth.SmsVerifyCode
import com.lafi.lawyer.core.data.model.auth.SmsVerifyCodeRequest
import com.lafi.lawyer.core.model.common.NetworkResult

interface AuthRemoteDataSource {
    suspend fun postLoginSocial(requestBody: AuthLoginSocialRequest): NetworkResult<AuthLoginSocial>
    suspend fun postSmsVerifyCode(requestBody: SmsVerifyCodeRequest): NetworkResult<SmsVerifyCode>
}