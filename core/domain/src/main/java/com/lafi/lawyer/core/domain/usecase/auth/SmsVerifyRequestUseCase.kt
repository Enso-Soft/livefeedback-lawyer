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
    ): SmsVerifyRequestResult = authRepository.smsVerifyRequest(
        smsVerifyType = smsVerifyType,
        phoneNumber = phoneNumber
    ).let { response ->
        when (response) {
            is DataResult.Success -> SmsVerifyRequestResult.Success(
                code = response.data.code,
                expiresAt = response.data.expiresAt
            )
            is DataResult.Error -> when (response.error.code) {
                DUPLICATION_REQUEST_CODE -> SmsVerifyRequestResult.DuplicationRequest
                else -> SmsVerifyRequestResult.Error(error = response.error)
            }
        }
    }

    companion object {
        private const val DUPLICATION_REQUEST_CODE = 42901
    }
}