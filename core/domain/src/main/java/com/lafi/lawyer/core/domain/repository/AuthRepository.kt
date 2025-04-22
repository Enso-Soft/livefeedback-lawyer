package com.lafi.lawyer.core.domain.repository

import com.lafi.lawyer.core.model.common.data.ApiResult
import com.lafi.lawyer.core.model.common.sms_verify.SmsVerifyType

interface AuthRepository {
    suspend fun loginSocial(
        provider: String,
        accessToken: String
    ): ApiResult<Boolean>

    suspend fun smsVerifyRequest(
        smsVerifyType: SmsVerifyType,
        phoneNumber: String
    ): ApiResult<String>
}