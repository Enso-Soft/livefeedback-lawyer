package com.lafi.lawyer.core.domain.usecase.auth

import com.lafi.lawyer.core.domain.model.DataResult
import com.lafi.lawyer.core.domain.model.auth.result.SmsVerifyRequestResult
import com.lafi.lawyer.core.domain.model.auth.SmsVerifyType
import com.lafi.lawyer.core.domain.repository.AuthRepository
import java.time.Instant
import javax.inject.Inject

class SmsVerifyRequestUseCase @Inject constructor(private val authRepository: AuthRepository) {
    private var tempExpiresAt: Instant? = null

    suspend operator fun invoke(
        smsVerifyType: SmsVerifyType,
        phoneNumber: String
    ): SmsVerifyRequestResult = authRepository.smsVerifyRequest(
        smsVerifyType = smsVerifyType,
        phoneNumber = phoneNumber
    ).let { response ->
        when (response) {
            is DataResult.Success -> {
                tempExpiresAt = response.data.expiresAt
                SmsVerifyRequestResult.Success(
                    code = response.data.code,
                    requestId = response.data.requestId,
                    expiresAt = response.data.expiresAt
                )
            }
            is DataResult.Error -> when (response.error.code) {
                DUPLICATION_REQUEST_CODE -> SmsVerifyRequestResult.DuplicationRequest(tempExpiresAt)
                else -> {
                    tempExpiresAt = null
                    SmsVerifyRequestResult.Error(error = response.error)
                }
            }
        }
    }

    companion object {
        private const val DUPLICATION_REQUEST_CODE = 42901
    }
}