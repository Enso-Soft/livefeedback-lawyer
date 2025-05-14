package com.lafi.lawyer.core.domain.usecase.auth

import com.lafi.lawyer.core.domain.model.DataResult
import com.lafi.lawyer.core.domain.model.auth.SmsVerifyResult
import com.lafi.lawyer.core.domain.model.auth.SmsVerifyType
import com.lafi.lawyer.core.domain.repository.AuthRepository
import javax.inject.Inject

class SmsVerifyUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(
        smsVerifyType: SmsVerifyType,
        phoneNumber: String,
        requestId: String,
        code: String
    ): SmsVerifyResult {
        return when (
            val response = authRepository.smsVerify(
                smsVerifyType = smsVerifyType,
                phoneNumber = phoneNumber,
                requestId = requestId,
                code = code
            )
        ) {
            is DataResult.Success -> SmsVerifyResult.Success
            is DataResult.Error -> {
                when (response.error.code) {
                    INVALID_FORMAT_PHONE -> SmsVerifyResult.InvalidFormatPhoneNumber
                    INVALID_VERIFY_CODE -> SmsVerifyResult.InvalidVerifyCode
                    else -> SmsVerifyResult.Error(error = response.error)
                }
            }
        }
    }

    companion object {
        private const val INVALID_FORMAT_PHONE = 40005
        private const val INVALID_VERIFY_CODE = 40009
    }
}