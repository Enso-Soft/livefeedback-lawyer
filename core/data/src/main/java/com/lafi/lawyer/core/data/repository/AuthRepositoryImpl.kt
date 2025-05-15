package com.lafi.lawyer.core.data.repository

import com.lafi.lawyer.core.data.datasource.AuthRemoteDataSource
import com.lafi.lawyer.core.data.model.auth.request.AuthLoginSocialRequest
import com.lafi.lawyer.core.data.model.auth.request.SmsVerifyRequest
import com.lafi.lawyer.core.data.model.auth.request.SmsVerifyRequestCodeRequest
import com.lafi.lawyer.core.data.model.map
import com.lafi.lawyer.core.domain.model.DataResult
import com.lafi.lawyer.core.domain.model.auth.SmsVerifyRequestData
import com.lafi.lawyer.core.domain.model.auth.SocialProvider
import com.lafi.lawyer.core.domain.model.auth.SmsVerifyType
import com.lafi.lawyer.core.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun loginSocial(
        provider: SocialProvider,
        accessToken: String
    ): DataResult<String> {
        return authRemoteDataSource.postLoginSocial(
            requestBody = AuthLoginSocialRequest(
                provider = provider.providerName,
                accessToken = accessToken,
                requestID = "12345678901234567890121234567892"
            )
        ).map { it.userID }
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
}