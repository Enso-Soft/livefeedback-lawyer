package com.lafi.lawyer.core.domain.repository

import com.lafi.lawyer.core.domain.model.DataResult
import com.lafi.lawyer.core.domain.model.auth.SocialProvider
import com.lafi.lawyer.core.domain.model.signup.SmsVerifyType

interface AuthRepository {
    suspend fun loginSocial(
        provider: SocialProvider,
        accessToken: String
    ): DataResult<String>

    suspend fun smsVerifyRequest(
        smsVerifyType: SmsVerifyType,
        phoneNumber: String
    ): DataResult<String>
}