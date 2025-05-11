package com.lafi.lawyer.core.domain.usecase.auth

import com.lafi.lawyer.core.domain.model.DataResult
import com.lafi.lawyer.core.domain.model.auth.SmsVerifyRequestResult
import com.lafi.lawyer.core.domain.model.auth.SmsVerifyType
import com.lafi.lawyer.core.domain.repository.AuthRepository
import javax.inject.Inject

class SmsVerifyRequestUseCase @Inject constructor(private val authRepository: AuthRepository) {
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
            is DataResult.Success -> SmsVerifyRequestResult.Success(code = response.data.code, expiresAt = response.data.expiresAt)
            is DataResult.Error -> {
                when (response.error.code) {
                    42901 -> SmsVerifyRequestResult.DuplicationRequest
                    else -> SmsVerifyRequestResult.Error(error = response.error)
                }
            }
        }
    }
}