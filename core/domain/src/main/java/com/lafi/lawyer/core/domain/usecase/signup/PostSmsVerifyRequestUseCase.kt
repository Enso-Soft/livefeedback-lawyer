package com.lafi.lawyer.core.domain.usecase.signup

import com.lafi.lawyer.core.domain.repository.AuthRepository
import com.lafi.lawyer.core.model.common.data.ApiResult
import com.lafi.lawyer.core.model.common.sms_verify.SmsVerifyRequestResult
import com.lafi.lawyer.core.model.common.sms_verify.SmsVerifyType
import javax.inject.Inject

class PostSmsVerifyRequestUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        smsVerifyType: SmsVerifyType,
        phoneNumber: String
    ): SmsVerifyRequestResult {
        return when (
            val response = authRepository.smsVerifyRequest(
                smsVerifyType = smsVerifyType,
                phoneNumber = phoneNumber
            )
        ) {
            is ApiResult.Success -> SmsVerifyRequestResult.Success(code = response.data)
            is ApiResult.Error -> SmsVerifyRequestResult.Error(error = response.error)
        }
    }
}