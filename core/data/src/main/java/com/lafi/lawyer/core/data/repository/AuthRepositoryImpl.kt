package com.lafi.lawyer.core.data.repository

import com.lafi.lawyer.core.data.datasource.AuthRemoteDataSource
import com.lafi.lawyer.core.data.model.auth.request.SocialLoginRequest
import com.lafi.lawyer.core.data.model.auth.request.SmsVerifyRequest
import com.lafi.lawyer.core.data.model.auth.request.SmsVerifyRequestCodeRequest
import com.lafi.lawyer.core.data.model.auth.request.SocialSignupRequest
import com.lafi.lawyer.core.data.model.map
import com.lafi.lawyer.core.domain.model.DataResult
import com.lafi.lawyer.core.domain.model.auth.SmsVerifyRequestData
import com.lafi.lawyer.core.domain.model.auth.SocialProvider
import com.lafi.lawyer.core.domain.model.auth.SmsVerifyType
import com.lafi.lawyer.core.domain.model.auth.SocialLoginData
import com.lafi.lawyer.core.domain.model.auth.SocialSignupData
import com.lafi.lawyer.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun loginSocial(
        provider: SocialProvider,
        accessToken: String
    ): DataResult<SocialLoginData> {
        return authRemoteDataSource.postLoginSocial(
            requestBody = SocialLoginRequest(
                provider = provider.providerName,
                accessToken = accessToken,
                requestID = "12345678901234567890121234567892"
            )
        ).map { it.toDomain() }
    }

    override suspend fun smsVerifyRequest(
        smsVerifyType: SmsVerifyType,
        phoneNumber: String
    ): DataResult<SmsVerifyRequestData> {
        return authRemoteDataSource.postSmsVerifyRequestCode(
            requestBody = SmsVerifyRequestCodeRequest(
                requestType = smsVerifyType.text,
                phoneNumber = phoneNumber
            )
        ).map { it.toDomain() }
    }

    override suspend fun smsVerify(
        smsVerifyType: SmsVerifyType,
        phoneNumber: String,
        requestId: String,
        code: String
    ): DataResult<Unit> {
        return authRemoteDataSource.postSmsVerify(
            requestBody = SmsVerifyRequest(
                requestType = smsVerifyType.text,
                phoneNumber = phoneNumber,
                requestId = requestId,
                code = code
            )
        ).map()
    }

    override suspend fun signupSocial(
        provider: SocialProvider,
        accessToken: String,
        requestId: String,
        email: String,
        phoneNumber: String,
        name: String
    ): DataResult<SocialSignupData> {
        return authRemoteDataSource.postSocialSignup(
            requestBody = SocialSignupRequest(
                provider = provider.providerName,
                accessToken = accessToken,
                requestID = requestId,
                email = email,
                phoneNumber = phoneNumber,
                name = name
            )
        ).map { it.toDomain() }
    }
}