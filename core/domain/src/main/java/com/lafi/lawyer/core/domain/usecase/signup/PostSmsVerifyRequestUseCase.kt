package com.lafi.lawyer.core.domain.usecase.signup

import com.lafi.lawyer.core.domain.model.DataResult
import com.lafi.lawyer.core.domain.model.signup.SmsVerifyRequestResult
import com.lafi.lawyer.core.domain.model.signup.SmsVerifyType
import com.lafi.lawyer.core.domain.repository.AuthRepository
import javax.inject.Inject

class PostSmsVerifyRequestUseCase @Inject constructor(private val authRepository: AuthRepository) {
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
            is DataResult.Success -> SmsVerifyRequestResult.Success(code = response.data)
            is DataResult.Error -> SmsVerifyRequestResult.Error(error = response.error)
        }
    }
}