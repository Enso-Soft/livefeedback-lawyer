package com.lafi.lawyer.core.data.repository

import com.lafi.lawyer.core.data.datasource.AuthRemoteDataSource
import com.lafi.lawyer.core.data.model.ApiResult
import com.lafi.lawyer.core.data.model.auth.AuthLoginSocialRequest
import com.lafi.lawyer.core.data.model.auth.SmsVerifyCodeRequest
import com.lafi.lawyer.core.domain.repository.AuthRepository
import com.lafi.lawyer.core.model.common.sms_verify.SmsVerifyType
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun loginSocial(
        provider: String,
        accessToken: String
    ): DataResult<String> {
        val response = authRemoteDataSource.postLoginSocial(
            requestBody = AuthLoginSocialRequest(
                provider = provider,
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
        val response = authRemoteDataSource.postSmsVerifyCode(
            requestBody = SmsVerifyCodeRequest(
                requestType = smsVerifyType.text,
                phoneNumber = phoneNumber
            )
        )

        return when (response) {
            is ApiResult.Success -> DataResult.Success(response.data.code)
            is ApiResult.Error -> DataResult.Error(response.error)
        }
    }
}

