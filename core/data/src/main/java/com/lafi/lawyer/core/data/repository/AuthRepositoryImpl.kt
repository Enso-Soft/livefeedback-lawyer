package com.lafi.lawyer.core.data.repository

import com.lafi.lawyer.core.data.datasource.AuthRemoteDataSource
import com.lafi.lawyer.core.data.model.ApiResult
import com.lafi.lawyer.core.data.model.auth.request.AuthLoginSocialRequest
import com.lafi.lawyer.core.data.model.auth.request.SmsVerifyRequest
import com.lafi.lawyer.core.data.model.auth.request.SmsVerifyRequestCodeRequest
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
        val response = authRemoteDataSource.postLoginSocial(
            requestBody = AuthLoginSocialRequest(
                provider = provider.providerName,
                accessToken = accessToken,
                requestID = "12345678901234567890121234567892"
            )
        )

        return when (response) {
            is ApiResult.Success -> DataResult.Success(response.data.userID)
            is ApiResult.Error -> DataResult.Error(response.error)
        }
    }

    override suspend fun smsVerifyRequest(
        smsVerifyType: SmsVerifyType,
        phoneNumber: String
    ): DataResult<SmsVerifyRequestData> {
        val response = authRemoteDataSource.postSmsVerifyRequestCode(
            requestBody = SmsVerifyRequestCodeRequest(
                requestType = smsVerifyType.text,
                phoneNumber = phoneNumber
            )
        )

        return when (response) {
            is ApiResult.Success -> DataResult.Success(response.data.toDomain())
            is ApiResult.Error -> DataResult.Error(response.error)
        }
    }

    override suspend fun smsVerify(
        smsVerifyType: SmsVerifyType,
        phoneNumber: String,
        requestId: String,
        code: String
    ): DataResult<Unit> {
        val response = authRemoteDataSource.postSmsVerify(
            requestBody = SmsVerifyRequest(
                requestType = smsVerifyType.text,
                phoneNumber = phoneNumber,
                requestId = requestId,
                code = code
            )
        )

        return when (response) {
            is ApiResult.Success -> DataResult.Success(Unit)
            is ApiResult.Error -> DataResult.Error(response.error)
        }
    }
}

