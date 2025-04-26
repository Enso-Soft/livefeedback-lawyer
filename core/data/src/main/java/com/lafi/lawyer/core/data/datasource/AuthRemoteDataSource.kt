package com.lafi.lawyer.core.data.datasource

import com.lafi.lawyer.core.data.model.ApiResult
import com.lafi.lawyer.core.data.model.auth.AuthLoginSocial
import com.lafi.lawyer.core.data.model.auth.AuthLoginSocialRequest
import com.lafi.lawyer.core.data.model.auth.SmsVerifyRequest
import com.lafi.lawyer.core.data.model.auth.SmsVerifyRequestCode
import com.lafi.lawyer.core.data.model.auth.SmsVerifyRequestCodeRequest

interface AuthRemoteDataSource {
    suspend fun postLoginSocial(requestBody: AuthLoginSocialRequest): ApiResult<AuthLoginSocial>
    suspend fun postSmsVerifyRequestCode(requestBody: SmsVerifyRequestCodeRequest): ApiResult<SmsVerifyRequestCode>
    suspend fun postSmsVerify(requestBody: SmsVerifyRequest): ApiResult<Nothing>
}