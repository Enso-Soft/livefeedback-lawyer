package com.lafi.lawyer.core.data.repository

import com.lafi.lawyer.core.data.datasource.AuthRemoteDataSource
import com.lafi.lawyer.core.data.model.ApiResult
import com.lafi.lawyer.core.data.model.auth.AuthLoginSocialRequest
import com.lafi.lawyer.core.data.model.auth.SmsVerifyRequest
import com.lafi.lawyer.core.data.model.auth.SmsVerifyRequestCodeRequest
import com.lafi.lawyer.core.domain.model.DataResult
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
    ): DataResult<String> {
        val response = authRemoteDataSource.postSmsVerifyRequestCode(
            requestBody = SmsVerifyRequestCodeRequest(
                requestType = smsVerifyType.text,
                phoneNumber = phoneNumber
            )
        )

        return when (response) {
            is ApiResult.Success -> DataResult.Success(response.data.code)
            is ApiResult.Error -> DataResult.Error(response.error)
        }
    }

    override suspend fun smsVerify(
        smsVerifyType: SmsVerifyType,
        phoneNumber: String,
        code: String
    ): DataResult<Unit> {
        val response = authRemoteDataSource.postSmsVerify(
            requestBody = SmsVerifyRequest(
                requestType = smsVerifyType.text,
                phoneNumber = phoneNumber,
                code = code
            )
        )

        return when (response) {
            is ApiResult.Success -> DataResult.Success(Unit)
            is ApiResult.Error -> DataResult.Error(response.error)
        }
    }
}

