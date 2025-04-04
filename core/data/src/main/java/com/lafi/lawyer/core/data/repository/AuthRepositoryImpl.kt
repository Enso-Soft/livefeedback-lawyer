package com.lafi.lawyer.core.data.repository

import com.lafi.lawyer.core.domain.repository.AuthRepository
import com.lafi.lawyer.core.model.common.NetworkResult
import com.lafi.lawyer.core.model.common.sms_verify.SmsVerifyType
import com.lafi.lawyer.core.network.retrofit.lafi.auth.AuthDataSource
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.AuthLoginSocialRequest
import com.lafi.lawyer.core.network.retrofit.lafi.auth.model.SmsVerifyCodeRequest
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthDataSource
) : AuthRepository {
    override suspend fun loginSocial(
        provider: String,
        accessToken: String
    ): NetworkResult<Boolean> {
        val response = authApi.postLoginSocial(
            requestBody = AuthLoginSocialRequest(
                provider = provider,
                accessToken = accessToken,
            )
        )

        return if (response is NetworkResult.Success) {
            NetworkResult.Success(response.data.userExists)
        } else {
            response as NetworkResult.Error
        }
    }

    override suspend fun smsVerifyRequest(
        smsVerifyType: SmsVerifyType,
        phoneNumber: String
    ): NetworkResult<String> {
        val response = authApi.postSmsVerifyCode(
            requestBody = SmsVerifyCodeRequest(
                requestType = smsVerifyType.text,
                phoneNumber = phoneNumber
            )
        )

        return if (response is NetworkResult.Success) {
            NetworkResult.Success(response.data.code)
        } else {
            response as NetworkResult.Error
        }
    }
}

