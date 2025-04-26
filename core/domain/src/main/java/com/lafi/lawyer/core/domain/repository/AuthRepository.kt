package com.lafi.lawyer.core.domain.repository

import com.lafi.lawyer.core.domain.model.DataResult
import com.lafi.lawyer.core.model.common.sms_verify.SmsVerifyType

interface AuthRepository {
    suspend fun loginSocial(
        provider: String,
        accessToken: String
    ): DataResult<String>

    suspend fun smsVerifyRequest(
        smsVerifyType: SmsVerifyType,
        phoneNumber: String
    ): DataResult<String>
}