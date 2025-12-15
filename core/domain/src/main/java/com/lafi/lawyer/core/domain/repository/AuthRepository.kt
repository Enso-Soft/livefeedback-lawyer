package com.lafi.lawyer.core.domain.repository

import com.lafi.lawyer.core.domain.model.DataResult
import com.lafi.lawyer.core.domain.model.auth.SmsVerifyRequestData
import com.lafi.lawyer.core.domain.model.auth.SocialProvider
import com.lafi.lawyer.core.domain.model.auth.SmsVerifyType
import com.lafi.lawyer.core.domain.model.auth.SocialLoginData
import com.lafi.lawyer.core.domain.model.auth.SocialSignupData

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

    suspend fun signupSocial(
        provider: SocialProvider,
        accessToken: String,
        requestId: String,
        email: String,
        phoneNumber: String,
        name: String
    ): DataResult<SocialSignupData>
}