package com.lafi.lawyer.core.domain.repository

import com.lafi.lawyer.core.domain.model.DataResult
import com.lafi.lawyer.core.domain.model.auth.SmsVerifyRequestData
import com.lafi.lawyer.core.domain.model.auth.SocialProvider
import com.lafi.lawyer.core.domain.model.auth.SmsVerifyType
import com.lafi.lawyer.core.domain.model.auth.SocialLoginData

interface AuthRepository {
    suspend fun loginSocial(
        provider: SocialProvider,
        accessToken: String
    ): DataResult<SocialLoginData>

    suspend fun smsVerifyRequest(
        smsVerifyType: SmsVerifyType,
        phoneNumber: String
    ): DataResult<SmsVerifyRequestData>

    suspend fun smsVerify(
        smsVerifyType: SmsVerifyType,
        phoneNumber: String,
        requestId: String,
        code: String
    ): DataResult<Unit>
}