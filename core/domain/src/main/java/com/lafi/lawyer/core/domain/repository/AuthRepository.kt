package com.lafi.lawyer.core.domain.repository

import com.lafi.lawyer.core.model.common.NetworkResult
import com.lafi.lawyer.core.model.common.sms_verify.SmsVerifyType

interface AuthRepository {
    suspend fun loginSocial(
        provider: String,
        accessToken: String
    ): NetworkResult<Boolean>

    suspend fun smsVerifyRequest(
        smsVerifyType: SmsVerifyType,
        phoneNumber: String
    ): NetworkResult<String>
}